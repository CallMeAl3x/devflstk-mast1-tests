package com.example;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RechercheVilleTest {

    // ---------- Étape 1 : moins de 2 caractères -> NotFoundException ----------

    @Test
    void rechercher_shouldThrowNotFound_whenSearchTextHasOneCharacter() {
        // Arrange
        RechercheVille recherche = new RechercheVille();

        // Act + Assert
        assertThrows(NotFoundException.class, () -> recherche.Rechercher("P"));
    }

    @Test
    void rechercher_shouldThrowNotFound_whenSearchTextIsEmpty() {
        RechercheVille recherche = new RechercheVille();

        assertThrows(NotFoundException.class, () -> recherche.Rechercher(""));
    }

    @Test
    void rechercher_shouldThrowNotFound_whenSearchTextIsNull() {
        RechercheVille recherche = new RechercheVille();

        assertThrows(NotFoundException.class, () -> recherche.Rechercher(null));
    }

    // ---------- Étape 2 : >= 2 caractères -> villes correspondantes ----------

    @Test
    void rechercher_shouldReturnMatchingCities_forTwoCharacters() {
        RechercheVille recherche = new RechercheVille();

        List<String> result = recherche.Rechercher("Va");

        assertEquals(List.of("Valence", "Vancouver"), result);
    }

    // ---------- Étape 3 : insensible à la casse ----------

    @Test
    void rechercher_shouldBeCaseInsensitive() {
        RechercheVille recherche = new RechercheVille();

        List<String> result = recherche.Rechercher("va");

        assertEquals(List.of("Valence", "Vancouver"), result);
    }

    // ---------- Étape 4 : recherche sur une partie du nom ----------

    @Test
    void rechercher_shouldMatchPartOfCityName() {
        RechercheVille recherche = new RechercheVille();

        List<String> result = recherche.Rechercher("ape");

        assertEquals(List.of("Budapest"), result);
    }

    // ---------- Étape 5 : "*" renvoie toutes les villes ----------

    @Test
    void rechercher_shouldReturnAllCities_whenSearchTextIsAsterisk() {
        RechercheVille recherche = new RechercheVille();

        List<String> result = recherche.Rechercher("*");

        assertEquals(16, result.size());
    }
}
