package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordValidatorTest {

    // ---------- Tests classiques ----------

    @Test
    void isValid_shouldReturnTrue_forAValidPassword() {
        // Arrange
        PasswordValidator validator = new PasswordValidator();

        // Act
        boolean result = validator.isValid("Password1!");

        // Assert
        assertTrue(result);
    }

    @Test
    void isValid_shouldReturnFalse_whenPasswordIsNull() {
        PasswordValidator validator = new PasswordValidator();

        boolean result = validator.isValid(null);

        assertFalse(result);
    }

    @Test
    void isValid_shouldReturnFalse_whenPasswordIsTooShort() {
        PasswordValidator validator = new PasswordValidator();

        boolean result = validator.isValid("short1!");

        assertFalse(result);
    }

    // ---------- Tests paramétrés : @CsvSource ----------

    @ParameterizedTest(name = "[{index}] \"{0}\" => valide = {1}")
    @CsvSource({
            "Password1!, true",
            "Admin2024@, true",
            "short1!,    false",
            "PASSWORD1!, false",
            "password1!, false",
            "Password!,  false",
            "Password1,  false"
    })
    void isValid_shouldMatchExpectedResult(String password, boolean expected) {
        PasswordValidator validator = new PasswordValidator();

        assertEquals(expected, validator.isValid(password));
    }

    // ---------- Test paramétré : @ValueSource ----------

    @ParameterizedTest(name = "[{index}] \"{0}\" est valide")
    @ValueSource(strings = {"Password1!", "Admin2024@", "Secure9#", "MyPass1$"})
    void isValid_shouldReturnTrue_forSeveralValidPasswords(String password) {
        PasswordValidator validator = new PasswordValidator();

        assertTrue(validator.isValid(password));
    }

    // ---------- Test paramétré : @MethodSource ----------

    static Stream<Arguments> passwordsAndErrorMessages() {
        return Stream.of(
                Arguments.of("short1!",    "Password must contain at least 8 characters"),
                Arguments.of("PASSWORD1!", "Password must contain at least one lowercase letter"),
                Arguments.of("password1!", "Password must contain at least one uppercase letter"),
                Arguments.of("Password!",  "Password must contain at least one digit"),
                Arguments.of("Password1",  "Password must contain at least one special character"),
                Arguments.of("Password1!", "Password is valid")
        );
    }

    @ParameterizedTest(name = "[{index}] \"{0}\" => \"{1}\"")
    @MethodSource("passwordsAndErrorMessages")
    void getErrorMessage_shouldReturnExpectedMessage(String password, String expectedMessage) {
        PasswordValidator validator = new PasswordValidator();

        assertEquals(expectedMessage, validator.getErrorMessage(password));
    }

    // ---------- Vérification du message pour null ----------

    @Test
    void getErrorMessage_shouldReturnNullMessage_whenPasswordIsNull() {
        PasswordValidator validator = new PasswordValidator();

        assertEquals("Password must not be null", validator.getErrorMessage(null));
    }

    // ---------- Bonus : @NullAndEmptySource ----------

    @ParameterizedTest(name = "[{index}] valeur null ou vide => invalide")
    @NullAndEmptySource
    void isValid_shouldReturnFalse_whenPasswordIsNullOrEmpty(String password) {
        PasswordValidator validator = new PasswordValidator();

        assertFalse(validator.isValid(password));
    }
}
