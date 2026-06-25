package com.example.repository;

import com.example.model.Room;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/** Repository en mémoire des salles : map + identifiant auto-incrémenté. */
@Repository
public class InMemoryRoomRepository implements RoomRepository {

    private final Map<Long, Room> store = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    @Override
    public Room save(Room room) {
        if (room.getId() == null) {
            room.setId(sequence.incrementAndGet());
        }
        store.put(room.getId(), room);
        return room;
    }

    @Override
    public Optional<Room> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Room> findAll() {
        return new ArrayList<>(store.values());
    }
}
