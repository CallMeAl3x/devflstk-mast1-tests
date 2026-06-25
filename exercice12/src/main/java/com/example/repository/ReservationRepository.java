package com.example.repository;

import com.example.model.Reservation;

import java.util.List;
import java.util.Optional;

/** Contrat de persistance des réservations (implémentation en mémoire). */
public interface ReservationRepository {

    Reservation save(Reservation reservation);

    Optional<Reservation> findById(Long id);

    /** Retourne toutes les réservations d'une salle (utile pour la détection de chevauchement). */
    List<Reservation> findByRoomId(Long roomId);
}
