package org.bowlingsyndicate;

import java.util.List;

public interface BowlingFrame {
    /**
     * Returns the result of this frame, as either an open, spare, or strike
     * - as it would show on a scoring table.
     *
     * @return A list of either 1, 2, 3 values from {@link FrameScore} enum.
     */
    List<FrameScore> getFrameScore();
}
