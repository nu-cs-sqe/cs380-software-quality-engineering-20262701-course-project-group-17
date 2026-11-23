package domain;

public final class Position {
    public static final int BOARD_SIZE = 8;
    private static final int FIRST_INDEX = 0;
    private static final int LAST_INDEX = BOARD_SIZE - 1;

    private final int row;
    private final int col;

    public Position(int row, int col) {
        this.row = requireOnBoard(row, "row");
        this.col = col;
    }

    private static int requireOnBoard(int index, String axis) {
        if (index < FIRST_INDEX || index > LAST_INDEX) {
            throw new IllegalArgumentException(String.format(
                    "%s must be between %d and %d, but was %d", axis, FIRST_INDEX, LAST_INDEX, index));
        }
        return index;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
