package org.bowlingsyndicate.util;

import org.bowlingsyndicate.domain.BowlingFrame;
import org.bowlingsyndicate.domain.BowlingFrameScore;
import org.bowlingsyndicate.domain.RollScore;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class BowlingGameScorePrinter {
    public static void printScores(PrintStream out, List<BowlingFrame> frames, List<BowlingFrameScore> scores) {
        StringBuilder bfr = new StringBuilder();
        p(bfr, "-------------------------------------------------------------------------------------");
        p(bfr, "|   1   |   2   |   3   |   4   |   5   |   6   |   7   |   8   |   9   |     10    |");
        p(bfr, "|---+---|---+---|---+---|---+---|---+---|---+---|---+---|---+---|---+---|---+---+---|");
        s(bfr, "| x | x | x | x | x | x | x | x | x | x | x | x | x | x | x | x | x | x | x | x | x |", "x", " ", getMappedRolls(frames));
        p(bfr, "|   +---|   +---|   +---|   +---|   +---|   +---|   +---|   +---|   +---|   +---+---|");
        s(bfr, "|  xxx  |  xxx  |  xxx  |  xxx  |  xxx  |  xxx  |  xxx  |  xxx  |  xxx  |    xxx    |", "xxx", "   ", getMappedScores(scores));
        p(bfr, "--------+-------+-------+-------+-------+-------+-------+-------+-------+------------");
        out.print(bfr);
    }

    private static void s(StringBuilder bfr, String s, String code, String filler, List<String> items) {
        StringBuilder builder = new StringBuilder(s);

        for(int idx = 0, k = s.indexOf(code); k >= 0; k = builder.indexOf(code), idx ++) {
            if(idx < items.size()) {
                builder.replace(k, k + code.length(), items.get(idx));
            } else {
                builder.replace(k, k + code.length(), filler);
            }
        }
        bfr.append(builder).append("\n");
    }

    private static void p(StringBuilder bfr, String s) {
        bfr.append(s).append("\n");
    }

    private static List<String> getMappedScores(List<BowlingFrameScore> scores) {
        return scores.stream().map(x->pad(x.getAccumulatedScore())).collect(toList());
    }

    private static List<String> getMappedRolls(List<BowlingFrame> frames) {
        List<String> rollScores = new ArrayList<>(21);

        for (BowlingFrame x : frames) {
            if (x.containsStrikeScore() && !x.isLastFrame()) {
                rollScores.add(" ");
                rollScores.add("X");
            } else {
                for (RollScore frameResult : x.getFrameResults()) {
                    rollScores.add(mapResult(frameResult));
                }
            }
        }
        return rollScores;
    }

    private static String mapResult(RollScore frameResult) {
        if(frameResult.getBonus() == RollScore.Bonus.Strike) {
            return "X";
        }
        if(frameResult.getBonus() == RollScore.Bonus.Spare) {
            return "/";
        }
        return String.valueOf(frameResult.getRollScore());
    }

    private static String pad(int i) {
        if(i<10)   return " " + i + " ";
        if(i<100)  return " " + i;
        return String.valueOf(i);
    }
}
