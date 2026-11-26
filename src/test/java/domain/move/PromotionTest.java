package domain.move;

import domain.Board;
import domain.Position;
import domain.piece.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.List;
import java.util.Optional;

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

    @Test
    public void isDue_nullArgument_throwsNullPointerException() {
        NullPointerException nullBoard =
                assertThrows(NullPointerException.class, () -> Promotion.isDue(null, at(0, 3)));
        NullPointerException nullPosition =
                assertThrows(NullPointerException.class, () -> Promotion.isDue(board, null));

        assertEquals("board must not be null", nullBoard.getMessage());
        assertEquals("position must not be null", nullPosition.getMessage());
    }

    @ParameterizedTest
    @EnumSource(value = PieceType.class, names = {"QUEEN", "ROOK", "BISHOP", "KNIGHT"})
    public void promote_allowedChoice_replacesThePawn(PieceType choice) {
        place(at(0, 3), PAWN, WHITE);

        Promotion.promote(board, at(0, 3), choice);

        assertEquals(Optional.of(new Piece(choice, WHITE)), board.getPiece(at(0, 3)));
    }

    @Test
    public void promote_blackPawn_keepsItsColor() {
        place(at(7, 4), PAWN, BLACK);

        Promotion.promote(board, at(7, 4), KNIGHT);

        assertEquals(Optional.of(new Piece(KNIGHT, BLACK)), board.getPiece(at(7, 4)));
    }

    @ParameterizedTest
    @EnumSource(value = PieceType.class, names = {"KING", "PAWN"})
    public void promote_choiceNotAllowed_throwsAndLeavesThePawn(PieceType choice) {
        place(at(0, 3), PAWN, WHITE);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> Promotion.promote(board, at(0, 3), choice));

        assertEquals("a pawn can only be promoted to a queen, rook, bishop, or knight", exception.getMessage());
        assertEquals(Optional.of(new Piece(PAWN, WHITE)), board.getPiece(at(0, 3)));
    }

    @Test
    public void promote_noPromotionDue_throwsAndLeavesTheBoard() {
        place(at(1, 3), PAWN, WHITE);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> Promotion.promote(board, at(1, 3), QUEEN));

        assertEquals("position must hold a pawn on its last row", exception.getMessage());
        assertEquals(Optional.of(new Piece(PAWN, WHITE)), board.getPiece(at(1, 3)));
    }

    @Test
    public void promote_nullArgument_throwsNullPointerException() {
        place(at(0, 3), PAWN, WHITE);

        NullPointerException nullBoard =
                assertThrows(NullPointerException.class, () -> Promotion.promote(null, at(0, 3), QUEEN));
        NullPointerException nullPosition =
                assertThrows(NullPointerException.class, () -> Promotion.promote(board, null, QUEEN));
        NullPointerException nullChoice =
                assertThrows(NullPointerException.class, () -> Promotion.promote(board, at(0, 3), null));

        assertEquals("board must not be null", nullBoard.getMessage());
        assertEquals("position must not be null", nullPosition.getMessage());
        assertEquals("choice must not be null", nullChoice.getMessage());
    }

    private void place(Position position, PieceType type, PieceColor color) {
        board.placePiece(position, new Piece(type, color));
    }

    private static Position at(int row, int col) {
        return new Position(row, col);
    }
}
