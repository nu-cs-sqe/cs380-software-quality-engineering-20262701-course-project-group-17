package domain.piece;

import java.util.Objects;

public final class Piece {
    private static final int COLOR_COUNT = PieceColor.values().length;

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

    public boolean isSameColorAs(Piece other) {
        Objects.requireNonNull(other, "other must not be null");
        return color == other.color;
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Piece)) {
            return false;
        }
        Piece that = (Piece) other;
        return type == that.type && color == that.color;
    }

    @Override
    public int hashCode() {
        return type.ordinal() * COLOR_COUNT + color.ordinal();
    }
}
