package domain;

import domain.piece.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
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

        for (Position position : allPositions()) {
            assertTrue(board.isEmpty(position));
        }
    }

    @Test
    public void constructor_newBoard_getPieceReturnsEmptyForAllSquares() {
        Board board = new Board();

        for (Position position : allPositions()) {
            assertEquals(Optional.empty(), board.getPiece(position));
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

        assertPiece(PieceType.KING, PieceColor.WHITE, piece);
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

        for (int col = 0; col < Position.BOARD_SIZE; col++) {
            assertPiece(BACK_RANK[col], PieceColor.BLACK, board.getPiece(new Position(0, col)));
        }
    }

    @Test
    public void createStandard_rowOne_hasBlackPawns() {
        Board board = Board.createStandard();

        for (int col = 0; col < Position.BOARD_SIZE; col++) {
            assertPiece(PieceType.PAWN, PieceColor.BLACK, board.getPiece(new Position(1, col)));
        }
    }

    @Test
    public void createStandard_rowsTwoToFive_areEmpty() {
        Board board = Board.createStandard();

        for (int row = 2; row <= 5; row++) {
            for (int col = 0; col < Position.BOARD_SIZE; col++) {
                assertTrue(board.isEmpty(new Position(row, col)));
            }
        }
    }

    @Test
    public void createStandard_rowSix_hasWhitePawns() {
        Board board = Board.createStandard();

        for (int col = 0; col < Position.BOARD_SIZE; col++) {
            assertPiece(PieceType.PAWN, PieceColor.WHITE, board.getPiece(new Position(6, col)));
        }
    }

    @Test
    public void createStandard_rowSeven_hasWhiteBackRank() {
        Board board = Board.createStandard();

        for (int col = 0; col < Position.BOARD_SIZE; col++) {
            assertPiece(BACK_RANK[col], PieceColor.WHITE, board.getPiece(new Position(7, col)));
        }
    }

    @Test
    public void createStandard_allSquares_hasSixteenPiecesPerColor() {
        Board board = Board.createStandard();

        assertEquals(32, countAllPieces(board));
        for (PieceColor color : PieceColor.values()) {
            assertEquals(8, countPieces(board, PieceType.PAWN, color));
            assertEquals(2, countPieces(board, PieceType.ROOK, color));
            assertEquals(2, countPieces(board, PieceType.KNIGHT, color));
            assertEquals(2, countPieces(board, PieceType.BISHOP, color));
            assertEquals(1, countPieces(board, PieceType.QUEEN, color));
            assertEquals(1, countPieces(board, PieceType.KING, color));
        }
    }

    @Test
    public void createStandard_calledTwice_returnsIndependentBoards() {
        Board first = Board.createStandard();
        Board second = Board.createStandard();

        first.placePiece(new Position(4, 4), whiteKing());

        assertTrue(second.isEmpty(new Position(4, 4)));
    }

    @Test
    public void movePiece_toNeighbour_movesPieceAndEmptiesFrom() {
        Board board = new Board();
        board.placePiece(new Position(3, 4), whiteKing());

        board.movePiece(new Position(3, 4), new Position(3, 5));

        assertPiece(PieceType.KING, PieceColor.WHITE, board.getPiece(new Position(3, 5)));
        assertTrue(board.isEmpty(new Position(3, 4)));
    }

    @Test
    public void movePiece_oppositeCorners_doesNotSwapRowAndCol() {
        Board board = new Board();
        board.placePiece(new Position(0, 7), new Piece(PieceType.QUEEN, PieceColor.BLACK));

        board.movePiece(new Position(0, 7), new Position(7, 0));

        assertPiece(PieceType.QUEEN, PieceColor.BLACK, board.getPiece(new Position(7, 0)));
        assertTrue(board.isEmpty(new Position(0, 7)));
    }

    @Test
    public void movePiece_emptyFrom_throwsIllegalArgumentException() {
        Board board = new Board();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> board.movePiece(new Position(3, 4), new Position(3, 5)));

        assertEquals("from must hold a piece", exception.getMessage());
    }

    private static Piece whiteKing() {
        return new Piece(PieceType.KING, PieceColor.WHITE);
    }

    private static void assertPiece(PieceType type, PieceColor color, Optional<Piece> actual) {
        assertTrue(actual.isPresent(), "expected a " + color + " " + type + " but the square was empty");
        assertEquals(type, actual.get().getType());
        assertEquals(color, actual.get().getColor());
    }

    private static int countAllPieces(Board board) {
        int count = 0;
        for (Position position : allPositions()) {
            if (!board.isEmpty(position)) {
                count++;
            }
        }
        return count;
    }

    private static int countPieces(Board board, PieceType type, PieceColor color) {
        int count = 0;
        for (Position position : allPositions()) {
            Optional<Piece> piece = board.getPiece(position);
            if (piece.isPresent() && isTypeAndColor(piece.get(), type, color)) {
                count++;
            }
        }
        return count;
    }

    private static boolean isTypeAndColor(Piece piece, PieceType type, PieceColor color) {
        return piece.getType() == type && piece.getColor() == color;
    }

    private static List<Position> allPositions() {
        List<Position> positions = new ArrayList<>();
        for (int row = 0; row < Position.BOARD_SIZE; row++) {
            for (int col = 0; col < Position.BOARD_SIZE; col++) {
                positions.add(new Position(row, col));
            }
        }
        return positions;
    }
}
