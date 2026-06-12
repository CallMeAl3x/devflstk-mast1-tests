package com.example;

/**
 * Génère le nombre de quilles tombées pour un lancer. Cette dépendance porte
 * l'aléatoire du jeu : elle est simulée avec Mockito dans les tests afin de
 * rendre les scénarios déterministes.
 */
public interface IGenerateur {

    int randomPin(int max);
}
