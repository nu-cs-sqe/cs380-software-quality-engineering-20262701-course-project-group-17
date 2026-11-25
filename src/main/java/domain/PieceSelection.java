package domain;

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

    public Optional<Position> getSelected() {
        return selected;
    }

    public Set<Position> getHighlights() {
        return highlights;
    }
}
