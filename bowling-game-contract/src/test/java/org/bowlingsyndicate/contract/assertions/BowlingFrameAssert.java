package org.bowlingsyndicate.contract.assertions;

import org.assertj.core.api.AbstractAssert;
import org.bowlingsyndicate.domain.BowlingFrame;
import org.bowlingsyndicate.domain.RollScore;

public class BowlingFrameAssert extends AbstractAssert<BowlingFrameAssert, BowlingFrame> {

    public BowlingFrameAssert(BowlingFrame actual) {
        super(actual, BowlingFrameAssert.class);
    }

    public BowlingFrameAssert complete(boolean complete) {
        isNotNull();
        if (actual.isIncompleteFrame() == complete) {
            failWithMessage("Expected frame to be %s, but was not", complete?"complete":"incomplete");
        }
        return this;
    }

    public BowlingFrameAssert hasExactScore(int i) {
        isNotNull();
        if (actual.getFrameResults().size() != 1) {
            failWithMessage("Frame contains %s scores, while expected 1", actual.getFrameResults().size());
        }
        if(actual.getFrameResults().get(0).getRollScore() != i) {
            failWithMessage("Expected score to contain 1 roll with score %s but was %s", i, actual.getFrameResults().get(0).getRollScore());
        }
        return this;
    }

    public BowlingFrameAssert hasExactScore(int i, int j) {
        isNotNull();
        if (actual.getFrameResults().size() != 2) {
            failWithMessage("Frame contains %s scores, while expected 2", actual.getFrameResults().size());
        }
        if(actual.getFrameResults().get(0).getRollScore() != i || actual.getFrameResults().get(1).getRollScore() != j) {
            failWithMessage("Expected score to contain (%s,%s) was (%s,%s)", i, j,
                    actual.getFrameResults().get(0).getRollScore(), actual.getFrameResults().get(1).getRollScore());
        }
        return this;
    }

    public BowlingFrameAssert hasExactScore(int i, int j, int k) {
        isNotNull();
        if (actual.getFrameResults().size() != 3) {
            failWithMessage("Frame contains %s scores, while expected 3", actual.getFrameResults().size());
        }
        if(actual.getFrameResults().get(0).getRollScore() != i
                || actual.getFrameResults().get(1).getRollScore() != j
                || actual.getFrameResults().get(2).getRollScore() != k) {
            failWithMessage("Expected score to contain (%s,%s,%s) was (%s,%s,%s)", i, j, k,
                    actual.getFrameResults().get(0).getRollScore(),
                    actual.getFrameResults().get(1).getRollScore(),
                    actual.getFrameResults().get(2).getRollScore());
        }
        return this;
    }

    public BowlingFrameAssert hasExactBonus(RollScore.Bonus bonus) {
        isNotNull();
        if (actual.getFrameResults().size() != 1) {
            failWithMessage("Frame contains %s bonus entries, while expected 1", actual.getFrameResults().size());
        }
        if (actual.getFrameResults().get(0).getBonus() != bonus) {
            failWithMessage("Expected bonus in roll 1 to contain bonus %s but was %s", bonus, actual.getFrameResults().get(0).getBonus());
        }
        return this;
    }

    public BowlingFrameAssert hasExactBonus(RollScore.Bonus i, RollScore.Bonus j) {
        isNotNull();
        if (actual.getFrameResults().size() != 2) {
            failWithMessage("Frame contains %s bonus entries, while expected 2", actual.getFrameResults().size());
        }
        if(actual.getFrameResults().get(0).getBonus() != i || actual.getFrameResults().get(1).getBonus() != j) {
            failWithMessage("Expected score to bonus entries (%s,%s) but was (%s,%s)", i, j,
                    actual.getFrameResults().get(0).getBonus(), actual.getFrameResults().get(1).getBonus());
        }
        return this;
    }

    public BowlingFrameAssert hasExactBonus(RollScore.Bonus i, RollScore.Bonus j, RollScore.Bonus k) {
        isNotNull();
        if (actual.getFrameResults().size() != 3) {
            failWithMessage("Frame contains %s bonus entries, while expected 3", actual.getFrameResults().size());
        }
        if(actual.getFrameResults().get(0).getBonus() != i
                || actual.getFrameResults().get(1).getBonus() != j
                || actual.getFrameResults().get(2).getBonus() != k) {
            failWithMessage("Expected bonus entries to contain (%s,%s,%s) but was (%s,%s,%s)", i, j, k,
                    actual.getFrameResults().get(0).getBonus(),
                    actual.getFrameResults().get(1).getBonus(),
                    actual.getFrameResults().get(2).getBonus());
        }
        return this;
    }
}
