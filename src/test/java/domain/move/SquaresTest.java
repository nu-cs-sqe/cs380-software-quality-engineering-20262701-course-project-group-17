package domain.move;

import domain.Board;
import domain.Position;
import domain.piece.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

public class SquaresTest {
    private static final Position TARGET = new Position(3, 4);

    private final Board board = new Board();

    @Test
    public void isEnemy_emptySquare_returnsFalse() {
        assertFalse(Squares.isEnemy(board, TARGET, PieceColor.WHITE));
    }

    @ParameterizedTest
    @EnumSource(PieceColor.class)
    public void isEnemy_otherColor_returnsTrue(PieceColor color) {
        board.placePiece(TARGET, new Piece(PieceType.PAWN, opposite(color)));

        assertTrue(Squares.isEnemy(board, TARGET, color));
    }

    @Test
    public void isEnemy_sameColor_returnsFalse() {
        board.placePiece(TARGET, new Piece(PieceType.PAWN, PieceColor.WHITE));

        assertFalse(Squares.isEnemy(board, TARGET, PieceColor.WHITE));
    }

    @Test
    public void isEmptyOrEnemy_emptySquare_returnsTrue() {
        assertTrue(Squares.isEmptyOrEnemy(board, TARGET, PieceColor.WHITE));
    }

    @Test
    public void isEmptyOrEnemy_otherColor_returnsTrue() {
        board.placePiece(TARGET, new Piece(PieceType.PAWN, PieceColor.BLACK));

        assertTrue(Squares.isEmptyOrEnemy(board, TARGET, PieceColor.WHITE));
    }

    @Test
    public void isEmptyOrEnemy_sameColor_returnsFalse() {
        board.placePiece(TARGET, new Piece(PieceType.PAWN, PieceColor.BLACK));

        assertFalse(Squares.isEmptyOrEnemy(board, TARGET, PieceColor.BLACK));
    }

    private static PieceColor opposite(PieceColor color) {
        return color == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE;
    }
}
