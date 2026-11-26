package domain.move;

import domain.Board;
import domain.Position;
import domain.piece.Piece;
import domain.piece.PieceType;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

public final class PieceMoves {
    private final Map<PieceType, MoveRule> rules = new EnumMap<>(PieceType.class);

    public PieceMoves() {
        rules.put(PieceType.KING, JumpRule.king());
        rules.put(PieceType.QUEEN, SlideRule.queen());
        rules.put(PieceType.ROOK, SlideRule.rook());
        rules.put(PieceType.BISHOP, SlideRule.bishop());
        rules.put(PieceType.KNIGHT, JumpRule.knight());
        rules.put(PieceType.PAWN, new PawnRule());
    }

    public Set<Position> from(Board board, Position position) {
        Piece piece = board.getPiece(position).get();
        return rules.get(piece.getType()).destinations(board, position, piece.getColor());
    }
}
