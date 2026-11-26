package domain.move;

import domain.Board;
import domain.Direction;
import domain.Position;
import domain.piece.PieceColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

final class SlideRule implements MoveRule {
    private final List<Direction> directions;

    private SlideRule(List<Direction> directions) {
        this.directions = directions;
    }

    static SlideRule rook() {
        return new SlideRule(Direction.ORTHOGONAL);
    }

    static SlideRule bishop() {
        return new SlideRule(Direction.DIAGONAL);
    }

    @Override
    public Set<Position> destinations(Board board, Position from, PieceColor color) {
        return directions.stream()
                .flatMap(direction -> slide(from, direction).stream())
                .collect(Collectors.toUnmodifiableSet());
    }

    private static List<Position> slide(Position from, Direction direction) {
        List<Position> reached = new ArrayList<>();
        Optional<Position> next = from.translate(direction);
        while (next.isPresent()) {
            reached.add(next.get());
            next = next.get().translate(direction);
        }
        return reached;
    }
}
