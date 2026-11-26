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

    private Set<Position> pawnMoves(int row, int col, PieceColor color) {
        return new PawnRule().destinations(board, new Position(row, col), color);
    }

    private void placePawn(int row, int col, PieceColor color) {
        board.placePiece(new Position(row, col), new Piece(PieceType.PAWN, color));
    }
}
