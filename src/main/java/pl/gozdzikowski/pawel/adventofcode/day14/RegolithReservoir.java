package pl.gozdzikowski.pawel.adventofcode.day14;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RegolithReservoir {
    static final Point STARTING_POINT = new Point(500, 0);

    long howManySandCanFallDownBeforeGoToEndlessWorld(List<Line> rockLines) {
        Set<Point> rocks = rockLines.stream()
                .flatMap((line) -> line.getPoints().stream())
                .collect(Collectors.toSet());
        Point minYPoint = rocks.stream().max(Comparator.comparing(Point::y))
                .get();

        Set<Point> finalSandsPositions = new HashSet<>();

        Point sandPosition = new Point(500, 0);

        while (sandPosition.y < minYPoint.y) {
            if (sandOrRocksPositionsContains(rocks, finalSandsPositions, sandPosition.with(0, 1))) {
                if (!sandOrRocksPositionsContains(rocks, finalSandsPositions, sandPosition.with(-1, 1))) {
                    sandPosition = sandPosition.with(-1, 1);
                } else if (!sandOrRocksPositionsContains(rocks, finalSandsPositions, sandPosition.with(1, 1))) {
                    sandPosition = sandPosition.with(1, 1);
                } else {
                    finalSandsPositions.add(sandPosition);
                    sandPosition = new Point(500, 0);
                }
            } else {
                sandPosition = sandPosition.with(0, 1);
            }
        }
        return finalSandsPositions.size();
    }

    long howManySandCanFallDownBeforeGoToFloor(List<Line> rockLines) {
        Set<Point> rocks = rockLines.stream()
                .flatMap((line) -> line.getPoints().stream())
                .collect(Collectors.toSet());
        Point minYPoint = rocks.stream().max(Comparator.comparing(Point::y))
                .get();

        Set<Point> finalSandsPositions = new HashSet<>();

        Point sandPosition = STARTING_POINT;


        while (true) {
            if (sandOrRocksPositionsContains(rocks, finalSandsPositions, sandPosition.with(0, 1)) || sandPosition.y + 1 == minYPoint.y + 2) {
                if (!sandOrRocksPositionsContains(rocks, finalSandsPositions, sandPosition.with(-1, 1)) && sandPosition.y + 1 != minYPoint.y + 2) {
                    sandPosition = sandPosition.with(-1, 1);
                } else if (!sandOrRocksPositionsContains(rocks, finalSandsPositions, sandPosition.with(1, 1)) && sandPosition.y + 1 != minYPoint.y + 2) {
                    sandPosition = sandPosition.with(1, 1);
                } else {
                    finalSandsPositions.add(sandPosition);
                    if (sandPosition.equals(STARTING_POINT))
                        break;
                    sandPosition = STARTING_POINT;
                }
            } else {
                sandPosition = sandPosition.with(0, 1);
            }
        }
        return finalSandsPositions.size();
    }

    boolean sandOrRocksPositionsContains(Set<Point> rocks, Set<Point> sands, Point point) {
        return rocks.contains(point) || sands.contains(point);
    }

    record Line(
            Point start,
            Point end
    ) {
        static Line of(Point start, Point end) {
            return new Line(
                    start, end
            );
        }

        Set<Point> getPoints() {
            Set<Point> points = new HashSet<>();
            if (start.x == end.x) {
                for (int currentPosition = Math.min(start.y, end.y); currentPosition <= Math.max(start.y, end.y); ++currentPosition) {
                    points.add(Point.of(start.x, currentPosition));
                }
            } else {
                for (int currentPosition = Math.min(start.x, end.x); currentPosition <= Math.max(start.x, end.x); ++currentPosition) {
                    points.add(Point.of(currentPosition, start.y));
                }
            }
            return points;
        }
    }

    record Point(
            int x,
            int y
    ) {
        static Point of(int x, int y) {
            return new Point(x, y);
        }

        Point with(int offsetX, int offsetY) {
            return new Point(x + offsetX, y + offsetY);
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}
