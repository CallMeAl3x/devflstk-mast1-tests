package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FrameTest {

    private static final boolean SERIE_STANDARD = false;
    private static final boolean SERIE_FINALE = true;

    private final IGenerateur generateur = mock(IGenerateur.class);

    // ---------- Série standard ----------

    @Test
    void shouldIncreaseScoreWhenFirstRollIsMadeInStandardFrame() {
        when(generateur.randomPin(anyInt())).thenReturn(3);
        Frame frame = new Frame(generateur, SERIE_STANDARD);

        assertTrue(frame.makeRoll());
        assertEquals(3, frame.getScore());
    }

    @Test
    void shouldIncreaseScoreWhenSecondRollIsMadeInStandardFrame() {
        when(generateur.randomPin(anyInt())).thenReturn(3, 4);
        Frame frame = new Frame(generateur, SERIE_STANDARD);

        frame.makeRoll();
        assertTrue(frame.makeRoll());
        assertEquals(7, frame.getScore());
    }

    @Test
    void shouldRejectSecondRollWhenStandardFrameStartsWithStrike() {
        when(generateur.randomPin(anyInt())).thenReturn(10);
        Frame frame = new Frame(generateur, SERIE_STANDARD);

        assertTrue(frame.makeRoll());
        assertFalse(frame.makeRoll());
        assertEquals(10, frame.getScore());
    }

    @Test
    void shouldRejectThirdRollWhenStandardFrameAlreadyHasTwoRolls() {
        when(generateur.randomPin(anyInt())).thenReturn(3, 4);
        Frame frame = new Frame(generateur, SERIE_STANDARD);

        frame.makeRoll();
        frame.makeRoll();
        assertFalse(frame.makeRoll());
    }

    // ---------- Série finale (dernier round) ----------

    @Test
    void shouldIncreaseScoreWhenSecondRollIsMadeAfterStrikeInLastFrame() {
        when(generateur.randomPin(anyInt())).thenReturn(10, 5);
        Frame frame = new Frame(generateur, SERIE_FINALE);

        frame.makeRoll();
        assertTrue(frame.makeRoll());
        assertEquals(15, frame.getScore());
    }

    @Test
    void shouldAcceptThirdRollWhenLastFrameStartsWithStrike() {
        when(generateur.randomPin(anyInt())).thenReturn(10, 5, 3);
        Frame frame = new Frame(generateur, SERIE_FINALE);

        frame.makeRoll();
        frame.makeRoll();
        assertTrue(frame.makeRoll());
    }

    @Test
    void shouldAcceptSecondRollWhenLastFrameStartsWithStrike() {
        when(generateur.randomPin(anyInt())).thenReturn(10, 5);
        Frame frame = new Frame(generateur, SERIE_FINALE);

        frame.makeRoll();
        assertTrue(frame.makeRoll());
    }

    @Test
    void shouldIncreaseScoreWhenThirdRollIsMadeAfterStrikeInLastFrame() {
        when(generateur.randomPin(anyInt())).thenReturn(10, 5, 3);
        Frame frame = new Frame(generateur, SERIE_FINALE);

        frame.makeRoll();
        frame.makeRoll();
        assertTrue(frame.makeRoll());
        assertEquals(18, frame.getScore());
    }

    @Test
    void shouldAcceptThirdRollWhenLastFrameStartsWithSpare() {
        when(generateur.randomPin(anyInt())).thenReturn(4, 6, 5);
        Frame frame = new Frame(generateur, SERIE_FINALE);

        frame.makeRoll();
        frame.makeRoll();
        assertTrue(frame.makeRoll());
    }

    @Test
    void shouldIncreaseScoreWhenThirdRollIsMadeAfterSpareInLastFrame() {
        when(generateur.randomPin(anyInt())).thenReturn(4, 6, 5);
        Frame frame = new Frame(generateur, SERIE_FINALE);

        frame.makeRoll();
        frame.makeRoll();
        assertTrue(frame.makeRoll());
        assertEquals(15, frame.getScore());
    }

    @Test
    void shouldRejectThirdRollWhenLastFrameHasNoStrikeOrSpare() {
        when(generateur.randomPin(anyInt())).thenReturn(4, 3);
        Frame frame = new Frame(generateur, SERIE_FINALE);

        frame.makeRoll();
        frame.makeRoll();
        assertFalse(frame.makeRoll());
    }

    @Test
    void shouldRejectFourthRollInLastFrame() {
        when(generateur.randomPin(anyInt())).thenReturn(10, 5, 3);
        Frame frame = new Frame(generateur, SERIE_FINALE);

        frame.makeRoll();
        frame.makeRoll();
        frame.makeRoll();
        assertFalse(frame.makeRoll());
    }
}
