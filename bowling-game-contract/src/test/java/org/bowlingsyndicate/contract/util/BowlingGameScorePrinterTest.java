package org.bowlingsyndicate.contract.util;

import org.bowlingsyndicate.domain.BowlingFrame;
import org.bowlingsyndicate.domain.BowlingFrameScore;
import org.bowlingsyndicate.util.BowlingGameScorePrinter;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

class BowlingGameScorePrinterTest {
    private final ByteArrayOutputStream stream = new ByteArrayOutputStream();
    private final PrintStream ps = new PrintStream(stream);

    @Test
    public void newGame() {
        BowlingGameScorePrinter.printScores(ps, emptyList(), emptyList());

        assertThat(stream.toString()).isEqualTo(
                "" +
                        "-------------------------------------------------------------------------------------\n" +
                        "|   1   |   2   |   3   |   4   |   5   |   6   |   7   |   8   |   9   |     10    |\n" +
                        "|---+---|---+---|---+---|---+---|---+---|---+---|---+---|---+---|---+---|---+---+---|\n" +
                        "|   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n" +
                        "|   +---|   +---|   +---|   +---|   +---|   +---|   +---|   +---|   +---|   +---+---|\n" +
                        "|       |       |       |       |       |       |       |       |       |           |\n" +
                        "--------+-------+-------+-------+-------+-------+-------+-------+-------+------------\n");
    }

    @Test
    public void loserGame() {
        List<BowlingFrame>      frames = IntStream.range(0, 10).mapToObj(x -> frame(0, 0)).collect(toList());
        List<BowlingFrameScore> scores = IntStream.range(0, 10).mapToObj(x -> new BowlingFrameScore(0, 0)).collect(toList());

        BowlingGameScorePrinter.printScores(ps,frames, scores);
        assertThat(stream.toString()).isEqualTo(
                "" +
                        "-------------------------------------------------------------------------------------\n" +
                        "|   1   |   2   |   3   |   4   |   5   |   6   |   7   |   8   |   9   |     10    |\n" +
                        "|---+---|---+---|---+---|---+---|---+---|---+---|---+---|---+---|---+---|---+---+---|\n" +
                        "| 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 |   |\n" +
                        "|   +---|   +---|   +---|   +---|   +---|   +---|   +---|   +---|   +---|   +---+---|\n" +
                        "|   0   |   0   |   0   |   0   |   0   |   0   |   0   |   0   |   0   |     0     |\n" +
                        "--------+-------+-------+-------+-------+-------+-------+-------+-------+------------\n");
    }

    @Test
    public void fullBlaze() {
        List<BowlingFrame> frames = List.of(
                frame(10),
                frame(10),
                frame(10),
                frame(10),
                frame(10),
                frame(10),
                frame(10),
                frame(10),
                frame(10),
                lastFrame(10, 10, 10));

        List<BowlingFrameScore> scores = scores(30, 60, 90, 120, 150, 180, 210, 240, 270, 300);

        BowlingGameScorePrinter.printScores(ps,frames, scores);
        assertThat(stream.toString()).isEqualTo(
                "" +
                        "-------------------------------------------------------------------------------------\n" +
                        "|   1   |   2   |   3   |   4   |   5   |   6   |   7   |   8   |   9   |     10    |\n" +
                        "|---+---|---+---|---+---|---+---|---+---|---+---|---+---|---+---|---+---|---+---+---|\n" +
                        "|   | X |   | X |   | X |   | X |   | X |   | X |   | X |   | X |   | X | X | X | X |\n" +
                        "|   +---|   +---|   +---|   +---|   +---|   +---|   +---|   +---|   +---|   +---+---|\n" +
                        "|   30  |   60  |   90  |  120  |  150  |  180  |  210  |  240  |  270  |    300    |\n" +
                        "--------+-------+-------+-------+-------+-------+-------+-------+-------+------------\n");
    }

