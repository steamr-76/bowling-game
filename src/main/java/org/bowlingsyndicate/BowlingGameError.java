package org.bowlingsyndicate;

public class BowlingGameError extends RuntimeException {
    BowlingGameError(String errorMessage) {
        super(errorMessage);
    }
}
