package org.bowlingsyndicate;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.bowlingsyndicate.FrameScore.*;

abstract class BowlingFrameFactoryTest {

    private final BowlingFrameFactory bowlingFrameFactory = getBowlingFrameFactory();

    @Test
    void totalMiss() {
        assertThat(bowlingFrameFactory.createBowlingFrame(0, 0).getFrameScore())
                .containsExactly(pins_0, pins_0);
    }

    @Test
    void validateOpenFrame() {
        assertThat(bowlingFrameFactory.createBowlingFrame(0, 4).getFrameScore())
                .containsExactly(pins_0, pins_4);
    }

    @Test
    void validateSpare() {
        assertThat(bowlingFrameFactory.createBowlingFrame(6, 4).getFrameScore())
                .containsExactly(pins_6, Spare);
        assertThat(bowlingFrameFactory.createBowlingFrame(0, 10).getFrameScore())
                .containsExactly(pins_0, Spare);
    }

    @Test
    void validateStrike() {
        assertThat(bowlingFrameFactory.createBowlingFrame(10, 0).getFrameScore())
                .containsExactly(Strike);
    }

    @Test
    void invalidFigures() {
        assertThatExceptionOfType(BowlingScoreException.class).isThrownBy(
                ()-> bowlingFrameFactory.createBowlingFrame(11, 0)
        );
        assertThatExceptionOfType(BowlingScoreException.class).isThrownBy(
                ()-> bowlingFrameFactory.createBowlingFrame(0, 11)
        );
        assertThatExceptionOfType(BowlingScoreException.class).isThrownBy(
                ()-> bowlingFrameFactory.createBowlingFrame(5, 6)
        );
        assertThatExceptionOfType(BowlingScoreException.class).isThrownBy(
                ()-> bowlingFrameFactory.createBowlingFrame(10, 7)
        );
    }


    @Test
    void validTenthFrame() {
        assertThat(bowlingFrameFactory.createLastBowlingFrame(10, 10, 10).getFrameScore())
                .containsExactly(Strike, Strike, Strike);

        assertThat(bowlingFrameFactory.createLastBowlingFrame(10, 10, 4).getFrameScore())
                .containsExactly(Strike, Strike, pins_4);

        assertThat(bowlingFrameFactory.createLastBowlingFrame(10, 5, 0).getFrameScore())
                .containsExactly(Strike, pins_5);

        assertThat(bowlingFrameFactory.createLastBowlingFrame(5, 5, 0).getFrameScore())
                .containsExactly(pins_5, pins_5);
    }

    @Test
    void invalidTenthFrame() {
        assertThatExceptionOfType(BowlingScoreException.class).isThrownBy(
                ()-> bowlingFrameFactory.createLastBowlingFrame(0, 9, 6)
        );
        assertThatExceptionOfType(BowlingScoreException.class).isThrownBy(
                ()-> bowlingFrameFactory.createLastBowlingFrame(10, 4, 6)
        );
        assertThatExceptionOfType(BowlingScoreException.class).isThrownBy(
                ()-> bowlingFrameFactory.createLastBowlingFrame(10, 0, 6)
        );
        assertThatExceptionOfType(BowlingScoreException.class).isThrownBy(
                ()-> bowlingFrameFactory.createLastBowlingFrame(5, 6, 0)
        );
        assertThatExceptionOfType(BowlingScoreException.class).isThrownBy(
                ()-> bowlingFrameFactory.createLastBowlingFrame(0, 6, 5)
        );
    }

    protected abstract BowlingFrameFactory getBowlingFrameFactory();
}