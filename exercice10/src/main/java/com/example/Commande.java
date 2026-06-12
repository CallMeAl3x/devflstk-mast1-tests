package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Commande {

    private final String id;
    private final List<LigneCommande> lignes = new ArrayList<>();

    public Commande(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public List<LigneCommande> getLignes() {
        return List.copyOf(lignes);
    }

    /** Ajoute un produit : +1 s'il est déjà présent, sinon une nouvelle ligne à 1. */
    public void ajouter(Produit produit) {
        ligne(produit.id())
                .ifPresentOrElse(
                        LigneCommande::augmenter,
                        () -> lignes.add(new LigneCommande(produit, 1)));
    }

    /** Retire un produit : -1 s'il en reste plusieurs, sinon la ligne disparaît. */
    public void retirer(String produitId) {
        LigneCommande ligne = ligne(produitId)
                .orElseThrow(() -> new ProduitAbsentException(produitId, id));
        if (ligne.getQuantite() > 1) {
            ligne.diminuer();
        } else {
            lignes.remove(ligne);
        }
    }

    public boolean contient(String produitId) {
        return ligne(produitId).isPresent();
    }

    public int quantiteDe(String produitId) {
        return ligne(produitId).map(LigneCommande::getQuantite).orElse(0);
    }

    private Optional<LigneCommande> ligne(String produitId) {
        return lignes.stream()
                .filter(l -> l.getProduit().id().equals(produitId))
                .findFirst();
    }
}
