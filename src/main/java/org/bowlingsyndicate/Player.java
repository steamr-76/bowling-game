package org.bowlingsyndicate;

import java.util.List;

public interface Player {
    List<BowlingFrame> getBowlingFrames();
    String getNickName();
    int getPlayOrder();
    void registerFrame(BowlingFrame frame);
}
