package org.bowlingsyndicate.contract.tests;

import org.bowlingsyndicate.contract.BowlingScoreCalculator;
import org.bowlingsyndicate.domain.BowlingFrame;
import org.bowlingsyndicate.domain.BowlingFrameScore;
import org.bowlingsyndicate.error.BowlingScoreException;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static java.util.List.of;
import static org.assertj.core.api.Assertions.*;

public abstract class TraditionalBowlingScoreCalculatorTest {

    BowlingScoreCalculator calculator = bowlingScoreCalculator();

    @Test
    public void iWannaGoHide() {
        List<BowlingFrameScore> bowlingFrames = calculator.calculateScore(of(
                frame(0, 0),
                frame(0, 0),
                frame(0, 0),
                frame(0, 0),
                frame(0, 0),
                frame(0, 0),
                frame(0, 0),
                frame(0, 0),
                frame(0, 0),
                lastFrame(0, 0)
        ));

        assertThat(bowlingFrames.size()).isEqualTo(10);

        for (int i = 0; i < bowlingFrames.size(); i++) {
            assertScoreForFrame(i, bowlingFrames.get(i), 0);
        }
    }

    @Test
    void weGoOnAFullStrike() {
        List<BowlingFrameScore> bowlingFrames = calculator.calculateScore(List.of(
                frame(10),
                frame(10),
                frame(10),
                frame(10),
                frame(10),
                frame(10),
                frame(10),
                frame(10),
                frame(10),
                lastFrame(10, 10, 10)
        ));

        assertThat(bowlingFrames.size()).isEqualTo(10);

        int x = 0;
        for (int i = 0; i < bowlingFrames.size(); i++) {
            x += 30;
            int finalX = x;
            assertScoreForFrame(i, bowlingFrames.get(i), finalX);
        }
    }

    @Test
    void spareMe() {
        List<BowlingFrameScore> bowlingFrames = calculator.calculateScore(of(
                frame(0, 10),
                frame(1, 9),
                frame(2, 8),
                frame(3, 7),
                frame(4, 6),
                frame(5, 5),
                frame(6, 4),
                frame(7, 3),
                frame(8, 2),
                lastFrame(9, 1, 10)
        ));

        assertThat(bowlingFrames.size()).isEqualTo(10);

        int[] frameScores = new int[]{11, 23, 36, 50, 65, 81, 98, 116, 135, 155};
        for (int i = 0; i < bowlingFrames.size(); i++) {
            assertScoreForFrame(i, bowlingFrames.get(i), frameScores[i]);
        }
    }

    @Test
    void ohMeFeelSoRandom() {
        List<BowlingFrameScore> bowlingFrames = calculator.calculateScore(List.of(
                frame(5, 0),
                frame(4, 5),
                frame(7, 0),
                frame(4, 6),
                frame(10),
                frame(6, 3),
                frame(4, 0),
                frame(8, 0),
                frame(0, 3),
                lastFrame(1, 5)
        ));

        assertThat(bowlingFrames.size()).isEqualTo(10);

        final int[] frameScores = new int[]{5, 14, 21, 41, 60, 69, 73, 81, 84, 90};
        for (int i = 0; i < bowlingFrames.size(); i++) {
            assertScoreForFrame(i, bowlingFrames.get(i), frameScores[i]);
        }
    }

    @Test
    void intermediateResultOfBeingAshamed() {
        List<BowlingFrameScore> bowlingFrames = calculator.calculateScore(List.of(
                frame(0, 0),
                frame(0, 0),
                frame(0, 0),
                frame(0)
        ));

        assertThat(bowlingFrames.size()).isEqualTo(3);
        assertScoreForFrame(0, bowlingFrames.get(0), 0);
        assertScoreForFrame(1, bowlingFrames.get(1), 0);
        assertScoreForFrame(2, bowlingFrames.get(2), 0);
    }

