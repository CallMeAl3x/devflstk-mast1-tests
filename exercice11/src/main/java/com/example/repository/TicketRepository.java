package com.example.repository;

import com.example.model.Ticket;

import java.util.List;
import java.util.Optional;

/** Contrat de persistance des tickets (implémentation en mémoire). */
public interface TicketRepository {

    /** Sauvegarde un ticket. Un identifiant est attribué si le ticket n'en a pas encore. */
    Ticket save(Ticket ticket);

    Optional<Ticket> findById(Long id);

    List<Ticket> findAll();
}
