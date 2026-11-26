package domain;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Direction {
    public static final List<Direction> ORTHOGONAL = List.of(
            new Direction(-1, 0), new Direction(1, 0), new Direction(0, -1), new Direction(0, 1));
    public static final List<Direction> DIAGONAL = List.of(
            new Direction(-1, -1), new Direction(-1, 1), new Direction(1, -1), new Direction(1, 1));
    public static final List<Direction> ALL = Stream.concat(ORTHOGONAL.stream(), DIAGONAL.stream())
            .collect(Collectors.toUnmodifiableList());

    private final int rowDelta;
    private final int colDelta;

    public Direction(int rowDelta, int colDelta) {
        if (isZeroStep(rowDelta, colDelta)) {
            throw new IllegalArgumentException("a direction must move at least one square");
        }
        this.rowDelta = rowDelta;
        this.colDelta = colDelta;
    }

    private static boolean isZeroStep(int rowDelta, int colDelta) {
        return rowDelta == 0 && colDelta == 0;
    }

    public int getRowDelta() {
        return rowDelta;
    }

    public int getColDelta() {
        return colDelta;
    }
}
