package domain.move;

import domain.Board;
import domain.Position;
import domain.piece.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static domain.piece.PieceColor.*;
import static domain.piece.PieceType.*;
import static org.junit.jupiter.api.Assertions.*;

public class PromotionTest {
    private final Board board = new Board();

    @Test
    public void choices_inDialogOrder_areQueenRookBishopKnight() {
        assertEquals(List.of(QUEEN, ROOK, BISHOP, KNIGHT), Promotion.CHOICES);
    }

    @Test
    public void isDue_pawnOnItsLastRow_returnsTrue() {
        place(at(0, 3), PAWN, WHITE);
        place(at(7, 3), PAWN, BLACK);

        assertTrue(Promotion.isDue(board, at(0, 3)));
        assertTrue(Promotion.isDue(board, at(7, 3)));
    }

    @Test
    public void isDue_pawnOneRowShort_returnsFalse() {
        place(at(1, 3), PAWN, WHITE);
        place(at(6, 3), PAWN, BLACK);

        assertFalse(Promotion.isDue(board, at(1, 3)));
        assertFalse(Promotion.isDue(board, at(6, 3)));
    }

    @Test
    public void isDue_pawnOnOtherSidesLastRow_returnsFalse() {
        place(at(7, 3), PAWN, WHITE);
        place(at(0, 3), PAWN, BLACK);

        assertFalse(Promotion.isDue(board, at(7, 3)));
        assertFalse(Promotion.isDue(board, at(0, 3)));
    }

    @Test
    public void isDue_emptySquare_returnsFalse() {
        assertFalse(Promotion.isDue(board, at(0, 3)));
    }

    @Test
    public void isDue_notAPawn_returnsFalse() {
        place(at(0, 3), QUEEN, WHITE);

        assertFalse(Promotion.isDue(board, at(0, 3)));
    }

    private void place(Position position, PieceType type, PieceColor color) {
        board.placePiece(position, new Piece(type, color));
    }

    private static Position at(int row, int col) {
        return new Position(row, col);
    }
}
