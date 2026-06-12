package com.example.exception;

import com.example.model.TicketStatus;

/** Levée lorsqu'une transition de statut n'est pas autorisée (→ HTTP 409). */
public class InvalidStatusTransitionException extends RuntimeException {

    public InvalidStatusTransitionException(TicketStatus actuel, TicketStatus cible) {
        super("Transition de statut interdite : " + actuel + " → " + cible);
    }
}
