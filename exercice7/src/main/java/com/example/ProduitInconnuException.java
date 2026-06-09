package com.example;

public class ProduitInconnuException extends RuntimeException {

    public ProduitInconnuException(String reference) {
        super("Produit inconnu : " + reference);
    }
}
