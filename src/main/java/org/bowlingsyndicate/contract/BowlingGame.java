package org.bowlingsyndicate.contract;

import org.bowlingsyndicate.domain.OrderBy;
import org.bowlingsyndicate.domain.Player;

import java.util.List;
import java.util.Optional;

public interface BowlingGame {
    void addPlayer(Player player);
    void beginGameplay();
    void registerPlayerScore(Player player, List<Integer> scores);
    Optional<Player> getCurrentPlayerInAction();
    List<Player> getPlayers(OrderBy order);

    boolean isFinalFrame();
    boolean isFinished();
}
