package org.bowlingsyndicate.contract;

import org.bowlingsyndicate.domain.BowlingFrame;
import org.bowlingsyndicate.error.BowlingGameException;

public interface BowlingFrameFactory {
    /**
     * Validates result: Total cannot be more than 10, unless it is
     * the last frame. Then it has to be consecutive strikes.
     *
     * @param roll1 result of first roll.
     * @param roll2 Result of second roll.
     * @return generated Frame result.
     */
    BowlingFrame createBowlingFrame(int roll1, int roll2) throws BowlingGameException;

    /**
     * Validates result: Total cannot be more than 10, unless it is
     * the last frame. Then it has to be consecutive strikes.
     *
     * @param roll1 result of first roll.
     * @param roll2 Result of second roll If not spare or string, no further rolls allowed.
     * @param roll3 Result of the optional third roll if the previous two are
     *              strikes or the two resulted in a spare.
     * @return generated Frame result.
     */
    BowlingFrame createLastBowlingFrame(int roll1, int roll2, int roll3) throws BowlingGameException;
}
