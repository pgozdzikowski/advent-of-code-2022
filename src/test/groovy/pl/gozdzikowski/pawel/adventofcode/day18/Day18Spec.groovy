package pl.gozdzikowski.pawel.adventofcode.day18

import pl.gozdzikowski.pawel.adventofcode.shared.input.FileInput
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input
import spock.lang.Specification

class Day18Spec extends Specification {

    BoilingBoulders boilingBoulders = new BoilingBoulders()

    def 'should calculate visible sides of cubes'() {
        given:
        Input input = new FileInput('day18sample.txt')
        expect:
        64 == boilingBoulders.countVisibleSides(input.get())
    }

    def 'task results part1'() {
        given:
        Input input = new FileInput('day18.txt')
        expect:
        4500 == boilingBoulders.countVisibleSides(input.get())
    }
}
