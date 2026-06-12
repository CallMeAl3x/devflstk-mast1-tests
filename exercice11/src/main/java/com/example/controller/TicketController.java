package com.example.controller;

import com.example.dto.ChangeStatusRequest;
import com.example.dto.CreateTicketRequest;
import com.example.dto.TicketResponse;
import com.example.model.Ticket;
import com.example.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Endpoints REST de gestion des tickets. */
@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService service;

    public TicketController(TicketService service) {
        this.service = service;
    }

    /** Création d'un ticket → 201 Created. */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TicketResponse create(@Valid @RequestBody CreateTicketRequest request) {
        Ticket ticket = service.create(request.title(), request.priority());
        return TicketResponse.from(ticket);
    }

    /** Consultation d'un ticket → 200 OK (404 si absent). */
    @GetMapping("/{id}")
    public TicketResponse getOne(@PathVariable Long id) {
        return TicketResponse.from(service.getById(id));
    }

    /** Liste des tickets → 200 OK. */
    @GetMapping
    public List<TicketResponse> list() {
        return service.findAll().stream().map(TicketResponse::from).toList();
    }

    /** Modification du statut → 200 OK (404 si absent, 409 si transition interdite). */
    @PatchMapping("/{id}/status")
    public TicketResponse changeStatus(@PathVariable Long id,
                                       @Valid @RequestBody ChangeStatusRequest request) {
        return TicketResponse.from(service.changeStatus(id, request.status()));
    }
}
