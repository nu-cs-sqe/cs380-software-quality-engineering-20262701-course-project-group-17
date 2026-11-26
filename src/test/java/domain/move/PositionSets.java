package domain.move;

import domain.Position;

import java.util.HashSet;
import java.util.Set;

final class PositionSets {
    private PositionSets() {
    }

    static Set<Position> squares(int... rowColPairs) {
        Set<Position> result = new HashSet<>();
        for (int i = 0; i < rowColPairs.length; i += 2) {
            result.add(new Position(rowColPairs[i], rowColPairs[i + 1]));
        }
        return result;
    }
}
