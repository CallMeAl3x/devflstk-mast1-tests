package com.example.dto;

import com.example.model.Priority;
import com.example.model.Ticket;
import com.example.model.TicketStatus;

/** Représentation d'un ticket renvoyée par l'API. */
public record TicketResponse(Long id, String title, Priority priority, TicketStatus status) {

    public static TicketResponse from(Ticket ticket) {
        return new TicketResponse(ticket.getId(), ticket.getTitle(), ticket.getPriority(), ticket.getStatus());
    }
}
