package org.bowlingsyndicate.contract.tests;

import org.assertj.core.api.Assertions;
import org.bowlingsyndicate.contract.BowlingFrameFactory;
import org.bowlingsyndicate.domain.FrameResult;
import org.bowlingsyndicate.error.BowlingScoreException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class BowlingFrameFactoryTest {

    private final BowlingFrameFactory bowlingFrameFactory = getBowlingFrameFactory();

    @Test
    void totalMiss() {
        assertThat(bowlingFrameFactory.createBowlingFrame(0, 0).getFrameResults())
                .containsExactly(FrameResult.pins_0, FrameResult.pins_0);
    }

    @Test
    void validateOpenFrame() {
        assertThat(bowlingFrameFactory.createBowlingFrame(0, 4).getFrameResults())
                .containsExactly(FrameResult.pins_0, FrameResult.pins_4);
    }

    @Test
    void validateSpare() {
        assertThat(bowlingFrameFactory.createBowlingFrame(6, 4).getFrameResults())
                .containsExactly(FrameResult.pins_6, FrameResult.Spare);
        assertThat(bowlingFrameFactory.createBowlingFrame(0, 10).getFrameResults())
                .containsExactly(FrameResult.pins_0, FrameResult.Spare);
    }

    @Test
    void validateStrike() {
        assertThat(bowlingFrameFactory.createStrikeFrame().getFrameResults())
                .containsExactly(FrameResult.Strike);
    }

    @Test
    void invalidRolls() {
        Assertions.assertThatExceptionOfType(BowlingScoreException.class).isThrownBy(
                ()-> bowlingFrameFactory.createBowlingFrame(11, 0)
        );
        Assertions.assertThatExceptionOfType(BowlingScoreException.class).isThrownBy(
                ()-> bowlingFrameFactory.createBowlingFrame(0, 11)
        );
        Assertions.assertThatExceptionOfType(BowlingScoreException.class).isThrownBy(
                ()-> bowlingFrameFactory.createBowlingFrame(5, 6)
        );
        Assertions.assertThatExceptionOfType(BowlingScoreException.class).isThrownBy(
                ()-> bowlingFrameFactory.createBowlingFrame(10, 7)
        );
        Assertions.assertThatExceptionOfType(BowlingScoreException.class).isThrownBy(
                () -> bowlingFrameFactory.createBowlingFrame(10, 0)
        );
    }


    @Test
    void validTenthFrame() {
        assertThat(bowlingFrameFactory.createLastBowlingFrame(10, 10, 10).getFrameResults())
                .containsExactly(FrameResult.Strike, FrameResult.Strike, FrameResult.Strike);

        assertThat(bowlingFrameFactory.createLastBowlingFrame(10, 10, 4).getFrameResults())
                .containsExactly(FrameResult.Strike, FrameResult.Strike, FrameResult.pins_4);

        assertThat(bowlingFrameFactory.createLastBowlingFrame(10, 5, 0).getFrameResults())
                .containsExactly(FrameResult.Strike, FrameResult.pins_5);

        assertThat(bowlingFrameFactory.createLastBowlingFrame(5, 5, 0).getFrameResults())
                .containsExactly(FrameResult.pins_5, FrameResult.pins_5);
    }

    @Test
    void invalidTenthFrame() {
        Assertions.assertThatExceptionOfType(BowlingScoreException.class).isThrownBy(
                ()-> bowlingFrameFactory.createLastBowlingFrame(0, 9, 6)
        );
        Assertions.assertThatExceptionOfType(BowlingScoreException.class).isThrownBy(
                ()-> bowlingFrameFactory.createLastBowlingFrame(10, 4, 6)
        );
        Assertions.assertThatExceptionOfType(BowlingScoreException.class).isThrownBy(
                ()-> bowlingFrameFactory.createLastBowlingFrame(10, 0, 6)
        );
        Assertions.assertThatExceptionOfType(BowlingScoreException.class).isThrownBy(
                ()-> bowlingFrameFactory.createLastBowlingFrame(5, 6, 0)
        );
        Assertions.assertThatExceptionOfType(BowlingScoreException.class).isThrownBy(
                ()-> bowlingFrameFactory.createLastBowlingFrame(0, 6, 5)
        );
    }

    protected abstract BowlingFrameFactory getBowlingFrameFactory();
}