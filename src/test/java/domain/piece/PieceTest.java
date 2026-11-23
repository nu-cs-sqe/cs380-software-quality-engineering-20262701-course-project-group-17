package domain.piece;

import org.junit.jupiter.api.Test;

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
}
