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

    static SlideRule queen() {
        return new SlideRule(Direction.ALL);
    }

    @Override
    public Set<Position> destinations(Board board, Position from, PieceColor color) {
        return directions.stream()
                .flatMap(direction -> slide(board, from, direction).stream())
                .filter(to -> Squares.isEmptyOrEnemy(board, to, color))
                .collect(Collectors.toUnmodifiableSet());
    }

    private static List<Position> slide(Board board, Position from, Direction direction) {
        List<Position> reached = new ArrayList<>();
        Optional<Position> next = from.translate(direction);
        while (next.isPresent()) {
            reached.add(next.get());
            next = next.filter(board::isEmpty).flatMap(square -> square.translate(direction));
        }
        return reached;
    }
}
