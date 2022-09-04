package org.bowlingsyndicate.domain;

import org.bowlingsyndicate.error.BowlingScoreException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.bowlingsyndicate.contract.assertions.Assertions.assertThat;
import static org.bowlingsyndicate.domain.RollScore.Bonus.*;

public class BowlingFrameTest {

    @Test
    public void openFrame() {
        BowlingFrame frame = BowlingFrame.emptyFrame();
        assertThat(frame.addRollResult(0)).complete(false).hasExactScore(0).hasExactBonus(None);
        assertThat(frame.addRollResult(3)).complete(true).hasExactScore(0, 3).hasExactBonus(None, None);

        assertThatExceptionOfType(BowlingScoreException.class)
                .isThrownBy(()->frame.addRollResult(0))
                .withMessage("Not allowed to add rolls to complete frames");
    }

    @Test
    public void spare_1() {
        BowlingFrame frame = BowlingFrame.emptyFrame();
        assertThat(frame.addRollResult(3)).complete(false).hasExactScore(3).hasExactBonus(None);
        assertThat(frame.addRollResult(7)).complete(true).hasExactScore(3, 7).hasExactBonus(None, Spare);

        assertThatExceptionOfType(BowlingScoreException.class)
                .isThrownBy(()->frame.addRollResult(5))
                .withMessage("Not allowed to add rolls to complete frames");
    }

    @Test
    public void spare_2() {
        BowlingFrame frame = BowlingFrame.emptyFrame();
        assertThat(frame.addRollResult(0)).complete(false).hasExactScore(0).hasExactBonus(None);
        assertThat(frame.addRollResult(10)).complete(true).hasExactScore(0, 10).hasExactBonus(None, Spare);

        assertThatExceptionOfType(BowlingScoreException.class)
                .isThrownBy(()->frame.addRollResult(5))
                .withMessage("Not allowed to add rolls to complete frames");
    }

    @Test
    public void strike() {
        BowlingFrame frame = BowlingFrame.emptyFrame();
        assertThat(frame.addRollResult(10)).complete(true).hasExactScore(10).hasExactBonus(Strike);

        assertThatExceptionOfType(BowlingScoreException.class)
                .isThrownBy(()->frame.addRollResult(10))
                .withMessage("Not allowed to add rolls to complete frames");
    }

    @Test
    void invalidRolls() {
        assertIllegalFrame(11, 0);
        assertIllegalFrame(0, 11);
        assertIllegalFrame(5, 6);
        assertIllegalFrame(10, 7);
        assertIllegalFrame(10, 0);
    }

    @Test
    public void lastFrameOpen() {
        BowlingFrame frame = BowlingFrame.emptyLastFrameFrame();
        assertThat(frame.addRollResult(4)).complete(false).hasExactScore(4).hasExactBonus(None);
        assertThat(frame.addRollResult(3)).complete(true).hasExactScore(4, 3).hasExactBonus(None, None);

        assertThatExceptionOfType(BowlingScoreException.class)
                .isThrownBy(()->frame.addRollResult(0))
                .withMessage("Not allowed to add rolls to complete frames");
    }

    @Test
    public void lastFrameSpare() {
        BowlingFrame frame = BowlingFrame.emptyLastFrameFrame();
        assertThat(frame.addRollResult(3)).complete(false).hasExactScore(3).hasExactBonus(None);
        assertThat(frame.addRollResult(7)).complete(false).hasExactScore(3, 7).hasExactBonus(None, Spare);
        assertThat(frame.addRollResult(10)).complete(true).hasExactScore(3, 7, 10).hasExactBonus(None, Spare, Strike);

        assertThatExceptionOfType(BowlingScoreException.class)
                .isThrownBy(()->frame.addRollResult(10))
                .withMessage("Not allowed to add rolls to complete frames");
    }

    @Test
    public void lastFrameStrike_fullFrame() {
        BowlingFrame frame = BowlingFrame.emptyLastFrameFrame();
        assertThat(frame.addRollResult(10)).complete(false).hasExactScore(10).hasExactBonus(Strike);
        assertThat(frame.addRollResult(10)).complete(false).hasExactScore(10, 10).hasExactBonus(Strike, Strike);
        assertThat(frame.addRollResult(10)).complete(true).hasExactScore(10, 10, 10).hasExactBonus(Strike, Strike, Strike);

        assertThatExceptionOfType(BowlingScoreException.class)
                .isThrownBy(()->frame.addRollResult(4))
                .withMessage("Not allowed to add rolls to complete frames");
    }

    @Test
    public void lastFrameStrike_unlucky() {
        BowlingFrame frame = BowlingFrame.emptyLastFrameFrame();
        assertThat(frame.addRollResult(10)).complete(false).hasExactScore(10).hasExactBonus(Strike);
        assertThat(frame.addRollResult(0 )).complete(false).hasExactScore(10, 0).hasExactBonus(Strike, None);
        assertThat(frame.addRollResult(1 )).complete(true) .hasExactScore(10, 0, 1).hasExactBonus(Strike, None, None);

        assertThatExceptionOfType(BowlingScoreException.class)
                .isThrownBy(()->frame.addRollResult(4))
                .withMessage("Not allowed to add rolls to complete frames");
    }

    @Test
    public void lastFrameStrike_threeRolls() {
        BowlingFrame frame = BowlingFrame.emptyLastFrameFrame();
        assertThat(frame.addRollResult(10)).complete(false).hasExactScore(10).hasExactBonus(Strike);
        assertThat(frame.addRollResult(10)).complete(false).hasExactScore(10, 10).hasExactBonus(Strike, Strike);
        assertThat(frame.addRollResult( 5)).complete(true).hasExactScore(10, 10, 5).hasExactBonus(Strike, Strike, None);

        assertThatExceptionOfType(BowlingScoreException.class)
                .isThrownBy(()->frame.addRollResult(4))
                .withMessage("Not allowed to add rolls to complete frames");
    }

    @Test
    void invalidTenthFrame() {
        assertIllegalLastFrame(5,6);
        assertIllegalLastFrame(5,6,0);
        assertIllegalLastFrame(0,9,6);
        assertIllegalLastFrame(5,6,0);
        assertIllegalLastFrame(0,6,5);
    }

    private void assertIllegalFrame(int roll1, int roll2) {
        BowlingFrame frame = BowlingFrame.emptyFrame();
        assertThatExceptionOfType(BowlingScoreException.class)
                .isThrownBy(()->frame.addRollResult(roll1).addRollResult(roll2));
    }

    private void assertIllegalLastFrame(int roll1, int roll2) {
        BowlingFrame frame = BowlingFrame.emptyLastFrameFrame();
        assertThatExceptionOfType(BowlingScoreException.class)
                .isThrownBy(()->frame.addRollResult(roll1).addRollResult(roll2));
    }

    private void assertIllegalLastFrame(int roll1, int roll2, int roll3) {
        BowlingFrame frame = BowlingFrame.emptyLastFrameFrame();
        assertThatExceptionOfType(BowlingScoreException.class)
                .isThrownBy(()->frame.addRollResult(roll1).addRollResult(roll2).addRollResult(roll3));
    }
}