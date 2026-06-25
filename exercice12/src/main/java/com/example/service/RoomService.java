package com.example.service;

import com.example.model.Room;
import com.example.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/** Logique métier de gestion des salles. */
@Service
public class RoomService {

    private final RoomRepository repository;

    public RoomService(RoomRepository repository) {
        this.repository = repository;
    }

    /** Crée une salle. */
    public Room create(String name, int capacity) {
        return repository.save(new Room(null, name, capacity));
    }

    public List<Room> findAll() {
        return repository.findAll();
    }
}
