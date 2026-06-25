package com.example.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/** Corps de requête pour la création d'une salle. */
public record CreateRoomRequest(

        @NotBlank(message = "Le nom de la salle est obligatoire")
        String name,

        @NotNull(message = "La capacité est obligatoire")
        @Min(value = 1, message = "La capacité doit être supérieure ou égale à 1")
        Integer capacity
) {
}
