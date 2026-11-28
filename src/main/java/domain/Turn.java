package domain;

import domain.piece.PieceColor;

import java.util.Objects;

public class Turn {
    private final Board board;
    private final PieceSelection selection;
    private PieceColor currentColor = PieceColor.WHITE;

    public Turn(Board board, PieceSelection selection) {
        this.board = Objects.requireNonNull(board, "board must not be null");
        this.selection = Objects.requireNonNull(selection, "selection must not be null");
    }

    public void click(Position position) {
        Objects.requireNonNull(position, "position must not be null");
        if (isHighlighted(position)) {
            moveSelectedPieceTo(position);
            passTurn();
        } else {
            selection.select(position, currentColor);
        }
    }

    private boolean isHighlighted(Position position) {
        return selection.getHighlights().contains(position);
    }

    private void moveSelectedPieceTo(Position position) {
        board.movePiece(selection.getSelected().orElseThrow(), position);
        selection.clear();
    }

    private void passTurn() {
        currentColor = isWhiteTurn() ? PieceColor.BLACK : PieceColor.WHITE;
    }

    private boolean isWhiteTurn() {
        return currentColor == PieceColor.WHITE;
    }

    public PieceColor getCurrentColor() {
        return currentColor;
    }
}
