package domain.move;

import domain.Board;
import domain.Position;
import domain.piece.PieceColor;

final class Squares {
    private Squares() {
    }

    static boolean isEnemy(Board board, Position position, PieceColor color) {
        return board.getPiece(position)
                .filter(piece -> piece.getColor() != color)
                .isPresent();
    }

    static boolean isEmptyOrEnemy(Board board, Position position, PieceColor color) {
        return board.isEmpty(position) || isEnemy(board, position, color);
    }
}
