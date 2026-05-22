package com.example;

public class PasswordValidator {

    private static final String SPECIAL_CHARACTERS = "!@#$%";

    public boolean isValid(String password) {
        return getErrorMessage(password).equals("Password is valid");
    }

    public String getErrorMessage(String password) {
        if (password == null) {
            return "Password must not be null";
        }
        if (password.length() < 8) {
            return "Password must contain at least 8 characters";
        }
        if (!containsLowercase(password)) {
            return "Password must contain at least one lowercase letter";
        }
        if (!containsUppercase(password)) {
            return "Password must contain at least one uppercase letter";
        }
        if (!containsDigit(password)) {
            return "Password must contain at least one digit";
        }
        if (!containsSpecialCharacter(password)) {
            return "Password must contain at least one special character";
        }
        return "Password is valid";
    }

    private boolean containsLowercase(String password) {
        return password.chars().anyMatch(Character::isLowerCase);
    }

    private boolean containsUppercase(String password) {
        return password.chars().anyMatch(Character::isUpperCase);
    }

    private boolean containsDigit(String password) {
        return password.chars().anyMatch(Character::isDigit);
    }

    private boolean containsSpecialCharacter(String password) {
        return password.chars().anyMatch(c -> SPECIAL_CHARACTERS.indexOf(c) >= 0);
    }
}
