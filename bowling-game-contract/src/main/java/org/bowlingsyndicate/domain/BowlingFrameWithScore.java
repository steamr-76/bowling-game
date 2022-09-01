package org.bowlingsyndicate.domain;

public final class BowlingFrameWithScore extends BowlingFrame {
    private final int score;

    public BowlingFrameWithScore(BowlingFrame bowlingFrame, int score) {
        super(bowlingFrame.getFrameResults());
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }
}
