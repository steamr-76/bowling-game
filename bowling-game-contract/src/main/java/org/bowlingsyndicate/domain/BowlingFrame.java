package org.bowlingsyndicate.domain;

import org.bowlingsyndicate.error.BowlingScoreException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.bowlingsyndicate.domain.RollScore.Bonus.*;

public class BowlingFrame {
    private final List<RollScore> frameResults = new ArrayList<>(3);
    private final boolean lastFrame;
    private boolean completeFrame = false;


    /**
     * Hidden constructor to enable either of modes (should be refactored into an hierarchy though).
     *
     * @param lastFrame duh?
     */
    private BowlingFrame(boolean lastFrame) {
        this.lastFrame = lastFrame;
    }

    public static BowlingFrame emptyFrame() {
        return new BowlingFrame(false);
    }

    public static BowlingFrame emptyLastFrameFrame() {
        return new BowlingFrame(true);
    }

    /**
     * Adds score if the frame score is valid and is not completed. It must also
     * fit in the frame according to framerules.
     *
     * @param pins amount of pins taken down.
     * @return this instance for chaining calls.
     */
    public BowlingFrame addRollResult(int pins) {
        if(isCompleteFrame()) {
            throw new BowlingScoreException("Not allowed to add rolls to complete frames");
        }
        if(pins > 10 || pins < 0) {
            throw new BowlingScoreException("Illegal score value: " + pins);
        }

        addFrameRoll(pins);
        determineCompleteStatus();
        return this;
    }

    private void addFrameRoll(int roll) {
        int rolls = frameResults.size() + 1;
        int newTotScore = roll + frameResults.stream().mapToInt(RollScore::getRollScore).sum();

        boolean isSpare = rolls == 2 && frameResults.get(0).getRollScore() < 10 && newTotScore == 10;
        boolean isStrike = !isSpare && roll == 10;
        boolean noBonus = !containsStrikeScore() && !containsSpareScore();

        if (newTotScore > 10 && (!lastFrame || noBonus)) {
            throw new BowlingScoreException("Frame score (" + newTotScore + ") exceeds maxScore");
        }
        frameResults.add(new RollScore(roll, isStrike ? Strike : isSpare ? Spare : None));

    }
    private void determineCompleteStatus() {
        int rolls = frameResults.size();
        boolean noBonus = !containsStrikeScore() && !containsSpareScore();

        boolean first9RoundsEndScenario = !lastFrame && (containsStrikeScore() || rolls == 2);
        boolean lastRoundsEndScenario   =  lastFrame && (rolls == 3 || rolls == 2 && noBonus);

        completeFrame = first9RoundsEndScenario || lastRoundsEndScenario;
    }

    public List<RollScore> getFrameResults() {
        return Collections.unmodifiableList(frameResults);
    }

    public boolean containsSpareScore() {
        return frameResults.stream().anyMatch(x->x.getBonus() == Spare);
    }

    public boolean containsStrikeScore() {
        return frameResults.stream().anyMatch(x->x.getBonus() == Strike);
    }

    public boolean isIncompleteFrame() {
        return !completeFrame;
    }

    public boolean isCompleteFrame() {
        return completeFrame;
    }

    public boolean isLastFrame() {
        return lastFrame;
    }
}
