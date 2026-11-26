package domain.move;

import domain.Board;
import domain.Position;
import domain.piece.*;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static domain.piece.PieceColor.*;
import static org.junit.jupiter.api.Assertions.*;

public class JumpRuleTest {
    private static final Position CENTER = new Position(3, 3);

    private final Board board = new Board();

    @Test
    public void knight_center_hasEightMoves() {
        Set<Position> moves = JumpRule.knight().destinations(board, CENTER, WHITE);

        assertEquals(squares(1, 2, 1, 4, 2, 1, 2, 5, 4, 1, 4, 5, 5, 2, 5, 4), moves);
    }

    private static Set<Position> squares(int... rowColPairs) {
        Set<Position> result = new HashSet<>();
        for (int i = 0; i < rowColPairs.length; i += 2) {
            result.add(new Position(rowColPairs[i], rowColPairs[i + 1]));
        }
        return result;
    }
}
