package domain;

import domain.piece.*;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    public void constructor_newBoard_allSquaresAreEmpty() {
        Board board = new Board();

        for (int row = 0; row <= 7; row++) {
            for (int col = 0; col <= 7; col++) {
                assertTrue(board.isEmpty(new Position(row, col)));
            }
        }
    }

    @Test
    public void constructor_newBoard_getPieceReturnsEmptyForAllSquares() {
        Board board = new Board();

        for (int row = 0; row <= 7; row++) {
            for (int col = 0; col <= 7; col++) {
                assertEquals(Optional.empty(), board.getPiece(new Position(row, col)));
            }
        }
    }

    @Test
    public void placePiece_lowerCorner_pieceIsStored() {
        Board board = new Board();

        board.placePiece(new Position(0, 0), whiteKing());

        assertHolds(board, new Position(0, 0), PieceType.KING, PieceColor.WHITE);
    }

    private static Piece whiteKing() {
        return new Piece(PieceType.KING, PieceColor.WHITE);
    }

    private static void assertHolds(Board board, Position position, PieceType type, PieceColor color) {
        Piece piece = board.getPiece(position).orElseThrow();
        assertEquals(type, piece.getType());
        assertEquals(color, piece.getColor());
    }
}
