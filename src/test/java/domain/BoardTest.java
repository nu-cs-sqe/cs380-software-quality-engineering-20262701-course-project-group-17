package domain;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    public void constructor_newBoard_allSquaresAreEmpty() {
        Board board = new Board();

        for (int row = 0; row <= 7; row++) {
            for (int col = 0; col <= 7; col++) {
                assertTrue(board.isEmpty(new Position(row, col)));
            }
        }
    }

    @Test
    public void constructor_newBoard_getPieceReturnsEmptyForAllSquares() {
        Board board = new Board();

        for (int row = 0; row <= 7; row++) {
            for (int col = 0; col <= 7; col++) {
                assertEquals(Optional.empty(), board.getPiece(new Position(row, col)));
            }
        }
    }
}
