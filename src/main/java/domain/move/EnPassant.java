package domain.move;

import domain.Board;
import domain.Direction;
import domain.Move;
import domain.Position;
import domain.piece.Piece;
import domain.piece.PieceType;

import java.util.Optional;

public final class EnPassant {
    private static final int DOUBLE_STEP = 2;

    private EnPassant() {
    }

    public static Optional<Position> destination(Board board, Position from, Move lastMove) {
        return board.getPiece(from)
                .filter(pawn -> isPawnDoubleStep(board, lastMove))
                .filter(pawn -> isBeside(from, lastMove.getTo()))
                .flatMap(pawn -> from.translate(diagonalToward(lastMove.getTo(), from, pawn)));
    }

    private static boolean isPawnDoubleStep(Board board, Move move) {
        return isDoubleStep(move) && board.getPiece(move.getTo()).filter(EnPassant::isPawn).isPresent();
    }

    private static boolean isPawn(Piece piece) {
        return piece.getType() == PieceType.PAWN;
    }

    private static boolean isDoubleStep(Move move) {
        return Math.abs(move.getFrom().getRow() - move.getTo().getRow()) == DOUBLE_STEP;
    }

    private static boolean isBeside(Position from, Position other) {
        return from.getRow() == other.getRow() && Math.abs(from.getCol() - other.getCol()) == 1;
    }

    private static Direction diagonalToward(Position enemyPawn, Position from, Piece pawn) {
        return new Direction(PawnRule.forwardStep(pawn.getColor()), enemyPawn.getCol() - from.getCol());
    }
}
