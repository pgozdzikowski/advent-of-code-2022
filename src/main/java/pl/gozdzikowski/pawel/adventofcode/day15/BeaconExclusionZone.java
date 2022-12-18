package pl.gozdzikowski.pawel.adventofcode.day15;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Integer.max;
import static java.lang.Integer.parseInt;

public class BeaconExclusionZone {

    private Pattern pattern = Pattern.compile("Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)");

    public long countBeaconsWhichArePossiblyOnRow(
            List<String> inputs,
            int atRow
    ) {

        Set<Point> beaconsPossiblyNotPresent = new HashSet<>();

        Set<Point> beaconsPositions = new HashSet<>();

        for (int i = 0; i < inputs.size(); ++i) {
            String input = inputs.get(i);
            Matcher matcher = pattern.matcher(input);
            matcher.find();
            Point sensorPosition = Point.of(Long.parseLong(matcher.group(1)), Long.parseLong(matcher.group(2)));
            Point beaconPosition = Point.of(Long.parseLong(matcher.group(3)), Long.parseLong(matcher.group(4)));
            beaconsPositions.add(beaconPosition);
            long distance = calculateManhatanDistance(sensorPosition, beaconPosition);
            for (long x = -distance; x < distance; ++x) {
                Point currentPoint = Point.of(sensorPosition.x + x, atRow);
                if (calculateManhatanDistance(sensorPosition, currentPoint) <= distance && !beaconsPositions.contains(currentPoint))
                    beaconsPossiblyNotPresent.add(currentPoint);
            }
        }
        return beaconsPossiblyNotPresent.size();
    }

    public long calculateTuningFrequency(
            List<String> inputs,
            int maxFrequency
    ) {
        Map<Point, Long> sensorToDistance = new HashMap<>();

        for (int i = 0; i < inputs.size(); ++i) {
            String input = inputs.get(i);
            Matcher matcher = pattern.matcher(input);
            matcher.find();
            Point sensorPosition = Point.of(Long.parseLong(matcher.group(1)), Long.parseLong(matcher.group(2)));
            Point beaconPosition = Point.of(Long.parseLong(matcher.group(3)), Long.parseLong(matcher.group(4)));
            sensorToDistance.put(sensorPosition, calculateManhatanDistance(sensorPosition, beaconPosition));
        }

        Optional<Point> beaconPosition = Optional.empty();

        for (Point sensor : sensorToDistance.keySet()) {
            Long distance = sensorToDistance.get(sensor);

            beaconPosition = Arrays.asList(
                            checkInRange(Point.of(sensor.x, sensor.y - distance - 1), Point.of(sensor.x + distance + 1, sensor.y), sensorToDistance),
                            checkInRange(Point.of(sensor.x, sensor.y + distance + 1), Point.of(sensor.x + distance + 1, sensor.y), sensorToDistance),
                            checkInRange(Point.of(sensor.x - distance - 1, sensor.y), Point.of(sensor.x, sensor.y + distance + 1), sensorToDistance),
                            checkInRange(Point.of(sensor.x - distance - 1, sensor.y), Point.of(sensor.x, sensor.y - distance - 1), sensorToDistance)
                    ).stream()
                    .filter(Objects::nonNull)
                    .filter((point) -> point.x >= 0 && point.y <= maxFrequency)
                    .findFirst();

            if(beaconPosition.isPresent())
                break;

        }


        return beaconPosition.get().x() * 4000000 + beaconPosition.get().y();
    }

    /* y = ax + b
               a = (y2 - y1)/(x2 - x1)
               b = y1 - a*x1
             */
    public Point checkInRange(Point from, Point to, Map<Point, Long> sensors) {
        float a = (to.y - from.y) / (to.x - from.x);
        float b = from.y - a * from.x;

        Point current = from.x < 0 ? Point.of(0, (long)b) : from;
        while(!current.equals(to) ) {
            boolean allSensor = true;
            for (Point sensor : sensors.keySet()) {
                if (calculateManhatanDistance(sensor, current) <= sensors.get(sensor)) {
                    allSensor = false;
                }
            }
            if (allSensor) {
                return current;
            }
            current = Point.of(current.x + 1, (long)(a * (current.x + 1) + b));
        }

        return null;
    }


    long calculateManhatanDistance(Point firstPoint, Point secondPoint) {
        return Math.abs(firstPoint.x - secondPoint.x) + Math.abs(firstPoint.y - secondPoint.y);
    }

    record Point(
            long x,
            long y
    ) {
        static Point of(long x, long y) {
            return new Point(x, y);
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
