package domain.move;

import domain.Board;
import domain.Direction;
import domain.Position;
import domain.piece.PieceColor;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

final class JumpRule implements MoveRule {
    private static final List<Direction> KNIGHT_JUMPS = List.of(
            new Direction(-2, -1), new Direction(-2, 1), new Direction(-1, -2), new Direction(-1, 2),
            new Direction(1, -2), new Direction(1, 2), new Direction(2, -1), new Direction(2, 1));

    private final List<Direction> jumps;

    private JumpRule(List<Direction> jumps) {
        this.jumps = jumps;
    }

    static JumpRule knight() {
        return new JumpRule(KNIGHT_JUMPS);
    }

    static JumpRule king() {
        return new JumpRule(Direction.ALL);
    }

    @Override
    public Set<Position> destinations(Board board, Position from, PieceColor color) {
        return jumps.stream()
                .map(from::translate)
                .flatMap(Optional::stream)
                .filter(to -> Squares.isEmptyOrEnemy(board, to, color))
                .collect(Collectors.toUnmodifiableSet());
    }
}
