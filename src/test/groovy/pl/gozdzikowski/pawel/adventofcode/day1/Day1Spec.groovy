package pl.gozdzikowski.pawel.adventofcode.day1

import pl.gozdzikowski.pawel.adventofcode.shared.input.FileInput
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input
import pl.gozdzikowski.pawel.adventofcode.shared.input.ListInput
import spock.lang.Specification

class Day1Spec extends Specification {

    def sampleFromTask = [
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

    ElfCalories elfCalories = new ElfCalories()

    def 'should return proper elf calories for test input'() {
        when:
            Long elfWithMostCalories = elfCalories.elfCalories(new ListInput(sampleFromTask))
        then:
            elfWithMostCalories == 24000
    }

    def 'results of tasks day1 part1'() {
        given:
        Input input = new FileInput('day1.txt')
        when:
        Long calories = elfCalories.elfCalories(input)
        then:
        69206 == calories
    }

    def 'sum of top three elf calories'() {
        when:
        Long elfWithMostCalories = elfCalories.sumOfThreeTopElfs(new ListInput(sampleFromTask))
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
