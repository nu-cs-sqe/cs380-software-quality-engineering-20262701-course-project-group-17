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
}
