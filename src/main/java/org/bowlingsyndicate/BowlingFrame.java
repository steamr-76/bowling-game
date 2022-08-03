package org.bowlingsyndicate;

public interface BowlingFrame {
    int getRoll1Result();
    int getRoll2Result();
    int getOptionalFinalRoll3Result();
    int[] getFrameResult();
    FrameScore getFrameScore();
}
