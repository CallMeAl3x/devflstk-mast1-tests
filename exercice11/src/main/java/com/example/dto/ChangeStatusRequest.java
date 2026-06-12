package com.example.dto;

import com.example.model.TicketStatus;
import jakarta.validation.constraints.NotNull;

/** Corps de requête pour la modification du statut d'un ticket. */
public record ChangeStatusRequest(

        @NotNull(message = "Le nouveau statut est obligatoire")
        TicketStatus status
) {
}
