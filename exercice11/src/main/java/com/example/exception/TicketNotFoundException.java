package com.example.exception;

/** Levée lorsqu'aucun ticket ne correspond à l'identifiant demandé (→ HTTP 404). */
public class TicketNotFoundException extends RuntimeException {

    public TicketNotFoundException(Long id) {
        super("Ticket introuvable : " + id);
    }
}
