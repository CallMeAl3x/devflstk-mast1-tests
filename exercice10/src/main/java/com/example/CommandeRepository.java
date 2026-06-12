package com.example;

public interface CommandeRepository {

    /**
     * @return la commande correspondante, ou {@code null} si elle n'existe pas.
     */
    Commande trouver(String id);

    void sauvegarder(Commande commande);
}
