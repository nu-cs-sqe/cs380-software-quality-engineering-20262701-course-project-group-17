package domain.piece;

// Placeholder with the signatures agreed in docs/design. Kris owns Piece;
// this file is replaced by his version when feature/piece merges.
public final class Piece {
    private final PieceType type;
    private final PieceColor color;

    public Piece(PieceType type, PieceColor color) {
        this.type = type;
        this.color = color;
    }

    public PieceType getType() {
        return type;
    }

    public PieceColor getColor() {
        return color;
    }
}
