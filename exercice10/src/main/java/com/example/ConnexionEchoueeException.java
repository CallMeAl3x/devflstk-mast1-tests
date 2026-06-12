package com.example;

public class ConnexionEchoueeException extends RuntimeException {

    public ConnexionEchoueeException() {
        super("Nom d'utilisateur ou mot de passe incorrect");
    }
}
