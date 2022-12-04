package pl.gozdzikowski.pawel.adventofcode.day2;

import pl.gozdzikowski.pawel.adventofcode.input.Input;

import java.util.List;
import java.util.Map;

public class PaperScissorsAndRock {

    Map<String, Integer> MY_RESPONSES_POINTS = Map.of(
            "X", 1,
            "Y", 2,
            "Z", 3
    );

    Map<String, String> WINNING_RULES = Map.of(
            "A", "Y",
            "B", "Z",
            "C", "X"
    );

    Map<String, String> DRAW_RULES = Map.of(
            "A", "X",
            "B", "Y",
            "C", "Z"
    );

    Map<String, String> LOOSE_RULES = Map.of(
            "A", "Z",
            "B", "X",
            "C", "Y"
    );

    public Long calculateResults(Input input) {
        List<String> rounds = input.get();

        return rounds.stream().map(
                        this::calculateResulsForSingleRound
                )
                .mapToLong(Long::longValue)
                .sum();

    }

    public Long calculateResultsWIthHowToEnd(Input input) {
        List<String> rounds = input.get();

        return rounds.stream().map(
                        this::calculateWithHowToEnd
                )
                .mapToLong(Long::longValue)
                .sum();

    }

    private long calculateResulsForSingleRound(String input) {
        String[] splited = input.split("\\s");
        return calculateSingleResult(splited[0], splited[1]);
    }

    private long calculateWithHowToEnd(String input) {
        String[] splited = input.split("\\s");
        String myResponse = mapResponseToProper(splited[0], splited[1]);
        return calculateSingleResult(splited[0], myResponse);
    }

    private Long calculateSingleResult(String oponent, String myResponse) {
        long results = MY_RESPONSES_POINTS.get(myResponse);

        if (WINNING_RULES.get(oponent).equals(myResponse))
            return results + 6;
        else if (DRAW_RULES.get(oponent).equals(myResponse)) {
            return results + 3;
        }
        return results;
    }

    private String mapResponseToProper(String opponent, String roundResult) {
        return switch(roundResult) {
            case "X" -> LOOSE_RULES.get(opponent);
            case "Y" -> DRAW_RULES.get(opponent);
            case "Z" -> WINNING_RULES.get(opponent);
            default -> throw new IllegalStateException("Unable to map");
        };
    }
}
