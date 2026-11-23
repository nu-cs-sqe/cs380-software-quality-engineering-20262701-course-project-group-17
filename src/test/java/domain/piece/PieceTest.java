package domain.piece;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static domain.piece.PieceColor.*;
import static domain.piece.PieceType.*;
import static org.junit.jupiter.api.Assertions.*;

public class PieceTest {

    @Test
    public void constructor_validTypeAndColor_storesBoth() {
        Piece whiteKing = new Piece(KING, WHITE);

        assertEquals(KING, whiteKing.getType());
        assertEquals(WHITE, whiteKing.getColor());
    }

    @Test
    public void constructor_nullType_throwsNullPointerException() {
        NullPointerException exception =
                assertThrows(NullPointerException.class, () -> new Piece(null, WHITE));

        assertEquals("type must not be null", exception.getMessage());
    }

    @Test
    public void constructor_nullColor_throwsNullPointerException() {
        NullPointerException exception =
                assertThrows(NullPointerException.class, () -> new Piece(KING, null));

        assertEquals("color must not be null", exception.getMessage());
    }

    @Test
    public void isSameColorAs_sameColor_returnsTrue() {
        assertTrue(new Piece(KING, WHITE).isSameColorAs(new Piece(PAWN, WHITE)));
    }

    @Test
    public void isSameColorAs_differentColor_returnsFalse() {
        assertFalse(new Piece(KING, WHITE).isSameColorAs(new Piece(KING, BLACK)));
    }

    @Test
    public void isSameColorAs_null_throwsNullPointerException() {
        Piece whiteKing = new Piece(KING, WHITE);

        NullPointerException exception =
                assertThrows(NullPointerException.class, () -> whiteKing.isSameColorAs(null));

        assertEquals("other must not be null", exception.getMessage());
    }

    @Test
    public void equals_sameTypeAndColor_returnsTrue() {
        assertEquals(new Piece(ROOK, BLACK), new Piece(ROOK, BLACK));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        assertNotEquals(new Piece(ROOK, BLACK), new Piece(BISHOP, BLACK));
    }

    @Test
    public void equals_differentColor_returnsFalse() {
        assertNotEquals(new Piece(ROOK, BLACK), new Piece(ROOK, WHITE));
    }

    @Test
    public void equals_null_returnsFalse() {
        assertNotEquals(new Piece(ROOK, BLACK), null);
    }

    @Test
    public void equals_differentClass_returnsFalse() {
        assertNotEquals(new Piece(ROOK, BLACK), ROOK);
    }

    @Test
    public void hashCode_equalPieces_returnsSameValue() {
        assertEquals(new Piece(QUEEN, WHITE).hashCode(), new Piece(QUEEN, WHITE).hashCode());
    }

    @ParameterizedTest
    @CsvSource({"KING, WHITE, 0", "KING, BLACK, 1", "QUEEN, WHITE, 2", "PAWN, BLACK, 11"})
    public void hashCode_anyPiece_returnsPieceIndex(PieceType type, PieceColor color, int expectedIndex) {
        assertEquals(expectedIndex, new Piece(type, color).hashCode());
    }
}
