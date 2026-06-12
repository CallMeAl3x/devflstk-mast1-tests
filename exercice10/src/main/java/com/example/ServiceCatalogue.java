package com.example;

import java.util.List;

public class ServiceCatalogue {

    private final ProduitRepository produits;

    public ServiceCatalogue(ProduitRepository produits) {
        this.produits = produits;
    }

    /** US3 — recherche par mot-clé (insensible à la casse, sur le nom). */
    public List<Produit> rechercherParMotCle(String motCle) {
        String recherche = motCle.toLowerCase();
        return produits.tousLesProduits().stream()
                .filter(p -> p.nom().toLowerCase().contains(recherche))
                .toList();
    }

    /** US3 — recherche par prix maximum. */
    public List<Produit> rechercherParPrixMax(double prixMax) {
        return produits.tousLesProduits().stream()
                .filter(p -> p.prix() <= prixMax)
                .toList();
    }

    /** US4 — produits d'une catégorie. */
    public List<Produit> produitsParCategorie(String categorie) {
        return produits.tousLesProduits().stream()
                .filter(p -> p.categorie().equalsIgnoreCase(categorie))
                .toList();
    }
}
