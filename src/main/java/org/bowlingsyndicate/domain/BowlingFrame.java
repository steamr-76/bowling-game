package org.bowlingsyndicate.domain;

import org.bowlingsyndicate.domain.FrameScore;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.unmodifiableList;

public class BowlingFrame {
    private final List<FrameScore> frameScores;

    public BowlingFrame(List<FrameScore> frameScores) {
        this.frameScores = unmodifiableList(frameScores);
    }

    public List<FrameScore> getFrameScore() {
        return frameScores;
    }
}
