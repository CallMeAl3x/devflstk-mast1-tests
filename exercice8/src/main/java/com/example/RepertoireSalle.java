package com.example;

/**
 * Source d'information sur les salles, simulée avec Mockito dans les tests.
 */
public interface RepertoireSalle {

    /**
     * @return la salle correspondant au code, ou {@code null} si elle n'existe pas.
     */
    Salle trouverParCode(String code);
}
