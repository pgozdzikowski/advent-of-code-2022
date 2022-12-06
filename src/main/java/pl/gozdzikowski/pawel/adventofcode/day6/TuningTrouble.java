package pl.gozdzikowski.pawel.adventofcode.day6;

import pl.gozdzikowski.pawel.adventofcode.shared.collections.Pair;

import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

public class TuningTrouble {

    private final int START_OF_MARKER = 4;
    private final int START_OF_MESSAGE = 14;

    public int findPositionOfMarkerStart(String signal) {
        return findPositionWithWindow(toCharList(signal), START_OF_MARKER);
    }

    public int findPositionOfStartOfMessage(String signal) {
        return findPositionWithWindow(toCharList(signal), START_OF_MESSAGE);
    }

    private int findPositionWithWindow(List<Character> characters, int window) {
        return IntStream.range(0, characters.size())
                .mapToObj((current) -> Pair.of(current, new HashSet<>(characters.subList(current, Math.min(current + window, characters.size())))))
                .filter((pair) -> pair.right().size() == window)
                .findFirst()
                .map((pair) -> pair.left() + window)
                .orElseThrow(() -> new IllegalStateException("Unable to find sequence"));
    }

    private List<Character> toCharList(String signal) {
        return signal.chars()
                .mapToObj(c -> (char) c)
                .toList();
    }

}
