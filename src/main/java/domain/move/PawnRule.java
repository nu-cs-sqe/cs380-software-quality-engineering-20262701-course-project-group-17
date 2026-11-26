package domain.move;

import domain.Board;
import domain.Direction;
import domain.Position;
import domain.piece.PieceColor;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class PawnRule implements MoveRule {
    private static final int WHITE_START_ROW = 6;
    private static final int BLACK_START_ROW = 1;

    @Override
    public Set<Position> destinations(Board board, Position from, PieceColor color) {
        Direction forward = new Direction(forwardStep(color), 0);
        Optional<Position> oneStep = from.translate(forward);
        Optional<Position> twoSteps = oneStep
                .filter(square -> isOnStartRow(from, color))
                .flatMap(square -> square.translate(forward));
        return Stream.of(oneStep, twoSteps)
                .flatMap(Optional::stream)
                .collect(Collectors.toUnmodifiableSet());
    }

    static int forwardStep(PieceColor color) {
        return color == PieceColor.WHITE ? -1 : 1;
    }

    private static boolean isOnStartRow(Position position, PieceColor color) {
        int startRow = color == PieceColor.WHITE ? WHITE_START_ROW : BLACK_START_ROW;
        return position.getRow() == startRow;
    }
}
