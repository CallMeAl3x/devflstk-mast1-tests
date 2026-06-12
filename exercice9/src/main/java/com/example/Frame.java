package com.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Une série (frame) du jeu de bowling. Le nombre de quilles de chaque lancer
 * est fourni par {@link IGenerateur} (mocké dans les tests).
 *
 * <p>{@link #makeRoll()} tente un lancer : il renvoie {@code true} si le lancer
 * est autorisé (et met alors à jour le score), {@code false} s'il est refusé par
 * les règles de la série.</p>
 */
public class Frame {

    private static final int NB_QUILLES = 10;

    private int score;
    private final boolean lastFrame;
    private final IGenerateur generateur;
    private final List<Roll> rolls;

    public Frame(IGenerateur generateur, boolean lastFrame) {
        this.lastFrame = lastFrame;
        this.generateur = generateur;
        this.rolls = new ArrayList<>();
    }

    public boolean makeRoll() {
        if (!peutLancer()) {
            return false;
        }
        int pins = generateur.randomPin(quillesDisponibles());
        rolls.add(new Roll(pins));
        score += pins;
        return true;
    }

    public int getScore() {
        return score;
    }

    public List<Roll> getRolls() {
        return List.copyOf(rolls);
    }

    /** Règles d'autorisation d'un nouveau lancer. */
    private boolean peutLancer() {
        int n = rolls.size();
        if (!lastFrame) {
            // Série standard : 2 lancers max, et le strike clôt la série.
            if (n >= 2) {
                return false;
            }
            return !(n == 1 && estStrike(rolls.get(0)));
        }
        // Série finale : 3 lancers max, le 3e n'est permis qu'après strike ou spare.
        if (n >= 3) {
            return false;
        }
        if (n == 2) {
            return strikeOuSpare();
        }
        return true;
    }

    /** Nombre de quilles encore debout, fourni comme borne au générateur. */
    private int quillesDisponibles() {
        int n = rolls.size();
        if (n == 0) {
            return NB_QUILLES;
        }
        if (!lastFrame) {
            return NB_QUILLES - rolls.get(0).getPins();
        }
        if (n == 1) {
            return estStrike(rolls.get(0)) ? NB_QUILLES : NB_QUILLES - rolls.get(0).getPins();
        }
        // 3e lancer de la série finale : les quilles sont remises selon le contexte.
        Roll premier = rolls.get(0);
        Roll second = rolls.get(1);
        if (estStrike(premier)) {
            return estStrike(second) ? NB_QUILLES : NB_QUILLES - second.getPins();
        }
        // Spare sur les deux premiers lancers : nouvelle volée complète.
        return NB_QUILLES;
    }

    private boolean estStrike(Roll roll) {
        return roll.getPins() == NB_QUILLES;
    }

    private boolean strikeOuSpare() {
        Roll premier = rolls.get(0);
        Roll second = rolls.get(1);
        if (estStrike(premier)) {
            return true;
        }
        return premier.getPins() + second.getPins() == NB_QUILLES;
    }
}
