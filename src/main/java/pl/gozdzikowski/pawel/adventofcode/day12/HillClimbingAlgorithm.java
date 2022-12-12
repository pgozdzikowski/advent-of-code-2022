package pl.gozdzikowski.pawel.adventofcode.day12;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HillClimbingAlgorithm {

    private char[][] climbingMap;

    public HillClimbingAlgorithm(char[][] climbingMap) {
        this.climbingMap = climbingMap;
    }

    public long howManyStepsToExit() {
        Set<Position> visitedPositions = new HashSet<>();
        Position currentPosition = findStartingPosition();
        return howManyStepsToExit(visitedPositions, currentPosition, 0L);
    }

    private Position findStartingPosition() {
        for (int y = 0; y < climbingMap.length; ++y) {
            for (int x = 0; x < climbingMap[y][x]; ++x) {
                if (climbingMap[y][x] == 'S') {
                    return new Position(x, y);
                }
            }
        }
        throw new IllegalStateException("Unable to find staring position");
    }

    private long howManyStepsToExit(Set<Position> visitedPositions, Position currentPosition, long howManyToExit) {

        if (climbingMap[currentPosition.y][currentPosition.x] == 'z') {
            return howManyToExit + 1;
        }
        Set<Position> positions = relativePositions(currentPosition);
        visitedPositions.add(currentPosition);
        positions.removeAll(visitedPositions);

        if (positions.isEmpty())
            return Integer.MAX_VALUE;


        List<Long> found = positions.stream().filter((pos) -> allowedToMove(pos, currentPosition))
                .map((pos) -> howManyStepsToExit(visitedPositions, pos, howManyToExit + 1))
                .toList();

        if (found.isEmpty()) {
            return Integer.MAX_VALUE;
        }


        return found
                .stream()
                .min(Long::compareTo).get();
    }

    private boolean allowedToMove(Position pos, Position currentPosition) {
        char currentHigh = climbingMap[currentPosition.y][currentPosition.x];
        char nextHigh = climbingMap[pos.y][pos.x];
        return nextHigh - currentHigh <= 1 || currentHigh == 'S';
    }

    Set<Position> relativePositions(Position currentPosition) {
        return Stream.of(new Position(currentPosition.x - 1, currentPosition.y),
                        new Position(currentPosition.x, currentPosition.y + 1),
                        new Position(currentPosition.x, currentPosition.y - 1),
                        new Position(currentPosition.x + 1, currentPosition.y)
                ).filter((pos) -> (pos.x >= 0 && pos.x < climbingMap[0].length) &&
                        (pos.y >= 0 && pos.y < climbingMap.length))
                .collect(Collectors.toSet());

    }

    record Position(
            int x,
            int y
    ) {
    }
}
