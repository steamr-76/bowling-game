package org.bowlingsyndicate.error;

public class BowlingGameException extends RuntimeException {
    BowlingGameException(String errorMessage) {
        super(errorMessage);
    }
}
