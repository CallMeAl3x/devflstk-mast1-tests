package com.example;

public class ConflitReservationException extends RuntimeException {

    public ConflitReservationException(String code) {
        super("La salle " + code + " est déjà réservée sur le créneau demandé");
    }
}
