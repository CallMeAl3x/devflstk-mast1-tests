package com.example.dto;

import com.example.model.Reservation;
import com.example.model.ReservationStatus;

import java.time.LocalDateTime;

/** Représentation d'une réservation renvoyée par l'API. */
public record ReservationResponse(Long id, Long roomId, String personName,
                                  LocalDateTime start, LocalDateTime end, ReservationStatus status) {

    public static ReservationResponse from(Reservation r) {
        return new ReservationResponse(r.getId(), r.getRoomId(), r.getPersonName(),
                r.getStart(), r.getEnd(), r.getStatus());
    }
}
