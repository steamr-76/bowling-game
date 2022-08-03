package org.bowlingsyndicate;

import java.util.List;
import java.util.Optional;

public interface BowlingGame {
    void addPlayer(Player player);
    void beginGameplay();
    void registerPlayerScore(Player player, BowlingFrame frame);
    Optional<Player> getCurrentPlayerInAction();
    List<Player> getPlayers(OrderBy order);

    boolean isFinalFrame();
    boolean isFinished();
}
