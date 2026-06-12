package com.example.steps;

import com.example.Commande;
import com.example.CommandeIntrouvableException;
import com.example.CommandeRepository;
import com.example.ConnexionEchoueeException;
import com.example.IdentifiantDejaUtiliseException;
import com.example.Produit;
import com.example.ProduitAbsentException;
import com.example.ProduitRepository;
import com.example.ServiceCatalogue;
import com.example.ServiceCommande;
import com.example.ServiceCompte;
import com.example.Utilisateur;
import com.example.UtilisateurRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BoutiqueStepDefinitions {

    private UtilisateurRepository utilisateurRepo;
    private ProduitRepository produitRepo;
    private CommandeRepository commandeRepo;

    private ServiceCompte serviceCompte;
    private ServiceCatalogue serviceCatalogue;
    private ServiceCommande serviceCommande;

    private final Map<String, Commande> commandes = new HashMap<>();
    private String message;
    private List<Produit> resultats;
    private RuntimeException erreur;

    @Before
    public void setUp() {
        utilisateurRepo = mock(UtilisateurRepository.class);
        produitRepo = mock(ProduitRepository.class);
        commandeRepo = mock(CommandeRepository.class);

        serviceCompte = new ServiceCompte(utilisateurRepo);
        serviceCatalogue = new ServiceCatalogue(produitRepo);
        serviceCommande = new ServiceCommande(commandeRepo);

        commandes.clear();
        message = null;
        resultats = null;
        erreur = null;
    }

    // ---------- US1 : création de compte ----------

    @Given("le compte {string} n'existe pas")
    public void le_compte_n_existe_pas(String nom) {
        when(utilisateurRepo.existeParNomUtilisateur(nom)).thenReturn(false);
    }

    @Given("le compte {string} existe déjà")
    public void le_compte_existe_deja(String nom) {
        when(utilisateurRepo.existeParNomUtilisateur(nom)).thenReturn(true);
    }

    @When("l'utilisateur s'inscrit avec l'email {string}, le nom {string} et le mot de passe {string}")
    public void l_utilisateur_s_inscrit(String email, String nom, String motDePasse) {
        executer(() -> message = serviceCompte.inscrire(email, nom, motDePasse));
    }

    @Then("l'inscription est confirmée")
    public void l_inscription_est_confirmee() {
        assertNull(erreur, () -> "inscription en échec inattendu : " + erreur);
        assertNotNull(message);
    }

    @Then("une erreur d'identifiant déjà utilisé est renvoyée")
    public void erreur_identifiant_deja_utilise() {
        assertTrue(erreur instanceof IdentifiantDejaUtiliseException,
                () -> "attendu IdentifiantDejaUtiliseException mais : " + erreur);
    }

    // ---------- US2 : connexion ----------

    @Given("le compte {string} existe avec le mot de passe {string}")
    public void le_compte_existe_avec_mot_de_passe(String nom, String motDePasse) {
        when(utilisateurRepo.trouverParNomUtilisateur(nom))
                .thenReturn(new Utilisateur(nom + "@mail.com", nom, motDePasse));
    }

    @When("l'utilisateur se connecte avec le nom {string} et le mot de passe {string}")
    public void l_utilisateur_se_connecte(String nom, String motDePasse) {
        executer(() -> message = serviceCompte.connecter(nom, motDePasse));
    }

    @Then("l'utilisateur est redirigé vers la page d'accueil")
    public void redirige_vers_accueil() {
        assertNull(erreur, () -> "connexion en échec inattendu : " + erreur);
        assertEquals("accueil", message);
    }

    @Then("un message d'erreur de connexion est affiché")
    public void message_erreur_connexion() {
        assertTrue(erreur instanceof ConnexionEchoueeException,
                () -> "attendu ConnexionEchoueeException mais : " + erreur);
    }

    // ---------- US3 / US4 : recherche et catégorie ----------

    @Given("un catalogue contenant les produits suivants:")
    public void un_catalogue(DataTable table) {
        List<Produit> produits = new ArrayList<>();
        List<Map<String, String>> lignes = table.asMaps();
        for (int i = 0; i < lignes.size(); i++) {
            Map<String, String> l = lignes.get(i);
            produits.add(new Produit(
                    "P" + (i + 1),
                    l.get("nom"),
                    Double.parseDouble(l.get("prix")),
                    l.get("categorie")));
        }
        when(produitRepo.tousLesProduits()).thenReturn(produits);
    }

    @When("l'utilisateur recherche le mot-clé {string}")
    public void recherche_mot_cle(String motCle) {
        resultats = serviceCatalogue.rechercherParMotCle(motCle);
    }

    @When("l'utilisateur recherche les produits à {int} € maximum")
    public void recherche_prix_max(int prixMax) {
        resultats = serviceCatalogue.rechercherParPrixMax(prixMax);
    }

    @When("l'utilisateur sélectionne la catégorie {string}")
    public void selectionne_categorie(String categorie) {
        resultats = serviceCatalogue.produitsParCategorie(categorie);
    }

    @Then("la recherche retourne {int} produit(s)")
    public void la_recherche_retourne(int nombre) {
        assertNotNull(resultats);
        assertEquals(nombre, resultats.size());
    }

    @Then("la recherche contient le produit {string}")
    public void la_recherche_contient(String nom) {
        assertTrue(resultats.stream().anyMatch(p -> p.nom().equals(nom)),
                () -> "produit absent des résultats : " + nom);
    }

    // ---------- US5 / US6 / US7 : commande ----------

    @Given("une commande {string} vide")
    public void une_commande_vide(String id) {
        enregistrerCommande(new Commande(id));
    }

    @Given("une commande {string} contenant {int} unité(s) du produit {string}")
    public void une_commande_contenant(String id, int quantite, String nomProduit) {
        Commande commande = new Commande(id);
        for (int i = 0; i < quantite; i++) {
            commande.ajouter(produit(nomProduit));
        }
        enregistrerCommande(commande);
    }

    @Given("la commande {string} n'existe pas")
    public void la_commande_n_existe_pas(String id) {
        when(commandeRepo.trouver(id)).thenReturn(null);
    }

    @When("l'utilisateur ajoute le produit {string} à la commande {string}")
    public void ajoute_produit(String nomProduit, String commandeId) {
        executer(() -> message = serviceCommande.ajouterProduit(commandeId, produit(nomProduit)));
    }

    @When("l'utilisateur supprime le produit {string} de la commande {string}")
    public void supprime_produit(String nomProduit, String commandeId) {
        executer(() -> serviceCommande.retirerProduit(commandeId, nomProduit));
    }

    @When("l'utilisateur valide la commande {string}")
    public void valide_commande(String commandeId) {
        executer(() -> message = serviceCommande.valider(commandeId));
    }

    @Then("l'ajout est confirmé")
    public void l_ajout_est_confirme() {
        assertNull(erreur, () -> "ajout en échec inattendu : " + erreur);
        assertNotNull(message);
    }

    @Then("la commande {string} contient {int} unité(s) du produit {string}")
    public void la_commande_contient(String id, int quantite, String nomProduit) {
        assertEquals(quantite, commandes.get(id).quantiteDe(nomProduit));
    }

    @Then("la commande {string} ne contient plus le produit {string}")
    public void la_commande_ne_contient_plus(String id, String nomProduit) {
        assertFalse(commandes.get(id).contient(nomProduit));
    }

    @Then("une erreur de commande introuvable est renvoyée")
    public void erreur_commande_introuvable() {
        assertTrue(erreur instanceof CommandeIntrouvableException,
                () -> "attendu CommandeIntrouvableException mais : " + erreur);
    }

    @Then("une erreur de produit absent est renvoyée")
    public void erreur_produit_absent() {
        assertTrue(erreur instanceof ProduitAbsentException,
                () -> "attendu ProduitAbsentException mais : " + erreur);
    }

    @Then("la commande est confirmée")
    public void la_commande_est_confirmee() {
        assertNull(erreur, () -> "validation en échec inattendu : " + erreur);
        assertNotNull(message);
    }

    // ---------- utilitaires ----------

    /** Un produit identifié par son nom (suffisant pour les scénarios de commande). */
    private Produit produit(String nom) {
        return new Produit(nom, nom, 0.0, "divers");
    }

    private void enregistrerCommande(Commande commande) {
        commandes.put(commande.getId(), commande);
        when(commandeRepo.trouver(commande.getId())).thenReturn(commande);
    }

    /** Exécute une action métier en capturant l'éventuelle exception de refus. */
    private void executer(Runnable action) {
        try {
            action.run();
        } catch (RuntimeException e) {
            erreur = e;
        }
    }
}
