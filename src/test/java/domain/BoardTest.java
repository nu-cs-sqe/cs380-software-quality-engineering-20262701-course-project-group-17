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


    @Test
    public void isEmpty_occupiedSquare_returnsFalse() {
        Board board = new Board();
        board.placePiece(new Position(3, 4), whiteKing());

        assertFalse(board.isEmpty(new Position(3, 4)));
    }


    @Test
    public void placePiece_upperCorner_pieceIsStored() {
        Board board = new Board();

        board.placePiece(new Position(7, 7), whiteKing());

        assertHolds(board, new Position(7, 7), PieceType.KING, PieceColor.WHITE);
    }

    @Test
    public void placePiece_rowZeroColSeven_doesNotSwapRowAndCol() {
        Board board = new Board();

        board.placePiece(new Position(0, 7), whiteKing());

        assertHolds(board, new Position(0, 7), PieceType.KING, PieceColor.WHITE);
        assertTrue(board.isEmpty(new Position(7, 0)));
    }


    @Test
    public void placePiece_rowSevenColZero_doesNotSwapRowAndCol() {
        Board board = new Board();

        board.placePiece(new Position(7, 0), whiteKing());

        assertHolds(board, new Position(7, 0), PieceType.KING, PieceColor.WHITE);
        assertTrue(board.isEmpty(new Position(0, 7)));
    }

    @Test
    public void placePiece_innerSquare_onlyThatSquareChanges() {
        Board board = new Board();

        board.placePiece(new Position(3, 4), whiteKing());

        assertHolds(board, new Position(3, 4), PieceType.KING, PieceColor.WHITE);
        assertTrue(board.isEmpty(new Position(2, 4)));
        assertTrue(board.isEmpty(new Position(4, 4)));
        assertTrue(board.isEmpty(new Position(3, 3)));
        assertTrue(board.isEmpty(new Position(3, 5)));
    }

    @Test
    public void placePiece_occupiedSquare_replacesPiece() {
        Board board = new Board();
        board.placePiece(new Position(3, 4), whiteKing());

        board.placePiece(new Position(3, 4), new Piece(PieceType.QUEEN, PieceColor.BLACK));

        assertHolds(board, new Position(3, 4), PieceType.QUEEN, PieceColor.BLACK);
    }

    @Test
    public void placePiece_separateEqualPosition_findsPiece() {
        Board board = new Board();
        Position placedAt = new Position(3, 4);
        Position readAt = new Position(3, 4);

        board.placePiece(placedAt, whiteKing());

        assertHolds(board, readAt, PieceType.KING, PieceColor.WHITE);
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
