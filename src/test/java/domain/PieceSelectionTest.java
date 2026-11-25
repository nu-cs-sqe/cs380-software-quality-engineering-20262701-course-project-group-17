package domain;

import domain.piece.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.*;

public class PieceSelectionTest {
    private static final Position WHITE_PAWN = new Position(6, 4);
    private static final Set<Position> WHITE_PAWN_MOVES = Set.of(new Position(5, 4), new Position(4, 4));

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

    @Test
    public void select_ownPiece_selectsPieceAndHighlightsMoves() {
        expectMoves(WHITE_PAWN, WHITE_PAWN_MOVES);
        PieceSelection selection = newSelection();

        selection.select(WHITE_PAWN, PieceColor.WHITE);

        assertSelected(WHITE_PAWN, WHITE_PAWN_MOVES, selection);
        verify(legalMoves);
    }

    @Test
    public void select_blackPieceOnBlackTurn_selectsPiece() {
        Position blackKnight = new Position(0, 6);
        Set<Position> blackKnightMoves = Set.of(new Position(2, 5), new Position(2, 7));
        expectMoves(blackKnight, blackKnightMoves);
        PieceSelection selection = newSelection();

        selection.select(blackKnight, PieceColor.BLACK);

        assertSelected(blackKnight, blackKnightMoves, selection);
        verify(legalMoves);
    }

    @Test
    public void select_pieceWithNoMoves_selectsWithNoHighlights() {
        Position whiteRook = new Position(7, 0);
        expectMoves(whiteRook, Set.of());
        PieceSelection selection = newSelection();

        selection.select(whiteRook, PieceColor.WHITE);

        assertSelected(whiteRook, Set.of(), selection);
        verify(legalMoves);
    }

    @Test
    public void select_pieceWithOneMove_highlightsOneSquare() {
        board = new Board();
        board.placePiece(WHITE_PAWN, new Piece(PieceType.PAWN, PieceColor.WHITE));
        board.placePiece(new Position(4, 4), new Piece(PieceType.PAWN, PieceColor.BLACK));
        expectMoves(WHITE_PAWN, Set.of(new Position(5, 4)));
        PieceSelection selection = newSelection();

        selection.select(WHITE_PAWN, PieceColor.WHITE);

        assertSelected(WHITE_PAWN, Set.of(new Position(5, 4)), selection);
        verify(legalMoves);
    }

    @Test
    public void select_emptySquare_doesNothing() {
        PieceSelection selection = newSelection();

        selection.select(new Position(4, 4), PieceColor.WHITE);

        assertNothingSelected(selection);
        verify(legalMoves);
    }

    private void expectMoves(Position position, Set<Position> moves) {
        expect(legalMoves.from(board, position)).andReturn(moves);
    }

    private PieceSelection newSelection() {
        replay(legalMoves);
        return new PieceSelection(board, legalMoves);
    }

    private void assertSelected(Position expected, Set<Position> expectedHighlights, PieceSelection selection) {
        assertEquals(Optional.of(expected), selection.getSelected());
        assertEquals(expectedHighlights, selection.getHighlights());
    }

    private void assertNothingSelected(PieceSelection selection) {
        assertEquals(Optional.empty(), selection.getSelected());
        assertTrue(selection.getHighlights().isEmpty());
    }
}
