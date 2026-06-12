package com.example.service;

import com.example.exception.InvalidStatusTransitionException;
import com.example.exception.TicketNotFoundException;
import com.example.model.Priority;
import com.example.model.Ticket;
import com.example.model.TicketStatus;
import com.example.repository.TicketRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** Tests unitaires du service avec Mockito (la couche de persistance est simulée). */
@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock
    private TicketRepository repository;

    @InjectMocks
    private TicketService service;

    @Test
    @DisplayName("La création initialise le statut à OPEN et persiste le ticket")
    void creation_statutInitialOpen() {
        when(repository.save(any(Ticket.class))).thenAnswer(invocation -> {
            Ticket t = invocation.getArgument(0);
            t.setId(1L);
            return t;
        });

        Ticket created = service.create("Imprimante en panne", Priority.HIGH);

        assertThat(created.getId()).isEqualTo(1L);
        assertThat(created.getStatus()).isEqualTo(TicketStatus.OPEN);
        assertThat(created.getTitle()).isEqualTo("Imprimante en panne");
        assertThat(created.getPriority()).isEqualTo(Priority.HIGH);
        verify(repository).save(any(Ticket.class));
    }

    @Test
    @DisplayName("getById lève TicketNotFoundException si le ticket est absent")
    void consultation_inconnu_leveNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(99L))
                .isInstanceOf(TicketNotFoundException.class);
    }

    @Test
    @DisplayName("Transition autorisée OPEN → IN_PROGRESS")
    void transition_openVersInProgress_ok() {
        Ticket ticket = new Ticket(1L, "Bug", Priority.MEDIUM, TicketStatus.OPEN);
        when(repository.findById(1L)).thenReturn(Optional.of(ticket));
        when(repository.save(any(Ticket.class))).thenAnswer(i -> i.getArgument(0));

        Ticket result = service.changeStatus(1L, TicketStatus.IN_PROGRESS);

        assertThat(result.getStatus()).isEqualTo(TicketStatus.IN_PROGRESS);
        verify(repository).save(ticket);
    }

    @Test
    @DisplayName("Transition interdite : un ticket RESOLVED ne change plus de statut")
    void transition_depuisResolved_leveConflit() {
        Ticket ticket = new Ticket(1L, "Bug", Priority.LOW, TicketStatus.RESOLVED);
        when(repository.findById(1L)).thenReturn(Optional.of(ticket));

        assertThatThrownBy(() -> service.changeStatus(1L, TicketStatus.IN_PROGRESS))
                .isInstanceOf(InvalidStatusTransitionException.class);

        verify(repository, never()).save(any(Ticket.class));
    }

    @Test
    @DisplayName("Transition interdite : IN_PROGRESS ne peut pas revenir à OPEN")
    void transition_inProgressVersOpen_leveConflit() {
        Ticket ticket = new Ticket(1L, "Bug", Priority.LOW, TicketStatus.IN_PROGRESS);
        when(repository.findById(1L)).thenReturn(Optional.of(ticket));

        assertThatThrownBy(() -> service.changeStatus(1L, TicketStatus.OPEN))
                .isInstanceOf(InvalidStatusTransitionException.class);
    }
}
