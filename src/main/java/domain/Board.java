package domain;

import domain.piece.*;

import java.util.Objects;
import java.util.Optional;

public class Board {
    private static final int BLACK_BACK_RANK_ROW = 0;
    private static final PieceType[] BACK_RANK = {
        PieceType.ROOK, PieceType.KNIGHT, PieceType.BISHOP, PieceType.QUEEN,
        PieceType.KING, PieceType.BISHOP, PieceType.KNIGHT, PieceType.ROOK
    };

    private final Piece[][] grid = new Piece[Position.BOARD_SIZE][Position.BOARD_SIZE];

    public static Board createStandard() {
        Board board = new Board();
        for (int col = 0; col < Position.BOARD_SIZE; col++) {
            board.placePiece(new Position(BLACK_BACK_RANK_ROW, col), new Piece(BACK_RANK[col], PieceColor.BLACK));
        }
        return board;
    }

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
