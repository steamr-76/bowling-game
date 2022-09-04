package org.bowlingsyndicate.contract;

import org.bowlingsyndicate.domain.BowlingFrame;
import org.bowlingsyndicate.domain.BowlingFrameScore;
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
     *               result can also be calculated (frames < 10) and incomplete
     *               frames. The frames themselves WILL ALWAYS hold valid
     *               values. Illegal values will never be passed. But the list
     *               can hold illegal combination of frames, see below for what errors are caught.
     *
     * @return list of scores for the completed frames.
     * @throws BowlingScoreException Thrown if frames are > 10, or if last frame comes earlier or is missing.
     */
    List<BowlingFrameScore> calculateScore(List<BowlingFrame> frames) throws BowlingScoreException;
}
