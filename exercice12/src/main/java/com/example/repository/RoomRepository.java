package com.example.repository;

import com.example.model.Room;

import java.util.List;
import java.util.Optional;

/** Contrat de persistance des salles (implémentation en mémoire). */
public interface RoomRepository {

    Room save(Room room);

    Optional<Room> findById(Long id);

    List<Room> findAll();
}
