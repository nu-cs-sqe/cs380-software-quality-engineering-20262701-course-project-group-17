package domain;

import domain.piece.*;

import java.util.Objects;
import java.util.Optional;

public class Board {
    private static final int BLACK_BACK_RANK_ROW = 0;
    private static final int BLACK_PAWN_ROW = 1;
    private static final int WHITE_PAWN_ROW = 6;
    private static final int WHITE_BACK_RANK_ROW = 7;
    private static final PieceType[] BACK_RANK = {
        PieceType.ROOK, PieceType.KNIGHT, PieceType.BISHOP, PieceType.QUEEN,
        PieceType.KING, PieceType.BISHOP, PieceType.KNIGHT, PieceType.ROOK
    };

    private final Piece[][] grid = new Piece[Position.BOARD_SIZE][Position.BOARD_SIZE];

    public static Board createStandard() {
        Board board = new Board();
        board.fillBackRank(BLACK_BACK_RANK_ROW, PieceColor.BLACK);
        board.fillPawnRow(BLACK_PAWN_ROW, PieceColor.BLACK);
        board.fillPawnRow(WHITE_PAWN_ROW, PieceColor.WHITE);
        board.fillBackRank(WHITE_BACK_RANK_ROW, PieceColor.WHITE);
        return board;
    }

    private void fillBackRank(int row, PieceColor color) {
        for (int col = 0; col < Position.BOARD_SIZE; col++) {
            placePiece(new Position(row, col), new Piece(BACK_RANK[col], color));
        }
    }

    private void fillPawnRow(int row, PieceColor color) {
        for (int col = 0; col < Position.BOARD_SIZE; col++) {
            placePiece(new Position(row, col), new Piece(PieceType.PAWN, color));
        }
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

    public void movePiece(Position from, Position to) {
        placePiece(to, getPiece(from).orElseThrow());
        grid[from.getRow()][from.getCol()] = null;
    }
}
