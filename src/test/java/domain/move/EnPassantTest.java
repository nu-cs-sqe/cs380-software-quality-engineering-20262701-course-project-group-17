package domain.move;

import domain.Board;
import domain.Move;
import domain.Position;
import domain.piece.*;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static domain.piece.PieceColor.*;
import static domain.piece.PieceType.*;
import static org.junit.jupiter.api.Assertions.*;

public class EnPassantTest {
    private static final Move BLACK_DOUBLE_STEP = new Move(at(1, 3), at(3, 3));

    private final Board board = new Board();

    @Test
    public void destination_enemyPawnDoubleStepBeside_returnsSkippedSquare() {
        place(at(3, 3), PAWN, BLACK);
        place(at(3, 4), PAWN, WHITE);
        place(at(3, 2), PAWN, WHITE);
        place(at(4, 4), PAWN, BLACK);
        place(at(4, 3), PAWN, WHITE);

        assertEquals(Optional.of(at(2, 3)), EnPassant.destination(board, at(3, 4), BLACK_DOUBLE_STEP));
        assertEquals(Optional.of(at(2, 3)), EnPassant.destination(board, at(3, 2), BLACK_DOUBLE_STEP));
        assertEquals(Optional.of(at(5, 3)), EnPassant.destination(board, at(4, 4), new Move(at(6, 3), at(4, 3))));
    }

    @Test
    public void destination_singleStep_returnsEmpty() {
        place(at(3, 3), PAWN, BLACK);
        place(at(3, 4), PAWN, WHITE);

        assertEquals(Optional.empty(), EnPassant.destination(board, at(3, 4), new Move(at(2, 3), at(3, 3))));
    }

    @Test
    public void destination_pawnNotBeside_returnsEmpty() {
        place(at(3, 3), PAWN, BLACK);
        place(at(3, 5), PAWN, WHITE);
        place(at(4, 4), PAWN, WHITE);

        assertEquals(Optional.empty(), EnPassant.destination(board, at(3, 5), BLACK_DOUBLE_STEP));
        assertEquals(Optional.empty(), EnPassant.destination(board, at(4, 4), BLACK_DOUBLE_STEP));
    }

    @Test
    public void destination_lastMoveNotByAPawn_returnsEmpty() {
        place(at(3, 3), ROOK, BLACK);
        place(at(3, 4), PAWN, WHITE);

        assertEquals(Optional.empty(), EnPassant.destination(board, at(3, 4), BLACK_DOUBLE_STEP));
    }

    private void place(Position position, PieceType type, PieceColor color) {
        board.placePiece(position, new Piece(type, color));
    }

    private static Position at(int row, int col) {
        return new Position(row, col);
    }
}
