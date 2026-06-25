package com.example.exception;

/** Levée lorsqu'une règle métier empêche l'action : chevauchement ou double annulation (→ HTTP 409). */
public class ReservationConflictException extends RuntimeException {

    public ReservationConflictException(String message) {
        super(message);
    }
}
