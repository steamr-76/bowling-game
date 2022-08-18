package org.bowlingsyndicate.error;

public class BowlingScoreException extends BowlingGameException {
    BowlingScoreException(String errorMessage) {
        super(errorMessage);
    }
}
