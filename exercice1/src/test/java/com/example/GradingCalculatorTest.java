package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GradingCalculatorTest {

    @Test
    void shouldReturnA_whenScore95AndAttendance90() {
        // Arrange
        GradingCalculator calculator = new GradingCalculator(95, 90);

        // Act
        char grade = calculator.getGrade();

        // Assert
        assertEquals('A', grade);
    }

    @Test
    void shouldReturnB_whenScore85AndAttendance90() {
        GradingCalculator calculator = new GradingCalculator(85, 90);

        char grade = calculator.getGrade();

        assertEquals('B', grade);
    }

    @Test
    void shouldReturnC_whenScore65AndAttendance90() {
        GradingCalculator calculator = new GradingCalculator(65, 90);

        char grade = calculator.getGrade();

        assertEquals('C', grade);
    }

    @Test
    void shouldReturnB_whenScore95AndAttendance65() {
        GradingCalculator calculator = new GradingCalculator(95, 65);

        char grade = calculator.getGrade();

        assertEquals('B', grade);
    }

    @Test
    void shouldReturnF_whenScore95AndAttendance55() {
        GradingCalculator calculator = new GradingCalculator(95, 55);

        char grade = calculator.getGrade();

        assertEquals('F', grade);
    }

    @Test
    void shouldReturnF_whenScore65AndAttendance55() {
        GradingCalculator calculator = new GradingCalculator(65, 55);

        char grade = calculator.getGrade();

        assertEquals('F', grade);
    }

    @Test
    void shouldReturnF_whenScore50AndAttendance90() {
        GradingCalculator calculator = new GradingCalculator(50, 90);

        char grade = calculator.getGrade();

        assertEquals('F', grade);
    }

    @ParameterizedTest(name = "Score {0}, Présence {1} => Note {2}")
    @CsvSource({
            "95, 90, A",
            "85, 90, B",
            "65, 90, C",
            "95, 65, B",
            "95, 55, F",
            "65, 55, F",
            "50, 90, F"
    })
    void shouldReturnExpectedGrade(int score, int attendance, char expectedGrade) {
        GradingCalculator calculator = new GradingCalculator(score, attendance);

        char grade = calculator.getGrade();

        assertEquals(expectedGrade, grade);
    }
}
