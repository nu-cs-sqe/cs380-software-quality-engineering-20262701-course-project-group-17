package domain;

import java.util.Set;

public interface LegalMoves {
    Set<Position> from(Board board, Position position);
}
