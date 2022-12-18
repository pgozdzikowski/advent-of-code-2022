package pl.gozdzikowski.pawel.adventofcode.day17

import pl.gozdzikowski.pawel.adventofcode.shared.input.FileInput
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input
import spock.lang.Specification


class Day17Spec extends Specification {

    PyroclasticFlow pyroclasticFlow = new PyroclasticFlow()
    def 'should calculate max tower height'() {
        given:
        String moves = '>>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>'
        expect:
        3068L == pyroclasticFlow.simulateUntil(2022, moves.split('') as char[])
    }

    def 'solution'() {
        given:
        Input input = new FileInput('day17.txt')
        expect:
        3173L == pyroclasticFlow.simulateUntil(2022, input.getContent().split('') as char[])
    }

}
