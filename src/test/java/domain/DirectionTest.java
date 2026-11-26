package domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Test
    public void constructor_zeroStep_throwsIllegalArgumentException() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new Direction(0, 0));

        assertEquals("a direction must move at least one square", exception.getMessage());
    }

    @Test
    public void orthogonal_fromCenter_reachesTheFourStraightNeighbours() {
        Set<Position> expected = Set.of(at(2, 3), at(4, 3), at(3, 2), at(3, 4));

        assertEquals(expected, reachedFromCenter(Direction.ORTHOGONAL));
    }

    @Test
    public void diagonal_fromCenter_reachesTheFourDiagonalNeighbours() {
        Set<Position> expected = Set.of(at(2, 2), at(2, 4), at(4, 2), at(4, 4));

        assertEquals(expected, reachedFromCenter(Direction.DIAGONAL));
    }

    @Test
    public void all_fromCenter_reachesAllEightNeighbours() {
        Set<Position> expected = Set.of(at(2, 2), at(2, 3), at(2, 4), at(3, 2),
                at(3, 4), at(4, 2), at(4, 3), at(4, 4));

        assertEquals(expected, reachedFromCenter(Direction.ALL));
        assertEquals(8, Direction.ALL.size());
    }

    private static Set<Position> reachedFromCenter(List<Direction> directions) {
        Position center = at(3, 3);
        return directions.stream()
                .map(center::translate)
                .flatMap(Optional::stream)
                .collect(Collectors.toSet());
    }

    private static Position at(int row, int col) {
        return new Position(row, col);
    }
}
