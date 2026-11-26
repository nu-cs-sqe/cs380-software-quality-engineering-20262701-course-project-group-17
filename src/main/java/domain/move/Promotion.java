package domain.move;

import domain.Board;
import domain.Position;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;

import java.util.List;
import java.util.Objects;

public final class Promotion {
    public static final List<PieceType> CHOICES = List.of(
            PieceType.QUEEN, PieceType.ROOK, PieceType.BISHOP, PieceType.KNIGHT);
    private static final int WHITE_LAST_ROW = 0;
    private static final int BLACK_LAST_ROW = Position.BOARD_SIZE - 1;

    private Promotion() {
    }

    public static boolean isDue(Board board, Position position) {
        Objects.requireNonNull(board, "board must not be null");
        Objects.requireNonNull(position, "position must not be null");
        return board.getPiece(position)
                .filter(piece -> piece.getType() == PieceType.PAWN)
                .filter(piece -> isOnLastRow(piece, position))
                .isPresent();
    }

    private static boolean isOnLastRow(Piece piece, Position position) {
        int lastRow = piece.getColor() == PieceColor.WHITE ? WHITE_LAST_ROW : BLACK_LAST_ROW;
        return position.getRow() == lastRow;
    }

    public static void promote(Board board, Position position, PieceType choice) {
        if (!CHOICES.contains(choice)) {
            throw new IllegalArgumentException("a pawn can only be promoted to a queen, rook, bishop, or knight");
        }
        if (!isDue(board, position)) {
            throw new IllegalArgumentException("position must hold a pawn on its last row");
        }
        PieceColor color = board.getPiece(position).orElseThrow().getColor();
        board.placePiece(position, new Piece(choice, color));
    }
}
