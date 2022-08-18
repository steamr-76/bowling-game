package org.bowlingsyndicate.contract;

import org.bowlingsyndicate.domain.BowlingFrame;
import org.bowlingsyndicate.error.BowlingScoreException;

import java.util.List;

/**
 * Calculates score based on frame results.
 * Implementation can decide whether they are calculated
 * based on Traditional scoring or World Bowling scoring
 */
public interface BowlingScoreCalculator {
    /**
     * Calculates score based on the given frames.
     *
     * @param frames nonnull list of max 10 frames. Intermediate
     *               result can also be calculated (frames < 10).
     * @return total score value.
     * @throws BowlingScoreException Thrown if frames are > 10, or if frames
     * contains impossible combinations. (ie  more than one strike in frames
     * 0-9)
     */
    int calculateScore(List<BowlingFrame> frames) throws BowlingScoreException;
}
