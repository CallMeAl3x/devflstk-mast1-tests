package com.example;

public class SalleInconnueException extends RuntimeException {

    public SalleInconnueException(String code) {
        super("Salle inconnue : " + code);
    }
}
