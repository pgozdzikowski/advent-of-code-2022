package pl.gozdzikowski.pawel.adventofcode.day14

import pl.gozdzikowski.pawel.adventofcode.shared.input.FileInput
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input
import spock.lang.Ignore
import spock.lang.Specification

class Day14Spec extends Specification{

    RegolithReservoirInputParser parser = new RegolithReservoirInputParser()
    RegolithReservoir regolithReservoir = new RegolithReservoir()

    def 'should simulate'() {
        given:
        List<String> input = [
                "498,4 -> 498,6 -> 496,6",
                "503,4 -> 502,4 -> 502,9 -> 494,9"
        ]
        def lines = parser.parseLines(input)
        when:
        def howManySands = regolithReservoir.howManySandCanFallDownBeforeGoToEndlessWorld(lines)
        then:
        howManySands == 24
    }

    def 'task solution'() {
        given:
        Input input = new FileInput('day14.txt')
        def lines = parser.parseLines(input.get())
        when:
        def howManySands = regolithReservoir.howManySandCanFallDownBeforeGoToEndlessWorld(lines)
        then:
        howManySands == 779
    }

    def 'should simulate with floor'() {
        given:
        List<String> input = [
                "498,4 -> 498,6 -> 496,6",
                "503,4 -> 502,4 -> 502,9 -> 494,9"
        ]
        def lines = parser.parseLines(input)
        when:
        def howManySands = regolithReservoir.howManySandCanFallDownBeforeGoToFloor(lines)
        then:
        howManySands == 93
    }

    def 'task solution part2'() {
        given:
        Input input = new FileInput('day14.txt')
        def lines = parser.parseLines(input.get())
        when:
        def howManySands = regolithReservoir.howManySandCanFallDownBeforeGoToFloor(lines)
        then:
        howManySands == 27426
    }
}
