package com.example.exception;

/** Levée lorsqu'une réservation a des données invalides, ex. créneau incohérent (→ HTTP 400). */
public class InvalidReservationException extends RuntimeException {

    public InvalidReservationException(String message) {
        super(message);
    }
}
