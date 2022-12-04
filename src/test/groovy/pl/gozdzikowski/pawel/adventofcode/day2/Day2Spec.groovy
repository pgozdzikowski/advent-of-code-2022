package pl.gozdzikowski.pawel.adventofcode.day2

import pl.gozdzikowski.pawel.adventofcode.shared.input.FileInput
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input
import pl.gozdzikowski.pawel.adventofcode.shared.input.ListInput
import spock.lang.Specification

class Day2Spec extends Specification {

    PaperScissorsAndRock paperScisorAndRock = new PaperScissorsAndRock()

    def 'should calculate result'() {
        given:
        Input input = new ListInput([
                'A Y',
                'B X',
                'C Z'
        ])
        when:
        long result = paperScisorAndRock.calculateResults(input)
        then:
        result == 15
    }

    def 'task output'() {
        given:
        Input input = new FileInput("day2.txt")
        when:
        long result = paperScisorAndRock.calculateResults(input)
        then:
        result == 13675
    }

    def 'should calculate result with how to end'() {
        given:
        Input input = new ListInput([
                'A Y',
                'B X',
                'C Z'
        ])
        when:
        long result = paperScisorAndRock.calculateResultsWIthHowToEnd(input)
        then:
        result == 12
    }

    def 'task output 2'() {
        given:
        Input input = new FileInput("day2.txt")
        when:
        long result = paperScisorAndRock.calculateResultsWIthHowToEnd(input)
        then:
        result == 14184
    }
}
