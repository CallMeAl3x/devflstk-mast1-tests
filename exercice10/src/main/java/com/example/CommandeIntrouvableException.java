package com.example;

public class CommandeIntrouvableException extends RuntimeException {

    public CommandeIntrouvableException(String id) {
        super("Commande introuvable : " + id);
    }
}
