package com.example.dto;

import com.example.model.Room;

/** Représentation d'une salle renvoyée par l'API. */
public record RoomResponse(Long id, String name, int capacity) {

    public static RoomResponse from(Room room) {
        return new RoomResponse(room.getId(), room.getName(), room.getCapacity());
    }
}
