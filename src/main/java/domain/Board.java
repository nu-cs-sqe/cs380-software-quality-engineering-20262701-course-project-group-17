package domain;

import domain.piece.Piece;

import java.util.Optional;

public class Board {
    private Piece piece;

    public boolean isEmpty(Position position) {
        return getPiece(position).isEmpty();
    }

    public Optional<Piece> getPiece(Position position) {
        return Optional.ofNullable(piece);
    }

    public void placePiece(Position position, Piece piece) {
        this.piece = piece;
    }
}
