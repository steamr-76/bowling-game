package org.bowlingsyndicate.contract;

import org.bowlingsyndicate.domain.BowlingFrame;
import org.bowlingsyndicate.domain.OrderBy;
import org.bowlingsyndicate.domain.Player;
import org.bowlingsyndicate.error.BowlingGameException;
import org.bowlingsyndicate.error.BowlingScoreException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BowlingGame {

    /**
     * A prestart method: adds a player to the game.
     * @param player A new player to the game.
     * @throws BowlingGameException If game has already been started, or is finished.
     */
    void addPlayer(Player player) throws BowlingGameException;

    /**
     * A prestart method: Starts the game. No player can be added after this point. Calling this method again is ok, but
     * throws an exception if any user has rolled.
     * @throws BowlingGameException if any user has rolled, or if game is finished.
     */
    void beginGameplay() throws BowlingGameException;


    /**
     * Register a users roll.
     *
     * @param player The score for the player.
     * @param pins a value between 0 and 10.
     * @throws BowlingScoreException If scoring does not make sense.
     * @throws BowlingGameException If pins are less or more than 10, if its not the players turn, if game is finished.
     */
    void registerPlayerRoll(Player player, int pins) throws BowlingScoreException, BowlingGameException;

    /**
     * Returns the user in play, if there are more frames left.
     * @return the next player's turn.
     */
    Optional<Player> getCurrentPlayerInAction();

    /**
     * Returns a list of users, in different order.
     *
     * @param order The order to sort the players of the game.
     * @return List of players in order as specified.
     */
    List<Player> getPlayers(OrderBy order);

    /**
     * @return an unmodifiable map of players and their frames. The value of the map contains list of BowlingFrame,
     * either with a score or without (depending if the frame is completed or not).
     */
    Map<Player, List<BowlingFrame>> getScoreTable();

    /**
     * @return true if it is the last frame in the game.
     */
    boolean isFinalFrame();

    /**
     * @return true if there are no more rolls or frames left in the game.
     */
    boolean isFinished();
}
