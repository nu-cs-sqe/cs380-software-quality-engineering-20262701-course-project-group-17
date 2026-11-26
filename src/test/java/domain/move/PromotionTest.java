package domain.move;

import org.junit.jupiter.api.Test;

import java.util.List;

import static domain.piece.PieceType.*;
import static org.junit.jupiter.api.Assertions.*;

public class PromotionTest {

    @Test
    public void choices_inDialogOrder_areQueenRookBishopKnight() {
        assertEquals(List.of(QUEEN, ROOK, BISHOP, KNIGHT), Promotion.CHOICES);
    }
}
