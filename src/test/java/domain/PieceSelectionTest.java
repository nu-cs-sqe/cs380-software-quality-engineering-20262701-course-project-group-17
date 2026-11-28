package domain;

import domain.piece.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.*;

public class PieceSelectionTest {
    private static final Position WHITE_PAWN = new Position(6, 4);
    private static final Set<Position> WHITE_PAWN_MOVES = Set.of(new Position(5, 4), new Position(4, 4));
    private static final Position WHITE_KNIGHT = new Position(7, 6);
    private static final Set<Position> WHITE_KNIGHT_MOVES = Set.of(new Position(5, 5), new Position(5, 7));
    private static final Position BLACK_PAWN = new Position(1, 4);

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

    @Test
    public void select_opponentPiece_doesNothing() {
        PieceSelection selection = newSelection();

        selection.select(BLACK_PAWN, PieceColor.WHITE);

        assertNothingSelected(selection);
        verify(legalMoves);
    }

    @Test
    public void select_whitePieceOnBlackTurn_doesNothing() {
        PieceSelection selection = newSelection();

        selection.select(WHITE_PAWN, PieceColor.BLACK);

        assertNothingSelected(selection);
        verify(legalMoves);
    }

    @Test
    public void select_separateEqualPosition_selectsPiece() {
        expectMoves(new Position(6, 4), WHITE_PAWN_MOVES);
        PieceSelection selection = newSelection();

        selection.select(new Position(6, 4), PieceColor.WHITE);

        assertEquals(Optional.of(new Position(6, 4)), selection.getSelected());
        verify(legalMoves);
    }

    @Test
    public void select_selectedPieceAgain_deselects() {
        expectMoves(WHITE_PAWN, WHITE_PAWN_MOVES);
        PieceSelection selection = newSelection();
        selection.select(WHITE_PAWN, PieceColor.WHITE);

        selection.select(WHITE_PAWN, PieceColor.WHITE);

        assertNothingSelected(selection);
        verify(legalMoves);
    }

    @Test
    public void select_otherOwnPiece_switchesSelection() {
        expectMoves(WHITE_PAWN, WHITE_PAWN_MOVES);
        expectMoves(WHITE_KNIGHT, WHITE_KNIGHT_MOVES);
        PieceSelection selection = newSelection();
        selection.select(WHITE_PAWN, PieceColor.WHITE);

        selection.select(WHITE_KNIGHT, PieceColor.WHITE);

        assertSelected(WHITE_KNIGHT, WHITE_KNIGHT_MOVES, selection);
        verify(legalMoves);
    }

    @Test
    public void select_emptySquareWhileSelected_keepsSelection() {
        expectMoves(WHITE_PAWN, WHITE_PAWN_MOVES);
        PieceSelection selection = newSelection();
        selection.select(WHITE_PAWN, PieceColor.WHITE);

        selection.select(new Position(3, 3), PieceColor.WHITE);

        assertSelected(WHITE_PAWN, WHITE_PAWN_MOVES, selection);
        verify(legalMoves);
    }

    @Test
    public void select_opponentPieceWhileSelected_keepsSelection() {
        expectMoves(WHITE_PAWN, WHITE_PAWN_MOVES);
        PieceSelection selection = newSelection();
        selection.select(WHITE_PAWN, PieceColor.WHITE);

        selection.select(BLACK_PAWN, PieceColor.WHITE);

        assertSelected(WHITE_PAWN, WHITE_PAWN_MOVES, selection);
        verify(legalMoves);
    }

    @Test
    public void select_afterDeselect_selectsAgain() {
        expectMoves(WHITE_PAWN, WHITE_PAWN_MOVES);
        expectMoves(WHITE_PAWN, WHITE_PAWN_MOVES);
        PieceSelection selection = newSelection();
        selection.select(WHITE_PAWN, PieceColor.WHITE);
        selection.select(WHITE_PAWN, PieceColor.WHITE);

        selection.select(WHITE_PAWN, PieceColor.WHITE);

        assertSelected(WHITE_PAWN, WHITE_PAWN_MOVES, selection);
        verify(legalMoves);
    }

    @Test
    public void select_switchedPieceAgain_deselects() {
        expectMoves(WHITE_PAWN, WHITE_PAWN_MOVES);
        expectMoves(WHITE_KNIGHT, WHITE_KNIGHT_MOVES);
        PieceSelection selection = newSelection();
        selection.select(WHITE_PAWN, PieceColor.WHITE);
        selection.select(WHITE_KNIGHT, PieceColor.WHITE);

        selection.select(WHITE_KNIGHT, PieceColor.WHITE);

        assertNothingSelected(selection);
        verify(legalMoves);
    }

    @Test
    public void select_anySequence_boardIsUnchanged() {
        expectMoves(WHITE_PAWN, WHITE_PAWN_MOVES);
        expectMoves(WHITE_KNIGHT, WHITE_KNIGHT_MOVES);
        PieceSelection selection = newSelection();

        selection.select(WHITE_PAWN, PieceColor.WHITE);
        selection.select(WHITE_KNIGHT, PieceColor.WHITE);
        selection.select(WHITE_KNIGHT, PieceColor.WHITE);

        Board standard = Board.createStandard();
        for (int row = 0; row < Position.BOARD_SIZE; row++) {
            for (int col = 0; col < Position.BOARD_SIZE; col++) {
                Position position = new Position(row, col);
                assertEquals(standard.getPiece(position), board.getPiece(position));
            }
        }
    }

    @Test
    public void select_nullPosition_throwsNullPointerException() {
        PieceSelection selection = newSelection();

        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> selection.select(null, PieceColor.WHITE));

        assertEquals("position must not be null", exception.getMessage());
    }

    @Test
    public void select_nullTurn_throwsNullPointerException() {
        PieceSelection selection = newSelection();

        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> selection.select(WHITE_PAWN, null));

        assertEquals("turn must not be null", exception.getMessage());
    }

    @Test
    public void getHighlights_modifyReturnedSet_throwsUnsupportedOperationException() {
        expectMoves(WHITE_PAWN, new HashSet<>(WHITE_PAWN_MOVES));
        PieceSelection selection = newSelection();
        selection.select(WHITE_PAWN, PieceColor.WHITE);

        Set<Position> highlights = selection.getHighlights();

        assertThrows(UnsupportedOperationException.class, () -> highlights.add(new Position(3, 3)));
    }

    @Test
    public void getHighlights_mockSetChangedLater_staysUnchanged() {
        Set<Position> moves = new HashSet<>(WHITE_PAWN_MOVES);
        expectMoves(WHITE_PAWN, moves);
        PieceSelection selection = newSelection();
        selection.select(WHITE_PAWN, PieceColor.WHITE);

        moves.add(new Position(3, 3));

        assertEquals(WHITE_PAWN_MOVES, selection.getHighlights());
    }

    @Test
    public void clear_pieceSelected_clearsSelectionAndHighlights() {
        expectMoves(WHITE_PAWN, WHITE_PAWN_MOVES);
        PieceSelection selection = newSelection();
        selection.select(WHITE_PAWN, PieceColor.WHITE);

        selection.clear();

        assertNothingSelected(selection);
    }

    @Test
    public void clear_nothingSelected_staysEmpty() {
        PieceSelection selection = newSelection();

        selection.clear();

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
