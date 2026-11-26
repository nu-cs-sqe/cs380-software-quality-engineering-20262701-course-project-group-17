package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MoveTest {
    private static final Position FROM = new Position(1, 3);
    private static final Position TO = new Position(3, 3);

    @Test
    public void constructor_twoSquares_keepsFromAndTo() {
        Move move = new Move(FROM, TO);

        assertEquals(FROM, move.getFrom());
        assertEquals(TO, move.getTo());
    }

    @Test
    public void constructor_nullFrom_throwsNullPointerException() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> new Move(null, TO));

        assertEquals("from must not be null", exception.getMessage());
    }

    @Test
    public void constructor_nullTo_throwsNullPointerException() {
        NullPointerException exception = assertThrows(NullPointerException.class, () -> new Move(FROM, null));

        assertEquals("to must not be null", exception.getMessage());
    }

    @Test
    public void constructor_sameSquare_throwsIllegalArgumentException() {
        IllegalArgumentException sameInstance =
                assertThrows(IllegalArgumentException.class, () -> new Move(TO, TO));
        IllegalArgumentException equalSquare =
                assertThrows(IllegalArgumentException.class, () -> new Move(TO, new Position(3, 3)));

        assertEquals("a move must change square", sameInstance.getMessage());
        assertEquals("a move must change square", equalSquare.getMessage());
    }
}
