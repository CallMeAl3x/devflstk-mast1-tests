package com.example.controller;

import com.example.dto.CreateReservationRequest;
import com.example.dto.ReservationResponse;
import com.example.model.Reservation;
import com.example.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** Endpoints REST de gestion des réservations. */
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }

    /** Création d'une réservation → 201 Created. */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse create(@Valid @RequestBody CreateReservationRequest request) {
        Reservation reservation = service.create(
                request.roomId(), request.personName(), request.start(), request.end());
        return ReservationResponse.from(reservation);
    }

    /** Consultation d'une réservation → 200 OK (404 si absente). */
    @GetMapping("/{id}")
    public ReservationResponse getOne(@PathVariable Long id) {
        return ReservationResponse.from(service.getById(id));
    }

    /** Annulation d'une réservation → 200 OK (404 si absente, 409 si déjà annulée). */
    @PatchMapping("/{id}/cancel")
    public ReservationResponse cancel(@PathVariable Long id) {
        return ReservationResponse.from(service.cancel(id));
    }
}
