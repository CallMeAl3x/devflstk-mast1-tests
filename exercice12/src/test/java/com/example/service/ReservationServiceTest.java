package com.example.service;

import com.example.exception.InvalidReservationException;
import com.example.exception.ReservationConflictException;
import com.example.exception.ReservationNotFoundException;
import com.example.exception.RoomNotFoundException;
import com.example.model.Reservation;
import com.example.model.ReservationStatus;
import com.example.model.Room;
import com.example.repository.ReservationRepository;
import com.example.repository.RoomRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** Tests unitaires du service de réservation avec Mockito. */
@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private ReservationService service;

    private final LocalDateTime debut = LocalDateTime.of(2026, 6, 12, 9, 0);
    private final LocalDateTime fin = LocalDateTime.of(2026, 6, 12, 10, 0);

    @Test
    @DisplayName("Création valide : la réservation est confirmée et persistée")
    void creation_valide() {
        when(roomRepository.findById(1L)).thenReturn(Optional.of(new Room(1L, "Salle A", 4)));
        when(reservationRepository.findByRoomId(1L)).thenReturn(List.of());
        when(reservationRepository.save(any(Reservation.class))).thenAnswer(i -> {
            Reservation r = i.getArgument(0);
            r.setId(1L);
            return r;
        });

        Reservation result = service.create(1L, "Alice", debut, fin);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getStatus()).isEqualTo(ReservationStatus.CONFIRMED);
        verify(reservationRepository).save(any(Reservation.class));
    }

    @Test
    @DisplayName("Refus si la salle n'existe pas")
    void creation_salleInexistante() {
        when(roomRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.create(99L, "Alice", debut, fin))
                .isInstanceOf(RoomNotFoundException.class);

        verify(reservationRepository, never()).save(any());
    }

    @Test
    @DisplayName("Refus si le créneau est invalide (fin avant ou égale au début)")
    void creation_creneauInvalide() {
        when(roomRepository.findById(1L)).thenReturn(Optional.of(new Room(1L, "Salle A", 4)));

        assertThatThrownBy(() -> service.create(1L, "Alice", fin, debut))
                .isInstanceOf(InvalidReservationException.class);

        verify(reservationRepository, never()).save(any());
    }

    @Test
    @DisplayName("Refus si le créneau chevauche une réservation confirmée existante")
    void creation_chevauchement() {
        when(roomRepository.findById(1L)).thenReturn(Optional.of(new Room(1L, "Salle A", 4)));
        Reservation existante = new Reservation(1L, 1L, "Bob",
                LocalDateTime.of(2026, 6, 12, 9, 30),
                LocalDateTime.of(2026, 6, 12, 10, 30),
                ReservationStatus.CONFIRMED);
        when(reservationRepository.findByRoomId(1L)).thenReturn(List.of(existante));

        assertThatThrownBy(() -> service.create(1L, "Alice", debut, fin))
                .isInstanceOf(ReservationConflictException.class);

        verify(reservationRepository, never()).save(any());
    }

    @Test
    @DisplayName("Annulation d'une réservation confirmée")
    void annulation_confirmee() {
        Reservation reservation = new Reservation(1L, 1L, "Alice", debut, fin, ReservationStatus.CONFIRMED);
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(any(Reservation.class))).thenAnswer(i -> i.getArgument(0));

        Reservation result = service.cancel(1L);

        assertThat(result.getStatus()).isEqualTo(ReservationStatus.CANCELLED);
    }

    @Test
    @DisplayName("Refus d'annulation si la réservation est déjà annulée")
    void annulation_dejaAnnulee() {
        Reservation reservation = new Reservation(1L, 1L, "Alice", debut, fin, ReservationStatus.CANCELLED);
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        assertThatThrownBy(() -> service.cancel(1L))
                .isInstanceOf(ReservationConflictException.class);

        verify(reservationRepository, never()).save(any());
    }

    @Test
    @DisplayName("Consultation d'une réservation inexistante")
    void consultation_inexistante() {
        lenient().when(reservationRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(99L))
                .isInstanceOf(ReservationNotFoundException.class);
    }
}
