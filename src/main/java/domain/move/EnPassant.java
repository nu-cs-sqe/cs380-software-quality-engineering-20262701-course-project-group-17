package domain.move;

import domain.Board;
import domain.Direction;
import domain.Move;
import domain.Position;
import domain.piece.Piece;

import java.util.Optional;

public final class EnPassant {
    private EnPassant() {
    }

    public static Optional<Position> destination(Board board, Position from, Move lastMove) {
        return board.getPiece(from)
                .flatMap(pawn -> from.translate(diagonalToward(lastMove.getTo(), from, pawn)));
    }

    private static Direction diagonalToward(Position enemyPawn, Position from, Piece pawn) {
        return new Direction(PawnRule.forwardStep(pawn.getColor()), enemyPawn.getCol() - from.getCol());
    }
}
