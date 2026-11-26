package domain.move;

import domain.Board;
import domain.Direction;
import domain.Position;
import domain.piece.PieceColor;

import java.util.Set;
import java.util.stream.Collectors;

final class PawnRule implements MoveRule {

    @Override
    public Set<Position> destinations(Board board, Position from, PieceColor color) {
        Direction forward = new Direction(forwardStep(color), 0);
        return from.translate(forward).stream()
                .collect(Collectors.toUnmodifiableSet());
    }

    static int forwardStep(PieceColor color) {
        return color == PieceColor.WHITE ? -1 : 1;
    }
}
