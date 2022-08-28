package org.bowlingsyndicate.contract;

import org.bowlingsyndicate.domain.BowlingFrame;
import org.bowlingsyndicate.error.BowlingGameException;

public interface BowlingFrameFactory {
    /**
     * A strike. Nothing further can occur in this frame.
     *
     * @return generated Frame result.
     */
    BowlingFrame createStrikeFrame() throws BowlingGameException;

    /**
     * Validates result: Total cannot be more than 10, unless it is
     * the last frame. Then it has to be consecutive strikes, or spare.
     *
     * @param roll1 result of first roll.
     * @param roll2 Result of second roll.
     * @return generated Frame result.
     */
    BowlingFrame createBowlingFrame(int roll1, int roll2) throws BowlingGameException;

    /**
     * Generate the last frame of the bowlinggame. Only use this for when there are strikes or spares in the last frame.
     * Otherwise other methods can be used for the last frame aswell.
     *
     * @param roll1 result of first roll.
     * @param roll2 Result of second roll. If not spare or Strike, no further rolls allowed.
     * @param roll3 Result of the optional third roll if the previous two are
     *              strikes or the two resulted in a spare.
     * @return generated Frame result.
     */
    BowlingFrame createLastBowlingFrame(int roll1, int roll2, int roll3) throws BowlingGameException;
}
