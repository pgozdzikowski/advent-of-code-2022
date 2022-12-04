package pl.gozdzikowski.pawel.adventofcode.day3;

import pl.gozdzikowski.pawel.adventofcode.shared.collections.ListExt;
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input;

import java.util.*;
import java.util.stream.Collectors;

public class RucksackReorganization {

    public long calculatePrioritiesOfRucksack(Input input) {
        return input.get().stream()
                .mapToLong(this::calculateForSingleRucksack)
                .sum();
    }

    public long calculatePrioritiesOfRucksackUniqueForThree(Input input) {
        return ListExt.partition(input.get(), 3)
                .stream().mapToLong(this::calculateForSingleRucksackForThree)
                .sum();
    }

    private long calculateForSingleRucksack(String rucksack) {
        List<Character> rucksackItems = itemsInRucksack(rucksack);

        int middleIndex = rucksackItems.size() / 2;
        List<Character> firstCompartment = rucksackItems.subList(0, middleIndex);
        List<Character> secondCompartment = rucksackItems.subList(middleIndex, rucksackItems.size());

        Set<Character> uniqueFirstCompartment = new HashSet<>(firstCompartment);
        Set<Character> uniqueSecondCompartment = new HashSet<>(secondCompartment);

        Set<Character> itemsIntersection = new HashSet<>(uniqueFirstCompartment);
        itemsIntersection.retainAll(uniqueSecondCompartment);

        return itemsIntersection.stream()
                .map(this::mapToPriority)
                .mapToLong(Long::valueOf)
                .sum();
    }

    private long calculateForSingleRucksackForThree(List<String> rucksacks) {
        List<Set<Character>> rucksacksWithSetOfItems = rucksacks.stream()
                .map(this::itemInRucksackUnique)
                .toList();

        return rucksacksWithSetOfItems.stream()
                .reduce(rucksacksWithSetOfItems.get(0), this::intersectWithOther)
                .stream()
                .map(this::mapToPriority)
                .mapToLong(Long::longValue)
                .sum();
    }

    private Set<Character> intersectWithOther(Set<Character> accumulator, Set<Character> next) {
        accumulator.retainAll(next);
        return accumulator;
    }

    private List<Character> itemsInRucksack(String rucksack) {
        return rucksack.chars()
                .mapToObj((c) -> (char) c)
                .collect(Collectors.toList());
    }

    private Set<Character> itemInRucksackUnique(String rucksack) {
        return new HashSet<>(itemsInRucksack(rucksack));
    }

    private long mapToPriority(Character c) {
        return Character.isUpperCase(c) ? c - 'A' + 27 : c - 'a' + 1;
    }
}
