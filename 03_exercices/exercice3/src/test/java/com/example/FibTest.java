package com.example;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FibTest {

    // ---------- Range = 1 ----------

    @Test
    void getFibSeries_withRange1_shouldNotBeEmpty() {
        // Arrange
        Fib fib = new Fib(1);

        // Act
        List<Integer> result = fib.getFibSeries();

        // Assert
        assertFalse(result.isEmpty());
    }

    @Test
    void getFibSeries_withRange1_shouldContainOnlyZero() {
        Fib fib = new Fib(1);

        List<Integer> result = fib.getFibSeries();

        assertEquals(List.of(0), result);
    }

    // ---------- Range = 6 ----------

    @Test
    void getFibSeries_withRange6_shouldContainThree() {
        Fib fib = new Fib(6);

        List<Integer> result = fib.getFibSeries();

        assertTrue(result.contains(3));
    }

    @Test
    void getFibSeries_withRange6_shouldHaveSixElements() {
        Fib fib = new Fib(6);

        List<Integer> result = fib.getFibSeries();

        assertEquals(6, result.size());
    }

    @Test
    void getFibSeries_withRange6_shouldNotContainFour() {
        Fib fib = new Fib(6);

        List<Integer> result = fib.getFibSeries();

        assertFalse(result.contains(4));
    }

    @Test
    void getFibSeries_withRange6_shouldEqualExpectedSeries() {
        Fib fib = new Fib(6);

        List<Integer> result = fib.getFibSeries();

        assertEquals(List.of(0, 1, 1, 2, 3, 5), result);
    }

    @Test
    void getFibSeries_withRange6_shouldBeSortedAscending() {
        Fib fib = new Fib(6);

        List<Integer> result = fib.getFibSeries();

        for (int i = 0; i < result.size() - 1; i++) {
            assertTrue(result.get(i) <= result.get(i + 1),
                    "La liste doit être triée par ordre croissant");
        }
    }
}
