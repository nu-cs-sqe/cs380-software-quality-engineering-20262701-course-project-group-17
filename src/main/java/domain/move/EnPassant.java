package domain.move;

import domain.Board;
import domain.Direction;
import domain.Move;
import domain.Position;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;

import java.util.Objects;
import java.util.Optional;

public final class EnPassant {
    private static final int DOUBLE_STEP = 2;

    private EnPassant() {
    }

    public static Optional<Position> destination(Board board, Position from, Move lastMove) {
        Objects.requireNonNull(board, "board must not be null");
        Objects.requireNonNull(from, "from must not be null");
        Objects.requireNonNull(lastMove, "lastMove must not be null");
        return board.getPiece(from)
                .filter(EnPassant::isPawn)
                .filter(pawn -> isEnemyPawnDoubleStep(board, lastMove, pawn.getColor()))
                .filter(pawn -> isBeside(from, lastMove.getTo()))
                .flatMap(pawn -> from.translate(diagonalToward(lastMove.getTo(), from, pawn)));
    }

    public static Position capturedPawn(Move enPassantMove) {
        return new Position(enPassantMove.getFrom().getRow(), enPassantMove.getTo().getCol());
    }

    private static boolean isEnemyPawnDoubleStep(Board board, Move move, PieceColor color) {
        return isDoubleStep(move) && board.getPiece(move.getTo())
                .filter(piece -> isPawn(piece) && piece.getColor() != color)
                .isPresent();
    }

    private static boolean isPawn(Piece piece) {
        return piece.getType() == PieceType.PAWN;
    }

    private static boolean isDoubleStep(Move move) {
        return Math.abs(move.getFrom().getRow() - move.getTo().getRow()) == DOUBLE_STEP
                && move.getFrom().getCol() == move.getTo().getCol();
    }

    private static boolean isBeside(Position from, Position other) {
        return from.getRow() == other.getRow() && Math.abs(from.getCol() - other.getCol()) == 1;
    }

    private static Direction diagonalToward(Position enemyPawn, Position from, Piece pawn) {
        return new Direction(PawnRule.forwardStep(pawn.getColor()), enemyPawn.getCol() - from.getCol());
    }
}
