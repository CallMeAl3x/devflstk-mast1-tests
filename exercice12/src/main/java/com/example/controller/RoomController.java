package com.example.controller;

import com.example.dto.CreateRoomRequest;
import com.example.dto.RoomResponse;
import com.example.model.Room;
import com.example.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Endpoints REST de gestion des salles. */
@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService service;

    public RoomController(RoomService service) {
        this.service = service;
    }

    /** Création d'une salle → 201 Created. */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoomResponse create(@Valid @RequestBody CreateRoomRequest request) {
        Room room = service.create(request.name(), request.capacity());
        return RoomResponse.from(room);
    }

    /** Liste des salles → 200 OK. */
    @GetMapping
    public List<RoomResponse> list() {
        return service.findAll().stream().map(RoomResponse::from).toList();
    }
}
