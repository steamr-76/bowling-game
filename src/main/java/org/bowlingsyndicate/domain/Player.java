package org.bowlingsyndicate.domain;


import java.util.LinkedList;
import java.util.List;

public class Player {
    private final String nickName;
    private final List<BowlingFrame> frames = new LinkedList<>();

    public Player(String nickName) {
        this.nickName = nickName;
    }

    public List<BowlingFrame> getBowlingFrames() {
        return frames;
    }

    public String getNickName() {
        return nickName;
    }

    public void registerFrame(BowlingFrame frame) {
        frames.add(frame);
    }
}
