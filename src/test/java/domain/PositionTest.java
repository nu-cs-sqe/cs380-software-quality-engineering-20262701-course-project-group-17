package domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {

    @ParameterizedTest
    @CsvSource({"0, 0", "7, 7"})
    public void constructor_onBoardBoundaries_storesRowAndCol(int row, int col) {
        Position position = new Position(row, col);

        assertEquals(row, position.getRow());
        assertEquals(col, position.getCol());
    }

    @Test
    public void constructor_differentRowAndCol_keepsThemApart() {
        Position position = new Position(3, 4);

        assertEquals(3, position.getRow());
        assertEquals(4, position.getCol());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 8})
    public void constructor_rowOffBoard_throwsIllegalArgumentException(int row) {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new Position(row, 0));

        assertEquals("row must be between 0 and 7, but was " + row, exception.getMessage());
    }
}
