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
        Objects.requireNonNull(position, "position must not be null");
        Objects.requireNonNull(turn, "turn must not be null");
        if (isSelected(position)) {
            deselect();
        } else if (isOwnPiece(position, turn)) {
            selectPiece(position);
        }
    }

    private boolean isSelected(Position position) {
        return selected.equals(Optional.of(position));
    }

    private void deselect() {
        selected = Optional.empty();
        highlights = Set.of();
    }

    private boolean isOwnPiece(Position position, PieceColor turn) {
        return board.getPiece(position).filter(piece -> piece.getColor() == turn).isPresent();
    }

    private void selectPiece(Position position) {
        selected = Optional.of(position);
        highlights = Set.copyOf(legalMoves.from(board, position));
    }

    public void clear() {
        deselect();
    }

    public Optional<Position> getSelected() {
        return selected;
    }

    public Set<Position> getHighlights() {
        return highlights;
    }
}
