package domain;

import domain.piece.Piece;

import java.util.Optional;

public class Board {

    public boolean isEmpty(Position position) {
        return true;
    }

    public Optional<Piece> getPiece(Position position) {
        throw new UnsupportedOperationException("not implemented yet");
    }
}
