package pl.gozdzikowski.pawel.adventofcode.day3

import pl.gozdzikowski.pawel.adventofcode.shared.input.FileInput
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input
import pl.gozdzikowski.pawel.adventofcode.shared.input.ListInput
import spock.lang.Specification

class Day3Spec extends Specification {

    RucksackReorganization rucksackPacking = new RucksackReorganization()

    def 'should return calculated priorities'() {
        given:
        Input input = new ListInput([
                "vJrwpWtwJgWrhcsFMMfFFhFp",
                "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
                "PmmdzqPrVvPwwTWBwg",
                "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
                "ttgJtRGJQctTZtZT",
                "CrZsJsPPZsGzwwsLwLmpwMDw"
        ])
        when:
        def result = rucksackPacking.calculatePrioritiesOfRucksack(input)
        then:
        result == 157
    }

    def 'task output'() {
        given:
        Input input = new FileInput('day3.txt')
        when:
        def result = rucksackPacking.calculatePrioritiesOfRucksack(input)
        then:
        result == 7811
    }

    def 'should calculate unique items priorities for 3 rucksacks'() {
        given:
        Input input = new ListInput([
                "vJrwpWtwJgWrhcsFMMfFFhFp",
                "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
                "PmmdzqPrVvPwwTWBwg",
                "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
                "ttgJtRGJQctTZtZT",
                "CrZsJsPPZsGzwwsLwLmpwMDw"
        ])
        when:
        def result = rucksackPacking.calculatePrioritiesOfRucksackUniqueForThree(input)
        then:
        result == 70
    }

    def 'task output 2'() {
        given:
        Input input = new FileInput('day3.txt')
        when:
        def result = rucksackPacking.calculatePrioritiesOfRucksackUniqueForThree(input)
        then:
        result == 2639
    }
}
