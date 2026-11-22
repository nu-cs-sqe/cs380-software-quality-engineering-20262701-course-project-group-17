package domain;

import domain.piece.Piece;

import java.util.Optional;

public class Board {

    public boolean isEmpty(Position position) {
        return true;
    }

    public Optional<Piece> getPiece(Position position) {
        return Optional.empty();
    }

    public void placePiece(Position position, Piece piece) {
        throw new UnsupportedOperationException("not implemented yet");
    }
}
