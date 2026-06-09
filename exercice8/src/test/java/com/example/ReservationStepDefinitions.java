package com.example;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReservationStepDefinitions {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private RepertoireSalle repertoire;
    private RegistreReservation registre;
    private ServiceNotification notification;
    private ServiceReservation service;

    private ConfirmationReservation confirmation;
    private RuntimeException erreur;

    @Before
    public void setUp() {
        repertoire = mock(RepertoireSalle.class);
        registre = mock(RegistreReservation.class);
        notification = mock(ServiceNotification.class);
        service = new ServiceReservation(repertoire, registre, notification);
        confirmation = null;
        erreur = null;
    }

    @Given("une salle {string} nommée {string} d'une capacité de {int}")
    public void une_salle(String code, String nom, int capacite) {
        when(repertoire.trouverParCode(code)).thenReturn(new Salle(code, nom, capacite));
    }

    @Given("la salle {string} n'existe pas")
    public void la_salle_n_existe_pas(String code) {
        when(repertoire.trouverParCode(code)).thenReturn(null);
    }

    @Given("aucune réservation existante pour la salle {string}")
    public void aucune_reservation_existante(String code) {
        when(registre.reservationsPour(code)).thenReturn(List.of());
    }

    @Given("une réservation existante pour la salle {string} du {string} au {string}")
    public void une_reservation_existante(String code, String debut, String fin) {
        Reservation existante = new Reservation(
                "autre@example.com", code, 1, parse(debut), parse(fin));
        when(registre.reservationsPour(code)).thenReturn(List.of(existante));
    }

    @When("un utilisateur réserve la salle {string} pour {int} participants du {string} au {string}")
    public void un_utilisateur_reserve(String code, int participants, String debut, String fin) {
        Reservation demande = new Reservation(
                "utilisateur@example.com", code, participants, parse(debut), parse(fin));
        try {
            confirmation = service.reserver(demande);
        } catch (RuntimeException e) {
            erreur = e;
        }
    }

    @Then("la réservation est acceptée")
    public void la_reservation_est_acceptee() {
        assertNull(erreur, () -> "réservation refusée de façon inattendue : " + erreur);
        assertNotNull(confirmation);
    }

    @Then("la réservation est refusée car la salle est inconnue")
    public void refusee_salle_inconnue() {
        assertRefusee(SalleInconnueException.class);
    }

    @Then("la réservation est refusée car la capacité est insuffisante")
    public void refusee_capacite_insuffisante() {
        assertRefusee(CapaciteInsuffisanteException.class);
    }

    @Then("la réservation est refusée car la période est invalide")
    public void refusee_periode_invalide() {
        assertRefusee(PeriodeInvalideException.class);
    }

    @Then("la réservation est refusée car la salle est déjà réservée")
    public void refusee_conflit() {
        assertRefusee(ConflitReservationException.class);
    }

    @Then("une confirmation est envoyée")
    public void une_confirmation_est_envoyee() {
        verify(notification).envoyerConfirmation(any(Reservation.class));
    }

    @Then("aucune confirmation n'est envoyée")
    public void aucune_confirmation_n_est_envoyee() {
        verify(notification, never()).envoyerConfirmation(any(Reservation.class));
    }

    private void assertRefusee(Class<? extends RuntimeException> type) {
        assertNull(confirmation);
        assertTrue(type.isInstance(erreur),
                () -> "exception attendue " + type.getSimpleName() + " mais : " + erreur);
    }

    private static LocalDateTime parse(String texte) {
        return LocalDateTime.parse(texte, FORMAT);
    }
}
