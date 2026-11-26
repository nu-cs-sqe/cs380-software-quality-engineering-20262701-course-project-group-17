package domain.move;

import domain.Board;
import domain.Position;
import domain.piece.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class PieceMovesTest {
    private static final Position CENTER = new Position(3, 3);
    private static final Position AHEAD_OF_CENTER = new Position(2, 3);

    private final Board board = new Board();
    private final PieceMoves pieceMoves = new PieceMoves();

    @ParameterizedTest
    @CsvSource({"KING, 8, true", "QUEEN, 27, true", "ROOK, 14, true",
        "BISHOP, 13, false", "KNIGHT, 8, false", "PAWN, 1, true"})
    public void from_eachPieceTypeAlone_usesItsOwnRule(PieceType type, int moveCount, boolean reachesAhead) {
        board.placePiece(CENTER, new Piece(type, PieceColor.WHITE));

        Set<Position> moves = pieceMoves.from(board, CENTER);

        assertEquals(moveCount, moves.size());
        assertEquals(reachesAhead, moves.contains(AHEAD_OF_CENTER));
    }

    @Test
    public void from_emptySquare_throwsIllegalArgumentException() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> pieceMoves.from(board, CENTER));

        assertEquals("position must hold a piece", exception.getMessage());
    }
}
