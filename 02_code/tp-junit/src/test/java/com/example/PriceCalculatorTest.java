package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PriceCalculatorTest {

    private static final double DELTA = 0.0001;

    // ---------- Cas nominaux ----------

    @Test
    void calculateTotalPrice_shouldReturn30() {
        // Arrange
        PriceCalculator calculator = new PriceCalculator();

        // Act
        double result = calculator.calculateTotalPrice(10.0, 3);

        // Assert
        assertEquals(30.0, result, DELTA);
    }

    @Test
    void applyDiscount_shouldReturn80() {
        PriceCalculator calculator = new PriceCalculator();

        double result = calculator.applyDiscount(100.0, 0.20);

        assertEquals(80.0, result, DELTA);
    }

    @Test
    void calculateVat_shouldReturn20() {
        PriceCalculator calculator = new PriceCalculator();

        double result = calculator.calculateVat(100.0, 0.20);

        assertEquals(20.0, result, DELTA);
    }

    @Test
    void calculatePriceWithVat_shouldReturn120() {
        PriceCalculator calculator = new PriceCalculator();

        double result = calculator.calculatePriceWithVat(100.0, 0.20);

        assertEquals(120.0, result, DELTA);
    }

    // ---------- Cas d'erreur ----------

    @Test
    void calculateTotalPrice_shouldThrow_whenUnitPriceIsNegative() {
        PriceCalculator calculator = new PriceCalculator();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> calculator.calculateTotalPrice(-10.0, 3)
        );

        assertEquals("Le prix unitaire ne doit pas être négatif", exception.getMessage());
    }

    @Test
    void calculateTotalPrice_shouldThrow_whenQuantityIsNegative() {
        PriceCalculator calculator = new PriceCalculator();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> calculator.calculateTotalPrice(10.0, -3)
        );

        assertEquals("La quantité ne doit pas être négative", exception.getMessage());
    }

    @Test
    void applyDiscount_shouldThrow_whenDiscountRateIsNegative() {
        PriceCalculator calculator = new PriceCalculator();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> calculator.applyDiscount(100.0, -0.20)
        );

        assertEquals("Le taux de remise ne doit pas être négatif", exception.getMessage());
    }

    @Test
    void calculateVat_shouldThrow_whenVatRateIsNegative() {
        PriceCalculator calculator = new PriceCalculator();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> calculator.calculateVat(100.0, -0.20)
        );

        assertEquals("Le taux de TVA ne doit pas être négatif", exception.getMessage());
    }

    @Test
    void applyDiscount_shouldThrow_whenPriceIsNegative() {
        PriceCalculator calculator = new PriceCalculator();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> calculator.applyDiscount(-100.0, 0.20)
        );

        assertEquals("Le prix ne doit pas être négatif", exception.getMessage());
    }

    @Test
    void applyDiscount_shouldThrow_whenDiscountRateExceedsOne() {
        PriceCalculator calculator = new PriceCalculator();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> calculator.applyDiscount(100.0, 1.5)
        );

        assertEquals("Le taux de remise ne doit pas dépasser 100%", exception.getMessage());
    }

    @Test
    void applyDiscount_shouldReturnZero_whenDiscountRateIsExactlyOne() {
        PriceCalculator calculator = new PriceCalculator();

        double result = calculator.applyDiscount(100.0, 1.0);

        assertEquals(0.0, result, DELTA);
    }

    @Test
    void calculateVat_shouldThrow_whenPriceIsNegative() {
        PriceCalculator calculator = new PriceCalculator();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> calculator.calculateVat(-100.0, 0.20)
        );

        assertEquals("Le prix ne doit pas être négatif", exception.getMessage());
    }
}
