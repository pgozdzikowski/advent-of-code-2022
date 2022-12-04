package pl.gozdzikowski.pawel.adventofcode.day1

import pl.gozdzikowski.pawel.adventofcode.input.FileInput
import pl.gozdzikowski.pawel.adventofcode.input.Input
import pl.gozdzikowski.pawel.adventofcode.input.ListInput
import spock.lang.Specification

class Day1Spec extends Specification {

    ElfCalories elfCalories = new ElfCalories()

    def 'should return proper elf calories for test input'() {
        given:
        List<String> inputList = [
                "1000",
                "2000",
                "3000",
                "",
                "4000",
                "",
                "5000",
                "6000",
                "",
                "7000",
                "8000",
                "9000",
                "",
                "10000"
        ]
        when:
            Long elfWithMostCalories = elfCalories.elfCalories(new ListInput(inputList))
        then:
            elfWithMostCalories == 24000
    }

    def 'results of tasks day1 part1'() {
        given:
        Input input = new FileInput('day1.txt')
        when:
        def calories = elfCalories.elfCalories(input)
        then:
        69206 == calories
    }

    def 'sum of top three'() {
        given:
        List<String> inputList = [
                "1000",
                "2000",
                "3000",
                "",
                "4000",
                "",
                "5000",
                "6000",
                "",
                "7000",
                "8000",
                "9000",
                "",
                "10000"
        ]
        when:
        Long elfWithMostCalories = elfCalories.sumOfThreeTopElfs(new ListInput(inputList))
        then:
        elfWithMostCalories == 45000
    }

    def 'results of tasks day1 part2'() {
        given:
        Input input = new FileInput('day1.txt')
        when:
        def calories = elfCalories.sumOfThreeTopElfs(input)
        then:
        197400 == calories
    }

}
