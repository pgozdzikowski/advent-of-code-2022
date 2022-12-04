package pl.gozdzikowski.pawel.adventofcode.day1;

import pl.gozdzikowski.pawel.adventofcode.shared.input.Input;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ElfCalories {

    public Long elfCalories(Input input) {
        List<String> calories = input.get();
        List<Long> elfCalories = calculateElfsCalories(calories);

        return elfCalories.stream().max(Long::compare).get();
    }

    public Long sumOfThreeTopElfs(Input input) {
        List<String> calories = input.get();
        List<Long> elfCalories = calculateElfsCalories(calories);
        return elfCalories.stream().sorted(Comparator.reverseOrder())
                .toList()
                .subList(0, 3)
                .stream()
                .mapToLong(Long::longValue)
                .sum();
    }

    private List<Long> calculateElfsCalories(List<String> calories) {
        List<Long> elfCalories = new ArrayList<>();
        long currentElfCalories = 0;

        for(String caloriesLine: calories) {
            if(caloriesLine.equals("")) {
                elfCalories.add(currentElfCalories);
                currentElfCalories = 0;
            } else {
                currentElfCalories += Long.parseLong(caloriesLine);
            }
        }

        if(currentElfCalories != 0)
            elfCalories.add(currentElfCalories);

        return elfCalories;
    }
}
