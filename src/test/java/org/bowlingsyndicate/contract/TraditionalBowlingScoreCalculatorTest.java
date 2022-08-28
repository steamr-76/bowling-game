package org.bowlingsyndicate.contract;

import org.bowlingsyndicate.domain.BowlingFrame;
import org.bowlingsyndicate.domain.BowlingFrameWithScore;
import org.bowlingsyndicate.domain.FrameResult;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.bowlingsyndicate.domain.FrameResult.*;

abstract class TraditionalBowlingScoreCalculatorTest {

    BowlingScoreCalculator calculator = bowlingScoreCalculator();

    @Test
    void iWannaGoHide() {
        List<BowlingFrame> bowlingFrames = calculator.calculateScore(of(
                frame(pins_0, pins_0),
                frame(pins_0, pins_0),
                frame(pins_0, pins_0),
                frame(pins_0, pins_0),
                frame(pins_0, pins_0),
                frame(pins_0, pins_0),
                frame(pins_0, pins_0),
                frame(pins_0, pins_0),
                frame(pins_0, pins_0),
                frame(pins_0, pins_0)
        ));

        assertThat(bowlingFrames.size()).isEqualTo(10);

        for (BowlingFrame bowlingFrame : bowlingFrames) {
            assertThat(bowlingFrame)
                    .isInstanceOfSatisfying(BowlingFrameWithScore.class, (m)->
                            assertThat(m.getScore()).isEqualTo(0)
                    );
        }
    }

    @Test
    void weGoOnAFullStrike() {
        List<BowlingFrame> bowlingFrames = calculator.calculateScore(of(
                frame(Strike),
                frame(Strike),
                frame(Strike),
                frame(Strike),
                frame(Strike),
                frame(Strike),
                frame(Strike),
                frame(Strike),
                frame(Strike),
                frame(Strike, Strike, Strike)
        ));

        assertThat(bowlingFrames.size()).isEqualTo(10);

        int x = 0;
        for (BowlingFrame bowlingFrame : bowlingFrames) {
            x += 30;
            int finalX = x;
            assertThat(bowlingFrame)
                    .isInstanceOfSatisfying(BowlingFrameWithScore.class, (m)->
                            assertThat(m.getScore()).isEqualTo(finalX)
                    );
        }
    }

    @Test
    void spareMe() {
        List<BowlingFrame> bowlingFrames = calculator.calculateScore(of(
                frame(pins_1, Spare),
                frame(pins_2, Spare),
                frame(pins_3, Spare),
                frame(pins_4, Spare),
                frame(pins_5, Spare),
                frame(pins_6, Spare),
                frame(pins_7, Spare),
                frame(pins_8, Spare),
                frame(pins_9, Spare),
                frame(pins_1, Spare, pins_5)
        ));

        assertThat(bowlingFrames.size()).isEqualTo(10);

        int[] frameScores = new int[]{12, 25, 39, 54, 70, 87, 105, 124, 135, 150};
        for (int i = 0; i < bowlingFrames.size(); i++) {
            int finalI = i;
            assertThat(bowlingFrames.get(i))
                    .isInstanceOfSatisfying(BowlingFrameWithScore.class, (m)->
                            assertThat(m.getScore()).isEqualTo(frameScores[finalI])
                    );
        }
    }

    @Test
    void ohMeFeelSoRandom() {
        List<BowlingFrame> bowlingFrames = calculator.calculateScore(of(
                frame(pins_5, pins_0),
                frame(pins_4, pins_5),
                frame(pins_7, pins_0),
                frame(pins_4, Spare),
                frame(Strike),
                frame(pins_6, pins_3),
                frame(pins_4, pins_0),
                frame(pins_8, pins_0),
                frame(pins_0, pins_3),
                frame(pins_1, pins_5)
        ));

        assertThat(bowlingFrames.size()).isEqualTo(10);

        final int[] frameScores = new int[]{12, 25, 39, 54, 70, 87, 105, 124, 135, 150};
        for (int i = 0; i < bowlingFrames.size(); i++) {
            int finalI = i;
            assertThat(bowlingFrames.get(i)).isInstanceOfSatisfying(BowlingFrameWithScore.class, (m)->
                    assertThat(m.getScore()).isEqualTo(frameScores[finalI])
            );
        }
    }

    @Test
    void intermediateResultOfBeingAshamed() {
        List<BowlingFrame> bowlingFrames = calculator.calculateScore(of(
                frame(pins_0, pins_0),
                frame(pins_0, pins_0),
                frame(pins_0, pins_0),
                frame(pins_0)
        ));

        assertThat(bowlingFrames.size()).isEqualTo(4);

        for (BowlingFrame bowlingFrame : bowlingFrames) {
            assertThat(bowlingFrame).isInstanceOfSatisfying(BowlingFrameWithScore.class, (m) ->
                    assertThat(m.getScore()).isEqualTo(0)
            );
        }
    }

    @Test
    void intermediateResultOfSpares() {
        List<BowlingFrame> bowlingFrames = calculator.calculateScore(of(
                frame(pins_5, Spare),
                frame(pins_6, Spare),
                frame(pins_1, Spare),
                frame(pins_4, Spare),
                frame(pins_2)
        ));

        assertThat(bowlingFrames.size()).isEqualTo(5);

        final int[] frameScores = new int[]{16, 27, 41, 53};
        for (int i = 0; i < frameScores.length; i++) {
            int finalI = i;
            assertThat(bowlingFrames.get(i)).isInstanceOfSatisfying(BowlingFrameWithScore.class, (m)->
                    assertThat(m.getScore()).isEqualTo(frameScores[finalI])
            );
        }
        assertThat(bowlingFrames.get(4)).isInstanceOf(BowlingFrame.class);
    }

    @Test
    void intermediateResultOfStrikeOhoi() {
        List<BowlingFrame> bowlingFrames = calculator.calculateScore(of(
                frame(Strike),
                frame(Strike),
                frame(Strike)
        ));

        assertThat(bowlingFrames.size()).isEqualTo(3);

        assertThat(bowlingFrames.get(0)).isInstanceOfSatisfying(BowlingFrameWithScore.class, (m)->
                assertThat(m.getScore()).isEqualTo(30)
        );
        assertThat(bowlingFrames.get(1)).isInstanceOf(BowlingFrame.class);
        assertThat(bowlingFrames.get(2)).isInstanceOf(BowlingFrame.class);
    }

    @Test
    void intermediateResultOfMeFeelingSoRandom() {
        List<BowlingFrame> bowlingFrames = calculator.calculateScore(of(
                frame(pins_3, pins_6),
                frame(pins_3, Spare),
                frame(pins_3, pins_6),
                frame(Strike),
                frame(pins_5, pins_3),
                frame(pins_5)
        ));

        assertThat(bowlingFrames.size()).isEqualTo(6);

        final int[] frameScores = new int[]{9, 22, 31, 49, 57};
        for (int i = 0; i < frameScores.length; i++) {
            int finalI = i;
            assertThat(bowlingFrames.get(i)).isInstanceOfSatisfying(BowlingFrameWithScore.class, (m)->
                    assertThat(m.getScore()).isEqualTo(frameScores[finalI])
            );
        }
        assertThat(bowlingFrames.get(5)).isInstanceOf(BowlingFrame.class);
    }

    abstract BowlingScoreCalculator bowlingScoreCalculator();

    private BowlingFrame frame(FrameResult ... rolls) {
        return new BowlingFrame(of(rolls));
    }
}
