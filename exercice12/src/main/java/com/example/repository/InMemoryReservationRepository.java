package com.example.repository;

import com.example.model.Reservation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/** Repository en mémoire des réservations : map + identifiant auto-incrémenté. */
@Repository
public class InMemoryReservationRepository implements ReservationRepository {

    private final Map<Long, Reservation> store = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    @Override
    public Reservation save(Reservation reservation) {
        if (reservation.getId() == null) {
            reservation.setId(sequence.incrementAndGet());
        }
        store.put(reservation.getId(), reservation);
        return reservation;
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Reservation> findByRoomId(Long roomId) {
        return store.values().stream()
                .filter(r -> r.getRoomId().equals(roomId))
                .toList();
    }
}
