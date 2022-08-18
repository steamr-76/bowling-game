package org.bowlingsyndicate;

import org.bowlingsyndicate.contract.BowlingGame;
import org.bowlingsyndicate.domain.OrderBy;
import org.bowlingsyndicate.domain.Player;
import org.bowlingsyndicate.error.BowlingGameException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;

public abstract class BowlingGameTest {

    BowlingGame game = bowlingGame();

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
        game.addPlayer(new Player("player1"));
        game.beginGameplay();

        assertThat(
                catchThrowable(()->game.addPlayer(new Player("player2")))
        )
                .isInstanceOf(BowlingGameException.class)
                .hasMessage("Cannot add player after start");
    }

    @Test
    void addingSamePlayerTwiceFails()  {
        Player player1 = new Player(("player1"));
        game.addPlayer(player1);

        assertThatExceptionOfType(BowlingGameException.class).isThrownBy(()->
                game.addPlayer(player1)
        );
        assertThatExceptionOfType(BowlingGameException.class).isThrownBy(()->
                game.addPlayer(new Player("player1"))
        );
    }

    @Test
    void fullGameWithTwoPlayers()  {
        Player player1 = new Player("Godsent");
        game.addPlayer(player1);
        Player player2 = new Player("Arya");
        game.addPlayer(player2);
        game.beginGameplay();

        assertThat(game.getPlayers(OrderBy.PlayOrder)).containsExactly(player1, player2);
        assertThat(game.getPlayers(OrderBy.Name))     .containsExactly(player2, player1);

        int frame = 0;
        do {
            frame ++;
            if(frame > 10) {
                fail("There should not be 11 frames in bowling");
            }

            assertThat(game.getCurrentPlayerInAction()).containsSame(player1);
            game.registerPlayerScore(player1, List.of(0));

            assertThat(game.getCurrentPlayerInAction()).containsSame(player2);
            if(frame < 10) {
                assertThat(game.isFinalFrame()).isFalse();
                game.registerPlayerScore(player2, List.of(10));
            } else {
                assertThat(game.isFinalFrame()).isTrue();
                game.registerPlayerScore(player2, List.of(10, 10, 10));
            }
        } while(game.isFinished());

        assertThat(frame).isEqualTo(10);
        assertThat(game.isFinalFrame()).isFalse();
        assertThat(game.getCurrentPlayerInAction()).isEmpty();

        assertThat(game.getPlayers(OrderBy.Score)).containsExactly(player2, player1);
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

    protected abstract BowlingGame bowlingGame();
    protected abstract Player player(String name);
}
