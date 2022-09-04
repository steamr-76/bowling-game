package org.bowlingsyndicate.domain;

public class RollScore {
    public enum Bonus {None, Strike, Spare}
    private final int score;
    private final Bonus bonus;

    RollScore(int score, Bonus bonus) {
        this.score = score;
        this.bonus = bonus;
    }

    public int getRollScore() {
        return score;
    }

    public Bonus getBonus() {
        return bonus;
    }
}