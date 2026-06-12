package com.example;

public class LigneCommande {

    private final Produit produit;
    private int quantite;

    public LigneCommande(Produit produit, int quantite) {
        this.produit = produit;
        this.quantite = quantite;
    }

    public Produit getProduit() {
        return produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void augmenter() {
        quantite++;
    }

    public void diminuer() {
        quantite--;
    }
}
