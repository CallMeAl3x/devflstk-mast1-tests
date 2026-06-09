package com.example;

import java.util.List;

/**
 * Registre des réservations déjà enregistrées, simulé avec Mockito dans les tests.
 */
public interface RegistreReservation {

    /**
     * @return les réservations existantes pour une salle donnée.
     */
    List<Reservation> reservationsPour(String codeSalle);
}
