package com.example;

public class IdentifiantDejaUtiliseException extends RuntimeException {

    public IdentifiantDejaUtiliseException(String nomUtilisateur) {
        super("Identifiant déjà utilisé : " + nomUtilisateur);
    }
}