    @Test
    public void spareMe() {
        List<BowlingFrame> frames = List.of(
                frame(0, 10),
                frame(1, 9),
                frame(2, 8),
                frame(3, 7),
                frame(4, 6),
                frame(5, 5),
                frame(6, 4),
                frame(7, 3),
                frame(8, 2),
                lastFrame(9, 1, 10));

        List<BowlingFrameScore> scores = scores(11, 23, 36, 50, 65, 81, 98, 116, 135, 155);

        BowlingGameScorePrinter.printScores(ps,frames, scores);
        assertThat(stream.toString()).isEqualTo(
                "" +
                        "-------------------------------------------------------------------------------------\n" +
                        "|   1   |   2   |   3   |   4   |   5   |   6   |   7   |   8   |   9   |     10    |\n" +
                        "|---+---|---+---|---+---|---+---|---+---|---+---|---+---|---+---|---+---|---+---+---|\n" +
                        "| 0 | / | 1 | / | 2 | / | 3 | / | 4 | / | 5 | / | 6 | / | 7 | / | 8 | / | 9 | / | X |\n" +
                        "|   +---|   +---|   +---|   +---|   +---|   +---|   +---|   +---|   +---|   +---+---|\n" +
                        "|   11  |   23  |   36  |   50  |   65  |   81  |   98  |  116  |  135  |    155    |\n" +
                        "--------+-------+-------+-------+-------+-------+-------+-------+-------+------------\n");
    }

    @Test
    void godsMistakes() {
        List<BowlingFrame> frames = List.of(
                frame(5, 0),
                frame(4, 5),
                frame(7, 0),
                frame(4, 6),
                frame(10),
                frame(6, 3),
                frame(4, 0),
                frame(8, 0),
                frame(0, 3),
                lastFrame(1, 5));

        List<BowlingFrameScore> scores = scores(5,14,21,41,60,69,73,81,84,90);

        BowlingGameScorePrinter.printScores(ps, frames, scores);
        assertThat(stream.toString()).isEqualTo(
                "" +
                        "-------------------------------------------------------------------------------------\n" +
                        "|   1   |   2   |   3   |   4   |   5   |   6   |   7   |   8   |   9   |     10    |\n" +
                        "|---+---|---+---|---+---|---+---|---+---|---+---|---+---|---+---|---+---|---+---+---|\n" +
                        "| 5 | 0 | 4 | 5 | 7 | 0 | 4 | / |   | X | 6 | 3 | 4 | 0 | 8 | 0 | 0 | 3 | 1 | 5 |   |\n" +
                        "|   +---|   +---|   +---|   +---|   +---|   +---|   +---|   +---|   +---|   +---+---|\n" +
                        "|   5   |   14  |   21  |   41  |   60  |   69  |   73  |   81  |   84  |     90    |\n" +
                        "--------+-------+-------+-------+-------+-------+-------+-------+-------+------------\n");
    }


    @Test
    void iShouldGoHomeBeforeItsMoreEmbarrassing() {
        List<BowlingFrame> frames = List.of(
                frame(0, 0),
                frame(0, 0),
                frame(0, 0),
                frame(0));

        List<BowlingFrameScore> scores = scores(0,0,0);

        BowlingGameScorePrinter.printScores(ps, frames, scores);
        assertThat(stream.toString()).isEqualTo(
                "" +
                        "-------------------------------------------------------------------------------------\n" +
                        "|   1   |   2   |   3   |   4   |   5   |   6   |   7   |   8   |   9   |     10    |\n" +
                        "|---+---|---+---|---+---|---+---|---+---|---+---|---+---|---+---|---+---|---+---+---|\n" +
                        "| 0 | 0 | 0 | 0 | 0 | 0 | 0 |   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n" +
                        "|   +---|   +---|   +---|   +---|   +---|   +---|   +---|   +---|   +---|   +---+---|\n" +
                        "|   0   |   0   |   0   |       |       |       |       |       |       |           |\n" +
                        "--------+-------+-------+-------+-------+-------+-------+-------+-------+------------\n");
    }

