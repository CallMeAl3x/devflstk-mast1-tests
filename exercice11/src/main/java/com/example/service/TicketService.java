package com.example.service;

import com.example.exception.InvalidStatusTransitionException;
import com.example.exception.TicketNotFoundException;
import com.example.model.Priority;
import com.example.model.Ticket;
import com.example.model.TicketStatus;
import com.example.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/** Logique métier de gestion des tickets. */
@Service
public class TicketService {

    private final TicketRepository repository;

    public TicketService(TicketRepository repository) {
        this.repository = repository;
    }

    /** Crée un ticket. Le statut initial est toujours OPEN. */
    public Ticket create(String title, Priority priority) {
        Ticket ticket = new Ticket(null, title, priority, TicketStatus.OPEN);
        return repository.save(ticket);
    }

    /** Récupère un ticket ou lève {@link TicketNotFoundException}. */
    public Ticket getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException(id));
    }

    public List<Ticket> findAll() {
        return repository.findAll();
    }

    /**
     * Change le statut d'un ticket en respectant les transitions autorisées.
     *
     * @throws TicketNotFoundException          si le ticket n'existe pas
     * @throws InvalidStatusTransitionException si la transition est interdite
     */
    public Ticket changeStatus(Long id, TicketStatus cible) {
        Ticket ticket = getById(id);
        if (!ticket.getStatus().peutPasserA(cible)) {
            throw new InvalidStatusTransitionException(ticket.getStatus(), cible);
        }
        ticket.setStatus(cible);
        return repository.save(ticket);
    }
}
