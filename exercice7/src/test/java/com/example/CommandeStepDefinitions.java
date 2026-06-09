package com.example;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CommandeStepDefinitions {

    private CatalogueProduit catalogue;
    private ServiceCommande service;
    private ProfilClient profil;

    private RecuCommande recu;
    private RuntimeException erreur;

    @Before
    public void setUp() {
        // Mockito simule la dépendance qui fournit les informations produit
        catalogue = mock(CatalogueProduit.class);
        service = new ServiceCommande(catalogue);
        recu = null;
        erreur = null;
    }

    @Given("un produit {string} nommé {string} au prix unitaire de {double} avec un stock de {int}")
    public void un_produit_en_stock(String reference, String nom, double prix, int stock) {
        when(catalogue.trouverParReference(reference))
                .thenReturn(new Produit(reference, nom, prix, stock));
    }

    @Given("le produit {string} n'existe pas dans le catalogue")
    public void un_produit_inconnu(String reference) {
        when(catalogue.trouverParReference(reference)).thenReturn(null);
    }

    @Given("un client de profil {string}")
    public void un_client_de_profil(String profil) {
        this.profil = ProfilClient.valueOf(profil);
    }

    @When("le client commande {int} unités du produit {string}")
    public void le_client_commande(int quantite, String reference) {
        Commande commande = new Commande("client@example.com", reference, quantite);
        try {
            recu = service.passerCommande(commande, profil);
        } catch (RuntimeException e) {
            erreur = e;
        }
    }

    @Then("la commande est acceptée")
    public void la_commande_est_acceptee() {
        assertNull(erreur, () -> "commande refusée de façon inattendue : " + erreur);
        assertNotNull(recu);
    }

    @Then("le reçu indique une quantité de {int}")
    public void le_recu_indique_une_quantite(int quantite) {
        assertEquals(quantite, recu.quantite());
    }

    @Then("le reçu indique un montant total de {double}")
    public void le_recu_indique_un_montant(double montant) {
        assertEquals(montant, recu.montantTotal(), 0.0001);
    }

    @Then("le reçu contient un message de confirmation")
    public void le_recu_contient_un_message() {
        assertNotNull(recu.messageConfirmation());
        assertTrue(!recu.messageConfirmation().isBlank());
    }

    @Then("la commande est refusée car le produit est inconnu")
    public void refusee_produit_inconnu() {
        assertNull(recu);
        assertTrue(erreur instanceof ProduitInconnuException,
                () -> "exception attendue ProduitInconnuException mais : " + erreur);
    }

    @Then("la commande est refusée car le stock est insuffisant")
    public void refusee_stock_insuffisant() {
        assertNull(recu);
        assertTrue(erreur instanceof StockInsuffisantException,
                () -> "exception attendue StockInsuffisantException mais : " + erreur);
    }
}
