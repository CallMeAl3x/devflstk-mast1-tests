package com.example.model;

/** Statut d'un ticket. Le statut initial est toujours OPEN. */
public enum TicketStatus {
    OPEN,
    IN_PROGRESS,
    RESOLVED;

    /**
     * Indique si la transition de ce statut vers {@code cible} est autorisée.
     * Transitions permises : OPEN→IN_PROGRESS, OPEN→RESOLVED, IN_PROGRESS→RESOLVED.
     * Un ticket RESOLVED ne peut plus changer de statut.
     */
    public boolean peutPasserA(TicketStatus cible) {
        return switch (this) {
            case OPEN -> cible == IN_PROGRESS || cible == RESOLVED;
            case IN_PROGRESS -> cible == RESOLVED;
            case RESOLVED -> false;
        };
    }
}
