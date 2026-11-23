package domain;

// Placeholder with the signatures agreed in docs/design. Kris owns Position;
// this file is replaced by his version when feature/position merges.
public final class Position {
    public static final int BOARD_SIZE = 8;

    private final int row;
    private final int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
