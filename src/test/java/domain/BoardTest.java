package domain;

import domain.piece.*;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    private static final PieceType[] BACK_RANK = {
        PieceType.ROOK, PieceType.KNIGHT, PieceType.BISHOP, PieceType.QUEEN,
        PieceType.KING, PieceType.BISHOP, PieceType.KNIGHT, PieceType.ROOK
    };

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

        assertPiece(PieceType.KING, PieceColor.WHITE, board.getPiece(new Position(0, 0)));
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

        assertPiece(PieceType.KING, PieceColor.WHITE, board.getPiece(new Position(7, 7)));
    }

    @Test
    public void placePiece_rowZeroColSeven_doesNotSwapRowAndCol() {
        Board board = new Board();

        board.placePiece(new Position(0, 7), whiteKing());

        assertPiece(PieceType.KING, PieceColor.WHITE, board.getPiece(new Position(0, 7)));
        assertTrue(board.isEmpty(new Position(7, 0)));
    }


    @Test
    public void placePiece_rowSevenColZero_doesNotSwapRowAndCol() {
        Board board = new Board();

        board.placePiece(new Position(7, 0), whiteKing());

        assertPiece(PieceType.KING, PieceColor.WHITE, board.getPiece(new Position(7, 0)));
        assertTrue(board.isEmpty(new Position(0, 7)));
    }

    @Test
    public void placePiece_innerSquare_onlyThatSquareChanges() {
        Board board = new Board();

        board.placePiece(new Position(3, 4), whiteKing());

        assertPiece(PieceType.KING, PieceColor.WHITE, board.getPiece(new Position(3, 4)));
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

        assertPiece(PieceType.QUEEN, PieceColor.BLACK, board.getPiece(new Position(3, 4)));
    }

    @Test
    public void placePiece_separateEqualPosition_findsPiece() {
        Board board = new Board();
        Position placedAt = new Position(3, 4);
        Position readAt = new Position(3, 4);

        board.placePiece(placedAt, whiteKing());

        assertPiece(PieceType.KING, PieceColor.WHITE, board.getPiece(readAt));
    }


    @Test
    public void placePiece_nullPosition_throwsNullPointerException() {
        Board board = new Board();

        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> board.placePiece(null, whiteKing()));

        assertEquals("position must not be null", exception.getMessage());
    }


    @Test
    public void placePiece_nullPiece_throwsNullPointerException() {
        Board board = new Board();

        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> board.placePiece(new Position(3, 4), null));

        assertEquals("piece must not be null", exception.getMessage());
    }


    @Test
    public void getPiece_occupiedSquare_returnsPiece() {
        Board board = new Board();
        board.placePiece(new Position(3, 4), whiteKing());

        Optional<Piece> piece = board.getPiece(new Position(3, 4));

        assertTrue(piece.isPresent());
        assertEquals(PieceType.KING, piece.get().getType());
        assertEquals(PieceColor.WHITE, piece.get().getColor());
    }

    @Test
    public void getPiece_emptySquare_returnsEmptyOptional() {
        Board board = new Board();
        board.placePiece(new Position(3, 4), whiteKing());

        assertEquals(Optional.empty(), board.getPiece(new Position(3, 5)));
    }


    @Test
    public void getPiece_nullPosition_throwsNullPointerException() {
        Board board = new Board();

        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> board.getPiece(null));

        assertEquals("position must not be null", exception.getMessage());
    }


    @Test
    public void isEmpty_emptySquare_returnsTrue() {
        Board board = new Board();
        board.placePiece(new Position(3, 4), whiteKing());

        assertTrue(board.isEmpty(new Position(3, 5)));
    }

    @Test
    public void isEmpty_nullPosition_throwsNullPointerException() {
        Board board = new Board();

        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> board.isEmpty(null));

        assertEquals("position must not be null", exception.getMessage());
    }

    @Test
    public void createStandard_rowZero_hasBlackBackRank() {
        Board board = Board.createStandard();

        for (int col = 0; col <= 7; col++) {
            assertPiece(BACK_RANK[col], PieceColor.BLACK, board.getPiece(new Position(0, col)));
        }
    }

    @Test
    public void createStandard_rowOne_hasBlackPawns() {
        Board board = Board.createStandard();

        for (int col = 0; col <= 7; col++) {
            assertPiece(PieceType.PAWN, PieceColor.BLACK, board.getPiece(new Position(1, col)));
        }
    }

    @Test
    public void createStandard_rowsTwoToFive_areEmpty() {
        Board board = Board.createStandard();

        for (int row = 2; row <= 5; row++) {
            for (int col = 0; col <= 7; col++) {
                assertTrue(board.isEmpty(new Position(row, col)));
            }
        }
    }

    @Test
    public void createStandard_rowSix_hasWhitePawns() {
        Board board = Board.createStandard();

        for (int col = 0; col <= 7; col++) {
            assertPiece(PieceType.PAWN, PieceColor.WHITE, board.getPiece(new Position(6, col)));
        }
    }

    @Test
    public void createStandard_rowSeven_hasWhiteBackRank() {
        Board board = Board.createStandard();

        for (int col = 0; col <= 7; col++) {
            assertPiece(BACK_RANK[col], PieceColor.WHITE, board.getPiece(new Position(7, col)));
        }
    }

    private static Piece whiteKing() {
        return new Piece(PieceType.KING, PieceColor.WHITE);
    }

    private static void assertPiece(PieceType type, PieceColor color, Optional<Piece> actual) {
        Piece piece = actual.orElseThrow();
        assertEquals(type, piece.getType());
        assertEquals(color, piece.getColor());
    }
}
