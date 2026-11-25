package domain;

import domain.piece.PieceColor;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class PieceSelection {
    private final Board board;
    private final LegalMoves legalMoves;
    private Optional<Position> selected = Optional.empty();
    private Set<Position> highlights = Set.of();

    public PieceSelection(Board board, LegalMoves legalMoves) {
        this.board = Objects.requireNonNull(board, "board must not be null");
        this.legalMoves = Objects.requireNonNull(legalMoves, "legalMoves must not be null");
    }

    public void select(Position position, PieceColor turn) {
        if (selected.equals(Optional.of(position))) {
            selected = Optional.empty();
            highlights = Set.of();
        } else if (isOwnPiece(position, turn)) {
            selected = Optional.of(position);
            highlights = legalMoves.from(board, position);
        }
    }

    private boolean isOwnPiece(Position position, PieceColor turn) {
        return board.getPiece(position).filter(piece -> piece.getColor() == turn).isPresent();
    }

    public Optional<Position> getSelected() {
        return selected;
    }

    public Set<Position> getHighlights() {
        return highlights;
    }
}
