package domain;

import java.util.Optional;
import java.util.Set;

public class PieceSelection {
    private Optional<Position> selected = Optional.empty();
    private Set<Position> highlights = Set.of();

    public PieceSelection(Board board, LegalMoves legalMoves) {
    }

    public Optional<Position> getSelected() {
        return selected;
    }

    public Set<Position> getHighlights() {
        return highlights;
    }
}
