package domain;

import domain.piece.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.*;

public class PieceSelectionTest {

    private Board board;
    private LegalMoves legalMoves;

    @BeforeEach
    public void setUp() {
        board = Board.createStandard();
        legalMoves = createMock(LegalMoves.class);
    }

    @Test
    public void constructor_newSelection_nothingSelected() {
        PieceSelection selection = newSelection();

        assertNothingSelected(selection);
        verify(legalMoves);
    }

    @Test
    public void constructor_nullBoard_throwsNullPointerException() {
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> new PieceSelection(null, legalMoves));

        assertEquals("board must not be null", exception.getMessage());
    }

    @Test
    public void constructor_nullLegalMoves_throwsNullPointerException() {
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> new PieceSelection(board, null));

        assertEquals("legalMoves must not be null", exception.getMessage());
    }

    private PieceSelection newSelection() {
        replay(legalMoves);
        return new PieceSelection(board, legalMoves);
    }

    private void assertNothingSelected(PieceSelection selection) {
        assertEquals(Optional.empty(), selection.getSelected());
        assertTrue(selection.getHighlights().isEmpty());
    }
}
