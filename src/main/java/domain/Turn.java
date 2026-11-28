package domain;

import domain.piece.PieceColor;

public class Turn {
    public Turn(Board board, PieceSelection selection) {
    }

    public PieceColor getCurrentColor() {
        return PieceColor.WHITE;
    }
}
