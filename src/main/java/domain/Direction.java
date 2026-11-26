package domain;

public final class Direction {
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
