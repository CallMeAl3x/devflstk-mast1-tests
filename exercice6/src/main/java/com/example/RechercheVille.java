package com.example;

import java.util.List;

public class RechercheVille {

    private final List<String> villes = List.of(
            "Paris", "Budapest", "Skopje", "Rotterdam", "Valence", "Vancouver",
            "Amsterdam", "Vienne", "Sydney", "New York", "Londres", "Bangkok",
            "Hong Kong", "Dubaï", "Rome", "Istanbul"
    );

    public List<String> Rechercher(String mot) {
        // Étape 5 : "*" renvoie toutes les villes
        if ("*".equals(mot)) {
            return List.copyOf(villes);
        }
        // Étape 1 : moins de 2 caractères -> exception
        if (mot == null || mot.length() < 2) {
            throw new NotFoundException("Le texte de recherche doit contenir au moins 2 caractères");
        }
        // Étapes 2, 3, 4 : recherche insensible à la casse sur une partie du nom
        String recherche = mot.toLowerCase();
        return villes.stream()
                .filter(ville -> ville.toLowerCase().contains(recherche))
                .toList();
    }
}
