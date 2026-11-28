package domain;

import domain.piece.PieceColor;

import java.util.Objects;

public class Turn {
    private final Board board;
    private final PieceSelection selection;

    public Turn(Board board, PieceSelection selection) {
        this.board = Objects.requireNonNull(board, "board must not be null");
        this.selection = Objects.requireNonNull(selection, "selection must not be null");
    }

    public PieceColor getCurrentColor() {
        return PieceColor.WHITE;
    }

    public void click(Position position) {
        if (selection.getHighlights().contains(position)) {
            board.movePiece(selection.getSelected().orElseThrow(), position);
            selection.clear();
        } else {
            selection.select(position, getCurrentColor());
        }
    }
}
