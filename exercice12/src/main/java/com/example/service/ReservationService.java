package com.example.service;

import com.example.exception.InvalidReservationException;
import com.example.exception.ReservationConflictException;
import com.example.exception.ReservationNotFoundException;
import com.example.exception.RoomNotFoundException;
import com.example.model.Reservation;
import com.example.model.ReservationStatus;
import com.example.repository.ReservationRepository;
import com.example.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/** Logique métier de gestion des réservations. */
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    public ReservationService(ReservationRepository reservationRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
    }

    /**
     * Crée une réservation confirmée.
     *
     * @throws RoomNotFoundException         si la salle n'existe pas
     * @throws InvalidReservationException   si la fin n'est pas strictement après le début
     * @throws ReservationConflictException  si le créneau chevauche une réservation confirmée existante
     */
    public Reservation create(Long roomId, String personName, LocalDateTime start, LocalDateTime end) {
        if (roomRepository.findById(roomId).isEmpty()) {
            throw new RoomNotFoundException(roomId);
        }
        if (start == null || end == null || !end.isAfter(start)) {
            throw new InvalidReservationException(
                    "La date/heure de fin doit être strictement après la date/heure de début");
        }
        boolean chevauchement = reservationRepository.findByRoomId(roomId).stream()
                .filter(r -> r.getStatus() == ReservationStatus.CONFIRMED)
                .anyMatch(r -> start.isBefore(r.getEnd()) && r.getStart().isBefore(end));
        if (chevauchement) {
            throw new ReservationConflictException(
                    "Le créneau chevauche une réservation existante pour cette salle");
        }
        Reservation reservation = new Reservation(null, roomId, personName, start, end, ReservationStatus.CONFIRMED);
        return reservationRepository.save(reservation);
    }

    /** Récupère une réservation ou lève {@link ReservationNotFoundException}. */
    public Reservation getById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException(id));
    }

    /**
     * Annule une réservation.
     *
     * @throws ReservationNotFoundException  si la réservation n'existe pas
     * @throws ReservationConflictException  si la réservation est déjà annulée
     */
    public Reservation cancel(Long id) {
        Reservation reservation = getById(id);
        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new ReservationConflictException("La réservation est déjà annulée");
        }
        reservation.setStatus(ReservationStatus.CANCELLED);
        return reservationRepository.save(reservation);
    }
}
