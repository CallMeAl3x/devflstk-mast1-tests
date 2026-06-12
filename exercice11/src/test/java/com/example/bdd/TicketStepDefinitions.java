package com.example.bdd;

import com.example.exception.InvalidStatusTransitionException;
import com.example.exception.TicketNotFoundException;
import com.example.model.Priority;
import com.example.model.Ticket;
import com.example.model.TicketStatus;
import com.example.service.TicketService;
import io.cucumber.java.fr.Alors;
import io.cucumber.java.fr.Etantdonné;
import io.cucumber.java.fr.Quand;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

/** Définitions des étapes Gherkin, branchées sur le vrai service Spring. */
public class TicketStepDefinitions {

    @Autowired
    private TicketService service;

    private Ticket ticket;
    private RuntimeException erreur;

    @Etantdonné("un ticket {string} de priorité {string}")
    public void unTicket(String titre, String priorite) {
        ticket = service.create(titre, Priority.valueOf(priorite));
    }

    @Etantdonné("aucun ticket d'identifiant {long}")
    public void aucunTicket(Long id) {
        // Rien à créer : on s'appuie sur le fait que cet identifiant n'existe pas.
    }

    @Quand("on crée un ticket {string} de priorité {string}")
    public void onCreeUnTicket(String titre, String priorite) {
        ticket = service.create(titre, Priority.valueOf(priorite));
    }

    @Alors("le ticket est créé avec le statut {string}")
    public void leTicketEstCree(String statut) {
        assertThat(erreur).isNull();
        assertThat(ticket.getId()).isNotNull();
        assertThat(ticket.getStatus()).isEqualTo(TicketStatus.valueOf(statut));
    }

    @Quand("on change le statut du ticket vers {string}")
    public void onChangeLeStatut(String statut) {
        try {
            ticket = service.changeStatus(ticket.getId(), TicketStatus.valueOf(statut));
        } catch (InvalidStatusTransitionException e) {
            erreur = e;
        }
    }

    @Quand("on consulte le ticket d'identifiant {long}")
    public void onConsulteLeTicket(Long id) {
        try {
            ticket = service.getById(id);
        } catch (TicketNotFoundException e) {
            erreur = e;
        }
    }

    @Alors("le statut du ticket est {string}")
    public void leStatutEst(String statut) {
        assertThat(erreur).isNull();
        assertThat(ticket.getStatus()).isEqualTo(TicketStatus.valueOf(statut));
    }

    @Alors("une erreur de transition interdite est renvoyée")
    public void uneErreurDeTransition() {
        assertThat(erreur).isInstanceOf(InvalidStatusTransitionException.class);
    }

    @Alors("une erreur de ticket introuvable est renvoyée")
    public void uneErreurIntrouvable() {
        assertThat(erreur).isInstanceOf(TicketNotFoundException.class);
    }
}
