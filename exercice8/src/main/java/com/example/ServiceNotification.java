package com.example;

/**
 * Service d'envoi de confirmation, simulé avec Mockito pour vérifier qu'une
 * notification est envoyée uniquement lorsque la réservation aboutit.
 */
public interface ServiceNotification {

    void envoyerConfirmation(Reservation reservation);
}
