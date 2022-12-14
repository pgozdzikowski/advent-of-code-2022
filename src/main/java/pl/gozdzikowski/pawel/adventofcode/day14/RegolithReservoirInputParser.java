package pl.gozdzikowski.pawel.adventofcode.day14;

import pl.gozdzikowski.pawel.adventofcode.day14.RegolithReservoir.Line;
import pl.gozdzikowski.pawel.adventofcode.day14.RegolithReservoir.Point;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;

public class RegolithReservoirInputParser {

    public List<Line> parseLines(List<String> input) {
        return input.stream()
                .flatMap(this::convertToLines)
                .toList();
    }

    private Stream<Line> convertToLines(String line) {
        String[] stringPoints = line.replaceAll(" ", "").split("->");
        List<Line> createdLines = new LinkedList<>();
        for (int i = 1; i < stringPoints.length; ++i) {
            String[] firstPoint = stringPoints[i - 1].split(",");
            String[] secondPoint = stringPoints[i].split(",");

            createdLines.add(Line.of(Point.of(parseInt(firstPoint[0]), parseInt(firstPoint[1])), Point.of(parseInt(secondPoint[0]), parseInt(secondPoint[1]))));
        }

        return createdLines.stream();
    }
}
