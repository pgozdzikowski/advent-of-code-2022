package pl.gozdzikowski.pawel.adventofcode.day9;

import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class RopeBridge {

    private static Pattern commandPattern = Pattern.compile("([URDL]) (\\d+)");

    public int countAllVisitedPointsByTailWithSize(List<String> commands, int size) {
        Rope rope = new Rope(size);
        List<Point> visitedPoints = new ArrayList<>(List.of(rope.getTail()));

        commands.stream()
                .map(this::convertToCommand)
                .forEach((command) -> executeSingleCommand(visitedPoints, rope, command));

        return Set.copyOf(visitedPoints).size();
    }

    private void executeSingleCommand(List<Point> visitedPoints, Rope rope, Command command) {
        IntStream.range(0, command.steps())
                .forEach(sequenceNum -> {
                            Point tailPositionBeforeMove = rope.getTail();
                            rope.executeCommand(command);
                            if (!tailPositionBeforeMove.equals(rope.getTail())) {
                                visitedPoints.add(rope.getTail());
                            }
                        }
                );
    }

    private Command convertToCommand(String command) {
        Matcher matcher = commandPattern.matcher(command);
        matcher.find();
        return new Command(matcher.group(1), Integer.parseInt(matcher.group(2)));
    }

    class Rope {
        private List<Point> points;

        public Rope(int size) {
            List<Point> points = IntStream.range(0, size)
                    .mapToObj((ignore) -> new Point(0, 0))
                    .toList();
            this.points = new ArrayList<>(points);
        }

        void executeCommand(Command command) {
            Point newHeadPosition = command.execute(getHead());
            points.set(points.size() - 1, newHeadPosition);
            for (int i = 0; i < points.size() - 1; ++i) {
                Point currentPointBefore = points.get(points.size() - i - 1);
                Point currentPoint = points.get(points.size() - i - 2);
                if (!pointsAdjacent(currentPointBefore, currentPoint)) {
                    Point vectorOfMovement = findVectorOfMovement(currentPointBefore, currentPoint);
                    Point newPointPosition = currentPoint.moveWith(vectorOfMovement.x, vectorOfMovement.y);
                    points.set(points.size() - i - 2, newPointPosition);
                }
            }
        }

        private Point findVectorOfMovement(Point head, Point tail) {
            int sgnX = (int) Math.signum(head.x - tail.x);
            int sgnY = (int) Math.signum(head.y - tail.y);
            if (head.x == tail.x) {
                return new Point(0, sgnY);
            } else if (head.y == tail.y) {
                return new Point(sgnX, 0);
            } else {
                return new Point(sgnX, sgnY);
            }
        }

        public Point getHead() {
            return points.get(points.size() - 1);
        }

        public Point getTail() {
            return points.get(0);
        }
    }


    record Point(
            int x,
            int y
    ) {
        Point moveWith(int xSteps, int ySteps) {
            return new Point(x + xSteps, y + ySteps);
        }
    }

    record Command(
            String direction,
            int steps
    ) {

        public Point execute(Point point) {
            Point vector = createVector(direction);
            return point.moveWith(vector.x, vector.y);
        }

        private Point createVector(String direction) {
            return switch (direction) {
                case "U" -> new Point(0, -1);
                case "D" -> new Point(0, 1);
                case "R" -> new Point(1, 0);
                case "L" -> new Point(-1, 0);
                default -> throw new IllegalStateException("Unable to find mapping");
            };
        }
    }


    boolean pointsAdjacent(Point firstPoint, Point secondPoint) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (firstPoint.moveWith(j, i).equals(secondPoint)) {
                    return true;
                }
            }
        }
        return false;
    }
}
