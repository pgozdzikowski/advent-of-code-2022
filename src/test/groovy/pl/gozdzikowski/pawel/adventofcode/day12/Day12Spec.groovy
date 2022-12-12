package pl.gozdzikowski.pawel.adventofcode.day12

import pl.gozdzikowski.pawel.adventofcode.shared.input.FileInput
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input
import spock.lang.Specification

class Day12Spec extends Specification {

    def 'should calculate lowest steps to exit'() {
        given:
        def algorithm = """Sabqponm
abcryxxl
accszExk
acctuvwj
abdefghi"""
        HillClimbingAlgorithm hillClimbingAlgorithm = new HillClimbingAlgorithm(convertStringToCharArray(algorithm) as char[][])
        when:
        long result = hillClimbingAlgorithm.howManyStepsToExitFromStartingPoint()
        then:
        result == 31
    }

    def 'task results'() {
        given:
        Input input = new FileInput('day12.txt')
        HillClimbingAlgorithm hillClimbingAlgorithm = new HillClimbingAlgorithm(convertStringToCharArray(input.getContent()) as char[][])
        when:
        long result = hillClimbingAlgorithm.howManyStepsToExitFromStartingPoint()
        then:
        result == 481
    }

    def 'should find shortest path from lowest elevation'() {
        given:
        def algorithm = """Sabqponm
abcryxxl
accszExk
acctuvwj
abdefghi"""
        HillClimbingAlgorithm hillClimbingAlgorithm = new HillClimbingAlgorithm(convertStringToCharArray(algorithm) as char[][])
        when:
        long result = hillClimbingAlgorithm.minimalStepsFromLowestElevation()
        then:
        result == 29
    }

    def 'task results part2'() {
        given:
        Input input = new FileInput('day12.txt')
        HillClimbingAlgorithm hillClimbingAlgorithm = new HillClimbingAlgorithm(convertStringToCharArray(input.getContent()) as char[][])
        when:
        long result = hillClimbingAlgorithm.minimalStepsFromLowestElevation()
        then:
        result == 480
    }

    def convertStringToCharArray(String content) {
        return content.split("\n").collect { it.chars().mapToObj{(char)it}.toList()}

    }
}
