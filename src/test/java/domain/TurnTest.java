package domain;

import domain.piece.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.easymock.EasyMock.*;
import static org.junit.jupiter.api.Assertions.*;

public class TurnTest {
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

    private Turn newTurn() {
        replay(legalMoves);
        return new Turn(board, selection);
    }
}
