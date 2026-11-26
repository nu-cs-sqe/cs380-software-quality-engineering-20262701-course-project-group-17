package domain;

import java.util.Objects;

public final class Move {
    private final Position from;
    private final Position to;

    public Move(Position from, Position to) {
        this.from = Objects.requireNonNull(from, "from must not be null");
        this.to = Objects.requireNonNull(to, "to must not be null");
        if (from.equals(to)) {
            throw new IllegalArgumentException("a move must change square");
        }
    }

    public Position getFrom() {
        return from;
    }

    public Position getTo() {
        return to;
    }
}
