package org.bowlingsyndicate.contract.tests;

import org.bowlingsyndicate.contract.BowlingScoreCalculator;
import org.bowlingsyndicate.domain.BowlingFrame;
import org.bowlingsyndicate.domain.BowlingFrameWithScore;
import org.bowlingsyndicate.domain.FrameResult;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class TraditionalBowlingScoreCalculatorTest {

    BowlingScoreCalculator calculator = bowlingScoreCalculator();

    @Test
    public void iWannaGoHide() {
        List<BowlingFrame> bowlingFrames = calculator.calculateScore(of(
                frame(FrameResult.pins_0, FrameResult.pins_0),
                frame(FrameResult.pins_0, FrameResult.pins_0),
                frame(FrameResult.pins_0, FrameResult.pins_0),
                frame(FrameResult.pins_0, FrameResult.pins_0),
                frame(FrameResult.pins_0, FrameResult.pins_0),
                frame(FrameResult.pins_0, FrameResult.pins_0),
                frame(FrameResult.pins_0, FrameResult.pins_0),
                frame(FrameResult.pins_0, FrameResult.pins_0),
                frame(FrameResult.pins_0, FrameResult.pins_0),
                frame(FrameResult.pins_0, FrameResult.pins_0)
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
        List<BowlingFrame> bowlingFrames = calculator.calculateScore(List.of(
                frame(FrameResult.Strike),
                frame(FrameResult.Strike),
                frame(FrameResult.Strike),
                frame(FrameResult.Strike),
                frame(FrameResult.Strike),
                frame(FrameResult.Strike),
                frame(FrameResult.Strike),
                frame(FrameResult.Strike),
                frame(FrameResult.Strike),
                frame(FrameResult.Strike, FrameResult.Strike, FrameResult.Strike)
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
                frame(FrameResult.pins_1, FrameResult.Spare),
                frame(FrameResult.pins_2, FrameResult.Spare),
                frame(FrameResult.pins_3, FrameResult.Spare),
                frame(FrameResult.pins_4, FrameResult.Spare),
                frame(FrameResult.pins_5, FrameResult.Spare),
                frame(FrameResult.pins_6, FrameResult.Spare),
                frame(FrameResult.pins_7, FrameResult.Spare),
                frame(FrameResult.pins_8, FrameResult.Spare),
                frame(FrameResult.pins_9, FrameResult.Spare),
                frame(FrameResult.pins_1, FrameResult.Spare, FrameResult.pins_5)
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
        List<BowlingFrame> bowlingFrames = calculator.calculateScore(List.of(
                frame(FrameResult.pins_5, FrameResult.pins_0),
                frame(FrameResult.pins_4, FrameResult.pins_5),
                frame(FrameResult.pins_7, FrameResult.pins_0),
                frame(FrameResult.pins_4, FrameResult.Spare),
                frame(FrameResult.Strike),
                frame(FrameResult.pins_6, FrameResult.pins_3),
                frame(FrameResult.pins_4, FrameResult.pins_0),
                frame(FrameResult.pins_8, FrameResult.pins_0),
                frame(FrameResult.pins_0, FrameResult.pins_3),
                frame(FrameResult.pins_1, FrameResult.pins_5)
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
        List<BowlingFrame> bowlingFrames = calculator.calculateScore(List.of(
                frame(FrameResult.pins_0, FrameResult.pins_0),
                frame(FrameResult.pins_0, FrameResult.pins_0),
                frame(FrameResult.pins_0, FrameResult.pins_0),
                frame(FrameResult.pins_0)
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
        List<BowlingFrame> bowlingFrames = calculator.calculateScore(List.of(
                frame(FrameResult.pins_5, FrameResult.Spare),
                frame(FrameResult.pins_6, FrameResult.Spare),
                frame(FrameResult.pins_1, FrameResult.Spare),
                frame(FrameResult.pins_4, FrameResult.Spare),
                frame(FrameResult.pins_2)
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
        List<BowlingFrame> bowlingFrames = calculator.calculateScore(List.of(
                frame(FrameResult.Strike),
                frame(FrameResult.Strike),
                frame(FrameResult.Strike)
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
        List<BowlingFrame> bowlingFrames = calculator.calculateScore(List.of(
                frame(FrameResult.pins_3, FrameResult.pins_6),
                frame(FrameResult.pins_3, FrameResult.Spare),
                frame(FrameResult.pins_3, FrameResult.pins_6),
                frame(FrameResult.Strike),
                frame(FrameResult.pins_5, FrameResult.pins_3),
                frame(FrameResult.pins_5)
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

    protected abstract BowlingScoreCalculator bowlingScoreCalculator();

    private BowlingFrame frame(FrameResult ... rolls) {
        return new BowlingFrame(List.of(rolls));
    }
}
