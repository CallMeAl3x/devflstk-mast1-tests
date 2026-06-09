package com.example;

public class PeriodeInvalideException extends RuntimeException {

    public PeriodeInvalideException() {
        super("Période invalide : la date de fin doit être strictement après la date de début");
    }
}