    @Test
    void thisIsGoingToBeGreat() {
        List<BowlingFrame> frames = List.of(
                frame(5, 5),
                frame(6, 4),
                frame(1, 9),
                frame(4, 6),
                frame(2));

        List<BowlingFrameScore> scores = scores(16,27,41,53);

        BowlingGameScorePrinter.printScores(ps, frames, scores);
        assertThat(stream.toString()).isEqualTo(
                "" +
                        "-------------------------------------------------------------------------------------\n" +
                        "|   1   |   2   |   3   |   4   |   5   |   6   |   7   |   8   |   9   |     10    |\n" +
                        "|---+---|---+---|---+---|---+---|---+---|---+---|---+---|---+---|---+---|---+---+---|\n" +
                        "| 5 | / | 6 | / | 1 | / | 4 | / | 2 |   |   |   |   |   |   |   |   |   |   |   |   |\n" +
                        "|   +---|   +---|   +---|   +---|   +---|   +---|   +---|   +---|   +---|   +---+---|\n" +
                        "|   16  |   27  |   41  |   53  |       |       |       |       |       |           |\n" +
                        "--------+-------+-------+-------+-------+-------+-------+-------+-------+------------\n");
    }

    @Test
    void theFireIsLit() {
        List<BowlingFrame> frames = List.of(
                frame(10),
                frame(10),
                frame(10));

        List<BowlingFrameScore> scores = scores(30);

        BowlingGameScorePrinter.printScores(ps, frames, scores);
        assertThat(stream.toString()).isEqualTo(
                "" +
                        "-------------------------------------------------------------------------------------\n" +
                        "|   1   |   2   |   3   |   4   |   5   |   6   |   7   |   8   |   9   |     10    |\n" +
                        "|---+---|---+---|---+---|---+---|---+---|---+---|---+---|---+---|---+---|---+---+---|\n" +
                        "|   | X |   | X |   | X |   |   |   |   |   |   |   |   |   |   |   |   |   |   |   |\n" +
                        "|   +---|   +---|   +---|   +---|   +---|   +---|   +---|   +---|   +---|   +---+---|\n" +
                        "|   30  |       |       |       |       |       |       |       |       |           |\n" +
                        "--------+-------+-------+-------+-------+-------+-------+-------+-------+------------\n");
    }

    @Test
    void thisIsJustMeh() {
        List<BowlingFrame> frames = List.of(
                frame(3, 6),
                frame(3, 7),
                frame(3, 6),
                frame(10),
                frame(5, 3),
                frame(5));

        List<BowlingFrameScore> scores = scores(9,22,31,49,57);

        BowlingGameScorePrinter.printScores(ps, frames, scores);
        assertThat(stream.toString()).isEqualTo(
                "" +
                        "-------------------------------------------------------------------------------------\n" +
                        "|   1   |   2   |   3   |   4   |   5   |   6   |   7   |   8   |   9   |     10    |\n" +
                        "|---+---|---+---|---+---|---+---|---+---|---+---|---+---|---+---|---+---|---+---+---|\n" +
                        "| 3 | 6 | 3 | / | 3 | 6 |   | X | 5 | 3 | 5 |   |   |   |   |   |   |   |   |   |   |\n" +
                        "|   +---|   +---|   +---|   +---|   +---|   +---|   +---|   +---|   +---|   +---+---|\n" +
                        "|   9   |   22  |   31  |   49  |   57  |       |       |       |       |           |\n" +
                        "--------+-------+-------+-------+-------+-------+-------+-------+-------+------------\n");
    }


    private List<BowlingFrameScore> scores(int ... scores) {
        // Never print frame score, only accumulated score, so we ignore the value.
        return Arrays.stream(scores).mapToObj(x->new BowlingFrameScore(0, x)).collect(toList());
    }


    private BowlingFrame frame(Integer ... rolls) {
        BowlingFrame frame = BowlingFrame.emptyFrame();
        for (Integer roll : rolls) {
            frame.addRollResult(roll);
        }
        return frame;
    }

    private BowlingFrame lastFrame(Integer ... rolls) {
        BowlingFrame frame = BowlingFrame.emptyLastFrameFrame();
        for (Integer roll : rolls) {
            frame.addRollResult(roll);
        }
        return frame;
    }
}