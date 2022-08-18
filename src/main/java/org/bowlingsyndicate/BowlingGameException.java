package org.bowlingsyndicate;

public class BowlingGameException extends RuntimeException {
    BowlingGameException(String errorMessage) {
        super(errorMessage);
    }
}
