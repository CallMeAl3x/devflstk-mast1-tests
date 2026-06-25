package com.example.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** Test d'intégration : contexte Spring complet, parcours salle → réservation → consultation → annulation. */
@SpringBootTest
@AutoConfigureMockMvc
class ReservationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Parcours complet : créer une salle, réserver, consulter puis annuler")
    void parcoursComplet() throws Exception {
        // 1. Créer une salle → 201
        String roomBody = mockMvc.perform(post("/api/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Salle A\",\"capacity\":8}"))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        long roomId = com.jayway.jsonpath.JsonPath.parse(roomBody).read("$.id", Long.class);

        // 2. Créer une réservation → 201 et statut CONFIRMED
        String resaBody = mockMvc.perform(post("/api/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"roomId\":" + roomId + ",\"personName\":\"Alice\","
                                + "\"start\":\"2026-06-12T09:00:00\",\"end\":\"2026-06-12T10:00:00\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("CONFIRMED"))
                .andReturn().getResponse().getContentAsString();
        long resaId = com.jayway.jsonpath.JsonPath.parse(resaBody).read("$.id", Long.class);

        // 3. Consulter la réservation → 200
        mockMvc.perform(get("/api/reservations/" + resaId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personName").value("Alice"));

        // 4. Annuler la réservation → 200 et statut CANCELLED
        mockMvc.perform(patch("/api/reservations/" + resaId + "/cancel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CANCELLED"));

        // 5. Nouvelle annulation interdite → 409
        mockMvc.perform(patch("/api/reservations/" + resaId + "/cancel"))
                .andExpect(status().isConflict());
    }
}
