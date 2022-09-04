package org.bowlingsyndicate.domain;

import java.util.List;

/**
 * Class holds the score per frame, as a framescore, and as accumulated score.
 */
public final class BowlingFrameScore {
    private final int score;
    private final int accumulatedScore;

    public BowlingFrameScore(int score, int accumulatedScore) {
        this.score = score;
        this.accumulatedScore = accumulatedScore;
    }

    public int getScore() {
        return this.score;
    }

    public int getAccumulatedScore() {
        return accumulatedScore;
    }
}
