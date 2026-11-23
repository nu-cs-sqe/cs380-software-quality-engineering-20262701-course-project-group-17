package domain.piece;

import java.util.Objects;

public final class Piece {
    private final PieceType type;
    private final PieceColor color;

    public Piece(PieceType type, PieceColor color) {
        this.type = Objects.requireNonNull(type, "type must not be null");
        this.color = Objects.requireNonNull(color, "color must not be null");
    }

    public PieceType getType() {
        return type;
    }

    public PieceColor getColor() {
        return color;
    }
}
