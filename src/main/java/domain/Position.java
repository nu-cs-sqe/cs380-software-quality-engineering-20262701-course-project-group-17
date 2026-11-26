package domain;

import java.util.Optional;

public final class Position {
    public static final int BOARD_SIZE = 8;
    private static final int FIRST_INDEX = 0;
    private static final int LAST_INDEX = BOARD_SIZE - 1;

    private final int row;
    private final int col;

    public Position(int row, int col) {
        this.row = requireOnBoard(row, "row");
        this.col = requireOnBoard(col, "col");
    }

    private static int requireOnBoard(int index, String axis) {
        if (isOffBoard(index)) {
            throw new IllegalArgumentException(String.format(
                    "%s must be between %d and %d, but was %d", axis, FIRST_INDEX, LAST_INDEX, index));
        }
        return index;
    }

    private static boolean isOffBoard(int index) {
        return index < FIRST_INDEX || index > LAST_INDEX;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Optional<Position> translate(Direction direction) {
        int newRow = row + direction.getRowDelta();
        int newCol = col + direction.getColDelta();
        if (isOffBoard(newRow) || isOffBoard(newCol)) {
            return Optional.empty();
        }
        return Optional.of(new Position(newRow, newCol));
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Position)) {
            return false;
        }
        Position that = (Position) other;
        return row == that.row && col == that.col;
    }

    @Override
    public int hashCode() {
        return row * BOARD_SIZE + col;
    }
}
