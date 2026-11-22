package domain;

import domain.piece.Piece;

import java.util.Objects;
import java.util.Optional;

public class Board {
    private final Piece[][] grid = new Piece[Position.BOARD_SIZE][Position.BOARD_SIZE];

    public boolean isEmpty(Position position) {
        return getPiece(position).isEmpty();
    }

    public Optional<Piece> getPiece(Position position) {
        Objects.requireNonNull(position, "position must not be null");
        return Optional.ofNullable(grid[position.getRow()][position.getCol()]);
    }

    public void placePiece(Position position, Piece piece) {
        Objects.requireNonNull(position, "position must not be null");
        Objects.requireNonNull(piece, "piece must not be null");
        grid[position.getRow()][position.getCol()] = piece;
    }
}
