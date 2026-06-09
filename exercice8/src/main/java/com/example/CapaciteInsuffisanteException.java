package com.example;

public class CapaciteInsuffisanteException extends RuntimeException {

    public CapaciteInsuffisanteException(String code, int participants, int capaciteMax) {
        super("Capacité insuffisante pour la salle " + code + " : " + participants
                + " participants pour une capacité de " + capaciteMax);
    }
}
