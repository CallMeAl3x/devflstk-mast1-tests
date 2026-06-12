package com.example.dto;

import com.example.model.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/** Corps de requête pour la création d'un ticket. */
public record CreateTicketRequest(

        @NotBlank(message = "Le titre est obligatoire")
        @Size(min = 3, message = "Le titre doit contenir au moins 3 caractères")
        String title,

        @NotNull(message = "La priorité est obligatoire")
        Priority priority
) {
}
