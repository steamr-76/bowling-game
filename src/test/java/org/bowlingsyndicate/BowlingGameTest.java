package org.bowlingsyndicate;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

public abstract class BowlingGameTest {

    BowlingGame game = instance();

    @Test
    void cannotStartWithoutPlayers()  {
        assertThat(
                catchThrowable(game::beginGameplay))
                .isInstanceOf(BowlingGameException.class)
                .hasMessage("Game cannot start with 1 player");
    }

    @Test
    void addingNullPlayerGivesNullPointer()  {
        assertThat(
                catchThrowable(()->game.addPlayer(null)))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Player cannot be <null>");
    }

    @Test
    void addingPlayerAfterStartGivesError()  {
        game.addPlayer(player("player1"));
        game.beginGameplay();

        assertThat(
                catchThrowable(()->game.addPlayer(player("player2")))
        )
                .isInstanceOf(BowlingGameException.class)
                .hasMessage("Cannot add player after start");
    }

    @Test
    void fullGameWithSinglePlayer()  {
        Player player1 = player("player1");
        game.addPlayer(player1);
        game.beginGameplay();

        int frame = 0;
        do {
            frame ++;
            if(frame == 11) {
                fail("There should not be 11 frames in bowling");
            }
            assertThat(game.getCurrentPlayerInAction()).containsSame(player1);
            assertThat(game.isFinalFrame()).isEqualTo(frame == 10);
            game.registerPlayerScore(player1, List.of(10));
        } while(game.isFinished());

        assertThat(frame).isEqualTo(10);
        assertThat(game.getCurrentPlayerInAction()).isEmpty();

        game.registerPlayerScore(player1, List.of(10));
        assertThat(game.getCurrentPlayerInAction()).containsSame(player1);
        assertThat(game.isFinalFrame()).isFalse();
        game.registerPlayerScore(player1, List.of(10));
    }

    @Test
    void enforceSillyLimitOf_8_Players()  {
        for (int i = 0; i < 8; i++) {
            Player player = player("player");
            game.addPlayer(player);
        }

        assertThat(
                catchThrowable(()->game.addPlayer(player("excessivePlayer")))
        )
                .isInstanceOf(BowlingGameException.class)
                .hasMessage("Too many players already");
    }

    protected abstract BowlingGame instance();
    protected abstract Player player(String name);
}
