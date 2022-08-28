package org.bowlingsyndicate.contract;

import org.bowlingsyndicate.domain.BowlingFrame;
import org.bowlingsyndicate.domain.FrameResult;
import org.bowlingsyndicate.domain.OrderBy;
import org.bowlingsyndicate.domain.Player;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BowlingGame {
    void addPlayer(Player player);
    void beginGameplay();
    // TODO: returns an enum saying, PLAYER_DONE or ROLL_AGAIN.
    void registerPlayerRoll(Player player, int pins);
    Optional<Player> getCurrentPlayerInAction();
    List<Player> getPlayers(OrderBy order);
    Map<Player, List<BowlingFrame>> getScoreTable();
    boolean isFinalFrame();
    boolean isFinished();
}
