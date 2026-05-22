package com.example;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTest {

    @Test
    void main_shouldPrintWelcomeAndCountFromOneToFive() {
        // Arrange : on capture la sortie standard
        PrintStream originalOut = System.out;
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captured));

        try {
            // Act
            Main.main(new String[]{});
        } finally {
            System.setOut(originalOut);
        }

        // Assert
        String output = captured.toString();
        assertTrue(output.contains("Hello and welcome!"));
        for (int i = 1; i <= 5; i++) {
            assertTrue(output.contains("i = " + i));
        }
    }
}
