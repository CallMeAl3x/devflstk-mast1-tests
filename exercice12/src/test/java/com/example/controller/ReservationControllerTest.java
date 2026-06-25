package com.example.controller;

import com.example.exception.ReservationConflictException;
import com.example.exception.ReservationNotFoundException;
import com.example.model.Reservation;
import com.example.model.ReservationStatus;
import com.example.service.ReservationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** Tests MockMvc du controller des réservations. */
@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService service;

    @Test
    @DisplayName("POST /api/reservations valide → 201 Created")
    void creationReservation_valide_retourne201() throws Exception {
        Reservation reservation = new Reservation(1L, 1L, "Alice",
                LocalDateTime.of(2026, 6, 12, 9, 0),
                LocalDateTime.of(2026, 6, 12, 10, 0),
                ReservationStatus.CONFIRMED);
        when(service.create(eq(1L), eq("Alice"), any(), any())).thenReturn(reservation);

        mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"roomId\":1,\"personName\":\"Alice\","
                                + "\"start\":\"2026-06-12T09:00:00\",\"end\":\"2026-06-12T10:00:00\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("CONFIRMED"));
    }

    @Test
    @DisplayName("GET /api/reservations/{id} inexistante → 404 Not Found")
    void consultation_inexistante_retourne404() throws Exception {
        when(service.getById(99L)).thenThrow(new ReservationNotFoundException(99L));

        mockMvc.perform(get("/api/reservations/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PATCH /api/reservations/{id}/cancel sur réservation déjà annulée → 409 Conflict")
    void annulation_dejaAnnulee_retourne409() throws Exception {
        when(service.cancel(1L)).thenThrow(new ReservationConflictException("La réservation est déjà annulée"));

        mockMvc.perform(patch("/api/reservations/1/cancel"))
                .andExpect(status().isConflict());
    }
}
