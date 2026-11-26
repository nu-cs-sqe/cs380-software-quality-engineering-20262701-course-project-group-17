package domain.move;

import domain.Board;
import domain.Position;
import domain.piece.PieceColor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Set;

import static domain.move.PositionSets.squares;
import static org.junit.jupiter.api.Assertions.*;

public class StandardBoardMovesIntegrationTest {
    private final Board board = Board.createStandard();
    private final PieceMoves pieceMoves = new PieceMoves();

    @ParameterizedTest
    @EnumSource(PieceColor.class)
    public void startingPosition_eachSide_hasTwentyMoves(PieceColor side) {
        int total = 0;
        for (int row = 0; row < Position.BOARD_SIZE; row++) {
            for (int col = 0; col < Position.BOARD_SIZE; col++) {
                total += movesIfOwnPiece(new Position(row, col), side);
            }
        }

        assertEquals(20, total);
    }

    @Test
    public void startingPosition_whiteKnight_jumpsOverThePawns() {
        assertEquals(squares(5, 0, 5, 2), pieceMoves.from(board, new Position(7, 1)));
    }

    @Test
    public void startingPosition_backRankExceptKnights_hasNoMoves() {
        for (int col : new int[] {0, 2, 3, 4}) {
            assertEquals(Set.of(), pieceMoves.from(board, new Position(7, col)));
        }
    }

    private int movesIfOwnPiece(Position position, PieceColor side) {
        boolean ownPiece = board.getPiece(position).filter(piece -> piece.getColor() == side).isPresent();
        return ownPiece ? pieceMoves.from(board, position).size() : 0;
    }
}
