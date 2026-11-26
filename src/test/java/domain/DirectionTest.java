package domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class DirectionTest {

    @Test
    public void constructor_knightJump_keepsRowAndColDeltas() {
        Direction direction = new Direction(-2, 1);

        assertEquals(-2, direction.getRowDelta());
        assertEquals(1, direction.getColDelta());
    }

    @ParameterizedTest
    @CsvSource({"0, 2", "1, 0"})
    public void constructor_oneDeltaZero_isAllowed(int rowDelta, int colDelta) {
        Direction direction = new Direction(rowDelta, colDelta);

        assertEquals(rowDelta, direction.getRowDelta());
        assertEquals(colDelta, direction.getColDelta());
    }
}
