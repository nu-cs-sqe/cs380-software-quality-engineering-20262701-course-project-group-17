package domain.move;

import domain.Board;
import domain.Position;
import domain.piece.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Set;

import static domain.move.PositionSets.squares;
import static domain.piece.PieceColor.*;
import static org.junit.jupiter.api.Assertions.*;

public class PawnRuleTest {
    private final Board board = new Board();

    @Test
    public void pawn_afterStartRow_movesOneSquare() {
        assertEquals(squares(4, 3), pawnMoves(5, 3, WHITE));
        assertEquals(squares(3, 3), pawnMoves(2, 3, BLACK));
    }

    @Test
    public void pawn_onStartRow_canMoveOneOrTwoSquares() {
        assertEquals(squares(5, 3, 4, 3), pawnMoves(6, 3, WHITE));
        assertEquals(squares(2, 3, 3, 3), pawnMoves(1, 3, BLACK));
    }

    @Test
    public void pawn_onOtherSidesStartRow_movesOneSquare() {
        assertEquals(squares(0, 3), pawnMoves(1, 3, WHITE));
        assertEquals(squares(7, 3), pawnMoves(6, 3, BLACK));
    }

    @Test
    public void pawn_onLastRow_hasNoMoves() {
        assertEquals(Set.of(), pawnMoves(0, 3, WHITE));
        assertEquals(Set.of(), pawnMoves(7, 3, BLACK));
    }

    @ParameterizedTest
    @EnumSource(PieceColor.class)
    public void pawn_squareAheadTaken_cannotMoveForward(PieceColor blocker) {
        placePawn(5, 3, blocker);

        assertEquals(Set.of(), pawnMoves(6, 3, WHITE));
    }

    @ParameterizedTest
    @EnumSource(PieceColor.class)
    public void pawn_twoAheadTaken_movesOneSquare(PieceColor blocker) {
        placePawn(4, 3, blocker);

        assertEquals(squares(5, 3), pawnMoves(6, 3, WHITE));
    }

    @Test
    public void pawn_enemyStraightAhead_cannotCapture() {
        placePawn(3, 3, BLACK);

        assertEquals(Set.of(), pawnMoves(4, 3, WHITE));
    }

    @Test
    public void pawn_enemiesDiagonallyAhead_canCaptureBoth() {
        placePawn(3, 2, BLACK);
        placePawn(3, 4, BLACK);
        placePawn(4, 2, WHITE);
        placePawn(4, 4, WHITE);

        assertEquals(squares(3, 3, 3, 2, 3, 4), pawnMoves(4, 3, WHITE));
        assertEquals(squares(4, 3, 4, 2, 4, 4), pawnMoves(3, 3, BLACK));
    }

    @Test
    public void pawn_ownPiecesDiagonallyAhead_cannotCapture() {
        placePawn(3, 2, WHITE);
        placePawn(3, 4, WHITE);

        assertEquals(squares(3, 3), pawnMoves(4, 3, WHITE));
    }

    @Test
    public void pawn_onEdgeCol_capturesOnlyInward() {
        placePawn(3, 1, BLACK);
        placePawn(3, 6, BLACK);

        assertEquals(squares(3, 0, 3, 1), pawnMoves(4, 0, WHITE));
        assertEquals(squares(3, 7, 3, 6), pawnMoves(4, 7, WHITE));
    }

    @Test
    public void pawn_enemiesBehind_cannotCaptureBackward() {
        placePawn(5, 2, BLACK);
        placePawn(5, 4, BLACK);

        assertEquals(squares(3, 3), pawnMoves(4, 3, WHITE));
    }

    private Set<Position> pawnMoves(int row, int col, PieceColor color) {
        return new PawnRule().destinations(board, new Position(row, col), color);
    }

    private void placePawn(int row, int col, PieceColor color) {
        board.placePiece(new Position(row, col), new Piece(PieceType.PAWN, color));
    }
}
