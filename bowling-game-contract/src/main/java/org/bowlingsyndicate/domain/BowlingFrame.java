package org.bowlingsyndicate.domain;

import java.util.List;

import static java.util.Collections.unmodifiableList;

public class BowlingFrame {
    private final List<FrameResult> frameResults;

    public BowlingFrame(List<FrameResult> frameResults) {
        this.frameResults = unmodifiableList(frameResults);
    }

    public List<FrameResult> getFrameResults() {
        return frameResults;
    }
}
