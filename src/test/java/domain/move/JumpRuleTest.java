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

public class JumpRuleTest {
    private static final Position CENTER = new Position(3, 3);

    private final Board board = new Board();

    @Test
    public void knight_center_hasEightMoves() {
        Set<Position> moves = knightMoves(CENTER, WHITE);

        assertEquals(squares(1, 2, 1, 4, 2, 1, 2, 5, 4, 1, 4, 5, 5, 2, 5, 4), moves);
    }

    @Test
    public void knight_corner_hasTwoMoves() {
        assertEquals(squares(1, 2, 2, 1), knightMoves(new Position(0, 0), WHITE));
        assertEquals(squares(5, 6, 6, 5), knightMoves(new Position(7, 7), WHITE));
    }

    @Test
    public void knight_nextToCorner_hasFourMoves() {
        Set<Position> moves = knightMoves(new Position(1, 1), WHITE);

        assertEquals(squares(0, 3, 2, 3, 3, 0, 3, 2), moves);
    }

    @Test
    public void knight_edge_hasFourMoves() {
        Set<Position> moves = knightMoves(new Position(0, 3), WHITE);

        assertEquals(squares(1, 1, 1, 5, 2, 2, 2, 4), moves);
    }

    @Test
    public void knight_ownPieceOnTarget_cannotLandThere() {
        placePawn(1, 2, WHITE);

        Set<Position> moves = knightMoves(CENTER, WHITE);

        assertEquals(squares(1, 4, 2, 1, 2, 5, 4, 1, 4, 5, 5, 2, 5, 4), moves);
    }

    @Test
    public void knight_enemyOnTarget_canCapture() {
        placePawn(1, 2, BLACK);

        Set<Position> moves = knightMoves(CENTER, WHITE);

        assertEquals(squares(1, 2, 1, 4, 2, 1, 2, 5, 4, 1, 4, 5, 5, 2, 5, 4), moves);
    }

    @Test
    public void knight_blackKnight_capturesWhiteAndAvoidsBlack() {
        placePawn(1, 2, WHITE);
        placePawn(5, 4, BLACK);

        Set<Position> moves = knightMoves(CENTER, BLACK);

        assertEquals(squares(1, 2, 1, 4, 2, 1, 2, 5, 4, 1, 4, 5, 5, 2), moves);
    }

    @Test
    public void knight_surroundedByOwnPieces_jumpsOver() {
        for (Position neighbour : squares(2, 2, 2, 3, 2, 4, 3, 2, 3, 4, 4, 2, 4, 3, 4, 4)) {
            placePawn(neighbour.getRow(), neighbour.getCol(), WHITE);
        }

        Set<Position> moves = knightMoves(CENTER, WHITE);

        assertEquals(squares(1, 2, 1, 4, 2, 1, 2, 5, 4, 1, 4, 5, 5, 2, 5, 4), moves);
    }

    @Test
    public void king_center_hasEightMoves() {
        Set<Position> moves = kingMoves(CENTER, WHITE);

        assertEquals(squares(2, 2, 2, 3, 2, 4, 3, 2, 3, 4, 4, 2, 4, 3, 4, 4), moves);
    }

    @Test
    public void king_corner_hasThreeMoves() {
        assertEquals(squares(0, 1, 1, 0, 1, 1), kingMoves(new Position(0, 0), WHITE));
        assertEquals(squares(6, 6, 6, 7, 7, 6), kingMoves(new Position(7, 7), WHITE));
    }

    @Test
    public void king_edge_hasFiveMoves() {
        Set<Position> moves = kingMoves(new Position(0, 3), WHITE);

        assertEquals(squares(0, 2, 0, 4, 1, 2, 1, 3, 1, 4), moves);
    }

    @ParameterizedTest
    @EnumSource(PieceColor.class)
    public void king_ownAndEnemyNeighbours_capturesOnlyTheEnemy(PieceColor color) {
        placePawn(2, 3, color);
        placePawn(4, 4, color == WHITE ? BLACK : WHITE);

        Set<Position> moves = kingMoves(CENTER, color);

        assertEquals(squares(2, 2, 2, 4, 3, 2, 3, 4, 4, 2, 4, 3, 4, 4), moves);
    }

    private Set<Position> knightMoves(Position from, PieceColor color) {
        return JumpRule.knight().destinations(board, from, color);
    }

    private Set<Position> kingMoves(Position from, PieceColor color) {
        return JumpRule.king().destinations(board, from, color);
    }

    private void placePawn(int row, int col, PieceColor color) {
        board.placePiece(new Position(row, col), new Piece(PieceType.PAWN, color));
    }
}
