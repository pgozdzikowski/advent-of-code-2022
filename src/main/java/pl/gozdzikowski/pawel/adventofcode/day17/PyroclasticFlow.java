package pl.gozdzikowski.pawel.adventofcode.day17;

import java.util.*;

public class PyroclasticFlow {

    private final int CAVE_WIDTH = 7;
    private final int INITIAL_FLOOR_POSITION = 4;
    static char[][] MINUS = {{'#', '#', '#', '#'}};
    static char[][] PLUS = {
            {'.', '#', '.'},
            {'#', '#', '#'},
            {'.', '#', '.'},
    };

    static char[][] L = {
            {'.', '.', '#'},
            {'.', '.', '#'},
            {'#', '#', '#'},
    };

    static char[][] I = {
            {'#'},
            {'#'},
            {'#'},
            {'#'}
    };

    static char[][] SQUARE = {
            {'#', '#'},
            {'#', '#'}
    };

    enum Symbols {
        MINUS(PyroclasticFlow.MINUS), PLUS(PyroclasticFlow.PLUS), L(PyroclasticFlow.L), I((PyroclasticFlow.I)), SQUARE(PyroclasticFlow.SQUARE);
        char[][] shape;

        Symbols(char[][] shape) {
            this.shape = shape;
        }

        public char[][] getShape() {
            return shape;
        }
    }

    ;

    enum Move {
        LEFT('<', -1), RIGHT('>', 1);
        char symbol;
        int direction;

        Move(char symbol, int direction) {
            this.symbol = symbol;
            this.direction = direction;
        }

        public static Move from(char symbol) {
            return Arrays.stream(values())
                    .filter((move) -> move.symbol == symbol)
                    .findFirst().get();
        }
    }

    public long simulateUntil(int numOfRocks, char[] moves) {
        List<Point> currentPoints = new LinkedList<>(floorPoints());
        int iteration = 0;
        Deque<Symbols> symbols = new LinkedList<>(Arrays.asList(Symbols.values()));
        while (numOfRocks > 0) {
            Symbols symbol = symbols.pollFirst();
            List<Point> points = convertShapeToPoints(2, symbol.getShape());
            symbols.addLast(symbol);
            Integer maxPosition = points.stream().map(Point::getY).max(Integer::compareTo).get();
            Integer minimalPointsPositions = currentPoints.stream().map(Point::getY).min(Integer::compareTo).get();
            if (minimalPointsPositions - (maxPosition + 1) != 3) {
                currentPoints.forEach((point) -> point.translate(0, 3 - (minimalPointsPositions - (maxPosition + 1))));
            }

            while (true) {
                Move move = Move.from(moves[iteration % moves.length]);
                if (canBeTranslated(move, points, currentPoints)) {
                    points.forEach((point) -> point.translate(move.direction, 0));
                }
                iteration++;
                if (points.stream().anyMatch((point) -> point.inAfterOneDown(currentPoints))) {
                    currentPoints.addAll(points);
                    break;
                }
                points.forEach((point) -> point.translate(0, 1));
            }
            numOfRocks--;
        }

        Integer min = currentPoints.stream().map(Point::getY).min(Integer::compareTo).get();
        Integer max = currentPoints.stream().map(Point::getY).max(Integer::compareTo).get();
        return max - min;
    }

    private void printAfterIterations(List<Point> points) {
        Integer max = points.stream().map(Point::getY).max(Integer::compareTo).get();
        for (int i = 0; i < max + 1; ++i) {
            for (int j = 0; j < CAVE_WIDTH; ++j) {
                if (points.contains(new Point(j, i))) {
                    System.out.print("#");
                } else {
                    System.out.print(".");
                }
            }
            System.out.print("\n");
        }
    }

    private boolean canBeTranslated(Move move, List<Point> points, List<Point> currentPoints) {
        return points.stream().map((point) -> new Point(point.x + move.direction, point.y))
                .noneMatch((point) -> (point.x < 0 || point.x > 6 || currentPoints.contains(point)));
    }

    private Set<Point> floorPoints() {
        Set<Point> floorPoints = new HashSet<>();
        for (int i = 0; i < CAVE_WIDTH; ++i) {
            floorPoints.add(new Point(i, INITIAL_FLOOR_POSITION));
        }

        return floorPoints;
    }

    static class Point {
        private Integer x;
        private Integer y;

        public Point(Integer x, Integer y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public boolean inAfterOneDown(List<Point> points) {
            Point translated = new Point(x, y).translate(0, 1);
            return points.contains(translated);
        }

        public Point translate(int translateX, int translateY) {
            this.x += translateX;
            this.y += translateY;
            return this;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x.equals(point.x) && y.equals(point.y);
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    public List<Point> convertShapeToPoints(int translated, char[][] shape) {
        List<Point> shapePoints = new LinkedList<>();
        for (int y = 0; y < shape.length; ++y) {
            for (int x = 0; x < shape[y].length; ++x) {
                if (shape[y][x] == '#') {
                    shapePoints.add(new Point(x + translated, y));
                }
            }
        }
        return shapePoints;
    }

}
