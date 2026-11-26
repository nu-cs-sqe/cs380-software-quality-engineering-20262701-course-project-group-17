package domain.move;

import domain.Board;
import domain.Position;
import domain.piece.PieceColor;

import java.util.Set;

interface MoveRule {
    Set<Position> destinations(Board board, Position from, PieceColor color);
}
