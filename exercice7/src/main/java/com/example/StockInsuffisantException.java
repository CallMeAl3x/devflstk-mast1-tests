package com.example;

public class StockInsuffisantException extends RuntimeException {

    public StockInsuffisantException(String reference, int demande, int disponible) {
        super("Stock insuffisant pour " + reference + " : demandé " + demande
                + ", disponible " + disponible);
    }
}
