package com.example;

public record RecuCommande(String referenceProduit, int quantite, double montantTotal,
                           String messageConfirmation) {
}
