package com.example;

/**
 * Source d'information produit. C'est cette dépendance qui est simulée avec Mockito
 * dans les tests BDD.
 */
public interface CatalogueProduit {

    /**
     * @return le produit correspondant à la référence, ou {@code null} s'il n'existe pas.
     */
    Produit trouverParReference(String reference);
}
