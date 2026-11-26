package domain.move;

import domain.piece.PieceType;

import java.util.List;

public final class Promotion {
    public static final List<PieceType> CHOICES = List.of(
            PieceType.QUEEN, PieceType.ROOK, PieceType.BISHOP, PieceType.KNIGHT);

    private Promotion() {
    }
}
