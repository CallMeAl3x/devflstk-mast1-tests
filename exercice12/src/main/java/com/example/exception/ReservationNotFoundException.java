package com.example.exception;

/** Levée lorsqu'aucune réservation ne correspond à l'identifiant demandé (→ HTTP 404). */
public class ReservationNotFoundException extends RuntimeException {

    public ReservationNotFoundException(Long id) {
        super("Réservation introuvable : " + id);
    }
}
