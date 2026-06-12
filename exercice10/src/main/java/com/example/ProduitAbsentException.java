package com.example;

public class ProduitAbsentException extends RuntimeException {

    public ProduitAbsentException(String produitId, String commandeId) {
        super("Produit " + produitId + " absent de la commande " + commandeId);
    }
}
