package com.example.exception;

/** Levée lorsqu'aucune salle ne correspond à l'identifiant demandé (→ HTTP 404). */
public class RoomNotFoundException extends RuntimeException {

    public RoomNotFoundException(Long id) {
        super("Salle introuvable : " + id);
    }
}
