package domain.move;

import domain.Board;
import domain.Position;
import domain.piece.*;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static domain.move.PositionSets.squares;
import static domain.piece.PieceColor.*;
import static org.junit.jupiter.api.Assertions.*;

public class SlideRuleTest {
    private static final Position CENTER = new Position(3, 3);
    private static final Set<Position> ROOK_FROM_CENTER = squares(
            3, 0, 3, 1, 3, 2, 3, 4, 3, 5, 3, 6, 3, 7,
            0, 3, 1, 3, 2, 3, 4, 3, 5, 3, 6, 3, 7, 3);

    private final Board board = new Board();

    @Test
    public void rook_centerOfEmptyBoard_hasFourteenMoves() {
        assertEquals(ROOK_FROM_CENTER, SlideRule.rook().destinations(board, CENTER, WHITE));
    }

    @Test
    public void rook_cornerOfEmptyBoard_hasFourteenMoves() {
        Set<Position> expected = squares(
                0, 1, 0, 2, 0, 3, 0, 4, 0, 5, 0, 6, 0, 7,
                1, 0, 2, 0, 3, 0, 4, 0, 5, 0, 6, 0, 7, 0);

        assertEquals(expected, SlideRule.rook().destinations(board, new Position(0, 0), WHITE));
    }
}
