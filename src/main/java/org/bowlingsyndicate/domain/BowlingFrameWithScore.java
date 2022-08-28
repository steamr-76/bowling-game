package org.bowlingsyndicate.domain;

import java.util.List;

public class BowlingFrameWithScore extends BowlingFrame {
    private final int score;

    public BowlingFrameWithScore(BowlingFrame bowlingFrame, int score) {
        super(bowlingFrame.getFrameResults());
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }
}
