package org.bowlingsyndicate.contract.assertions;

import org.bowlingsyndicate.domain.BowlingFrame;

public class Assertions extends org.assertj.core.api.Assertions {
    public static BowlingFrameAssert assertThat(BowlingFrame actual) {
        return new BowlingFrameAssert(actual);
    }
}
