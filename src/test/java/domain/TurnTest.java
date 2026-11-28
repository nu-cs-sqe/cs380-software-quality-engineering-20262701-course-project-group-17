package domain;

import domain.piece.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.*;

public class TurnTest {
    private static final Position WHITE_PAWN = new Position(6, 4);
    private static final Position WHITE_PAWN_TARGET = new Position(4, 4);
    private static final Set<Position> WHITE_PAWN_MOVES = Set.of(new Position(5, 4), WHITE_PAWN_TARGET);
    private static final Position BLACK_PAWN = new Position(1, 4);
    private static final Position BLACK_PAWN_TARGET = new Position(3, 4);

    private Board board;
    private LegalMoves legalMoves;
    private PieceSelection selection;

    @BeforeEach
    public void setUp() {
        board = Board.createStandard();
        legalMoves = createMock(LegalMoves.class);
        selection = new PieceSelection(board, legalMoves);
    }

    @Test
    public void constructor_newTurn_whiteMovesFirst() {
        Turn turn = newTurn();

        assertEquals(PieceColor.WHITE, turn.getCurrentColor());
    }

    @Test
    public void constructor_nullBoard_throwsNullPointerException() {
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> new Turn(null, selection));

        assertEquals("board must not be null", exception.getMessage());
    }

    @Test
    public void constructor_nullSelection_throwsNullPointerException() {
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> new Turn(board, null));

        assertEquals("selection must not be null", exception.getMessage());
    }

    @Test
    public void click_ownPiece_selectsWithoutSwitchingTurn() {
        expectMoves(WHITE_PAWN, WHITE_PAWN_MOVES);
        Turn turn = newTurn();

        turn.click(WHITE_PAWN);

        assertEquals(Optional.of(WHITE_PAWN), selection.getSelected());
        assertEquals(PieceColor.WHITE, turn.getCurrentColor());
    }

    @Test
    public void click_highlightedSquare_movesPiece() {
        expectMoves(WHITE_PAWN, WHITE_PAWN_MOVES);
        Turn turn = newTurn();
        turn.click(WHITE_PAWN);

        turn.click(WHITE_PAWN_TARGET);

        assertEquals(Optional.of(new Piece(PieceType.PAWN, PieceColor.WHITE)), board.getPiece(WHITE_PAWN_TARGET));
        assertTrue(board.isEmpty(WHITE_PAWN));
    }

    @Test
    public void click_highlightedSquare_clearsSelection() {
        expectMoves(WHITE_PAWN, WHITE_PAWN_MOVES);
        Turn turn = newTurn();
        turn.click(WHITE_PAWN);

        turn.click(WHITE_PAWN_TARGET);

        assertEquals(Optional.empty(), selection.getSelected());
        assertTrue(selection.getHighlights().isEmpty());
    }

    @Test
    public void click_highlightedSquare_switchesTurnToBlack() {
        expectMoves(WHITE_PAWN, WHITE_PAWN_MOVES);
        Turn turn = newTurn();
        turn.click(WHITE_PAWN);

        turn.click(WHITE_PAWN_TARGET);

        assertEquals(PieceColor.BLACK, turn.getCurrentColor());
    }

    @Test
    public void click_blackMoves_switchesTurnBackToWhite() {
        expectMoves(WHITE_PAWN, WHITE_PAWN_MOVES);
        expectMoves(BLACK_PAWN, Set.of(BLACK_PAWN_TARGET));
        Turn turn = newTurn();
        playWhitePawnMove(turn);
        turn.click(BLACK_PAWN);

        turn.click(BLACK_PAWN_TARGET);

        assertEquals(Optional.of(new Piece(PieceType.PAWN, PieceColor.BLACK)), board.getPiece(BLACK_PAWN_TARGET));
        assertEquals(PieceColor.WHITE, turn.getCurrentColor());
    }

    private void expectMoves(Position position, Set<Position> moves) {
        expect(legalMoves.from(board, position)).andStubReturn(moves);
    }

    private void playWhitePawnMove(Turn turn) {
        turn.click(WHITE_PAWN);
        turn.click(WHITE_PAWN_TARGET);
    }

    private Turn newTurn() {
        replay(legalMoves);
        return new Turn(board, selection);
    }
}