    @Test
    void intermediateResultOfSpares() {
        List<BowlingFrameScore> bowlingFrames = calculator.calculateScore(List.of(
                frame(5, 5),
                frame(6, 4),
                frame(1, 9),
                frame(4, 6),
                frame(2)
        ));

        assertThat(bowlingFrames.size()).isEqualTo(4);

        final int[] frameScores = new int[]{16, 27, 41, 53};
        for (int i = 0; i < frameScores.length; i++) {
            assertScoreForFrame(i, bowlingFrames.get(i), frameScores[i]);
        }
    }

    @Test
    void intermediateResultOfMultiStrike() {
        List<BowlingFrameScore> bowlingFrames = calculator.calculateScore(List.of(
                frame(10),
                frame(10),
                frame(10)
        ));

        assertThat(bowlingFrames.size()).isEqualTo(1);
        assertScoreForFrame(0, bowlingFrames.get(0), 30);
    }

    @Test
    void intermediateResultOfMeFeelingSoRandom() {
        List<BowlingFrameScore> bowlingFrames = calculator.calculateScore(List.of(
                frame(3, 6),
                frame(3, 7),
                frame(3, 6),
                frame(10),
                frame(5, 3),
                frame(5)
        ));

        assertThat(bowlingFrames.size()).isEqualTo(5);

        final int[] frameScores = new int[]{9, 22, 31, 49, 57};
        for (int i = 0; i < frameScores.length; i++) {
            assertScoreForFrame(i, bowlingFrames.get(i), frameScores[i]);
        }
    }

    @Test
    void illegalFrameSet() {
        assertThatExceptionOfType(BowlingScoreException.class).isThrownBy(() ->
                calculator.calculateScore(List.of(frame(3, 6), lastFrame(5, 3)))
        );
    }

    @Test
    void missingLastFrame() {
        assertThatExceptionOfType(BowlingScoreException.class).isThrownBy(() ->
                calculator.calculateScore(List.of(
                        frame(3, 6),
                        frame(3, 6),
                        frame(3, 6),
                        frame(3, 6),
                        frame(3, 6),
                        frame(3, 6),
                        frame(3, 6),
                        frame(3, 6),
                        frame(3, 6),
                        frame(3, 6)))
        );
    }

    @Test
    void doNotAcceptNullList() {
        assertThatExceptionOfType(BowlingScoreException.class).isThrownBy(() ->
                calculator.calculateScore(null)
        );
    }

    @Test
    void acceptEmptyList() {
        List<BowlingFrameScore> bowlingFrameScores = calculator.calculateScore(Collections.emptyList());
        assertThat(bowlingFrameScores.size()).isEqualTo(0);
    }


    @Test
    void lateLastFrame() {
        assertThatExceptionOfType(BowlingScoreException.class).isThrownBy(() ->
                calculator.calculateScore(List.of(
                        frame(3, 6),
                        frame(3, 6),
                        frame(3, 6),
                        frame(3, 6),
                        frame(3, 6),
                        frame(3, 6),
                        frame(3, 6),
                        frame(3, 6),
                        frame(3, 6),
                        frame(3, 6),
                        lastFrame(3, 6)))
        );
    }
    protected abstract BowlingScoreCalculator bowlingScoreCalculator();

    private BowlingFrame frame(Integer ... rolls) {
        BowlingFrame frame = BowlingFrame.emptyFrame();
        for (Integer roll : rolls) {
            frame.addRollResult(roll);
        }
        return frame;
    }

    private BowlingFrame lastFrame(Integer ... rolls) {
        BowlingFrame frame = BowlingFrame.emptyLastFrameFrame();
        for (Integer roll : rolls) {
            frame.addRollResult(roll);
        }
        return frame;
    }

    private void assertScoreForFrame(int i, BowlingFrameScore frameScore, int expectedScore) {
        assertThat(frameScore.getAccumulatedScore()).as("Score in frame %s is not correct", i+1)
                .isEqualTo(expectedScore);
    }
}
