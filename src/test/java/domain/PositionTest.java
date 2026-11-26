package domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.Optional;

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

    @ParameterizedTest
    @ValueSource(ints = {-1, 8})
    public void constructor_colOffBoard_throwsIllegalArgumentException(int col) {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new Position(0, col));

        assertEquals("col must be between 0 and 7, but was " + col, exception.getMessage());
    }

    @Test
    public void equals_sameInstance_returnsTrue() {
        Position position = new Position(3, 4);

        assertTrue(position.equals(position));
    }

    @Test
    public void equals_sameRowAndCol_returnsTrue() {
        assertEquals(new Position(3, 4), new Position(3, 4));
    }

    @Test
    public void equals_differentRow_returnsFalse() {
        assertNotEquals(new Position(3, 4), new Position(2, 4));
    }

    @Test
    public void equals_differentCol_returnsFalse() {
        assertNotEquals(new Position(3, 4), new Position(3, 5));
    }

    @Test
    public void equals_null_returnsFalse() {
        assertNotEquals(new Position(3, 4), null);
    }

    @Test
    public void equals_differentType_returnsFalse() {
        assertNotEquals(new Position(3, 4), "3,4");
    }

    @Test
    public void hashCode_equalPositions_returnsSameValue() {
        assertEquals(new Position(3, 4).hashCode(), new Position(3, 4).hashCode());
    }

    @ParameterizedTest
    @CsvSource({"0, 0, 0", "0, 1, 1", "1, 0, 8", "7, 7, 63"})
    public void hashCode_anySquare_returnsSquareIndex(int row, int col, int expectedIndex) {
        assertEquals(expectedIndex, new Position(row, col).hashCode());
    }

    @Test
    public void translate_stepOnBoard_returnsNewPosition() {
        Position position = new Position(3, 4);

        assertEquals(Optional.of(new Position(4, 2)), position.translate(new Direction(1, -2)));
    }

    @Test
    public void translate_cornerToCorner_returnsFarCorner() {
        Position corner = new Position(0, 0);

        assertEquals(Optional.of(new Position(7, 7)), corner.translate(new Direction(7, 7)));
    }
}
