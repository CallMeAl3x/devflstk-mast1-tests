package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/** Corps de requête pour la création d'une réservation. */
public record CreateReservationRequest(

        @NotNull(message = "L'identifiant de la salle est obligatoire")
        Long roomId,

        @NotBlank(message = "Le nom de la personne est obligatoire")
        String personName,

        @NotNull(message = "La date/heure de début est obligatoire")
        LocalDateTime start,

        @NotNull(message = "La date/heure de fin est obligatoire")
        LocalDateTime end
) {
}
