package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class DiceScoreTest {

    @Mock
    private Ide de;

    @InjectMocks
    private DiceScore diceScore;

    @BeforeEach
    void setUp() {
        // Initialise le mock @Mock et l'injecte dans @InjectMocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getScore_whenBothDiceAreEqual_shouldReturnValueTimes2Plus10() {
        // Arrange : les deux lancers donnent 4
        when(de.getRoll()).thenReturn(4, 4);

        // Act
        int score = diceScore.getScore();

        // Assert : 4 * 2 + 10
        assertEquals(18, score);
    }

    @Test
    void getScore_whenBothDiceAreEqualToSix_shouldReturn30() {
        when(de.getRoll()).thenReturn(6, 6);

        int score = diceScore.getScore();

        assertEquals(30, score);
    }

    @Test
    void getScore_whenDiceDiffer_shouldReturnHighest_firstLowerThanSecond() {
        when(de.getRoll()).thenReturn(2, 5);

        int score = diceScore.getScore();

        assertEquals(5, score);
    }

    @Test
    void getScore_whenDiceDiffer_shouldReturnHighest_firstHigherThanSecond() {
        when(de.getRoll()).thenReturn(5, 2);

        int score = diceScore.getScore();

        assertEquals(5, score);
    }
}
