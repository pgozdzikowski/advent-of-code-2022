package pl.gozdzikowski.pawel.adventofcode.day13

import pl.gozdzikowski.pawel.adventofcode.shared.input.FileInput
import spock.lang.Specification
import spock.lang.Subject
import sun.misc.Signal

import static pl.gozdzikowski.pawel.adventofcode.day13.DistressSignal.FIRST_DIVIDER
import static pl.gozdzikowski.pawel.adventofcode.day13.DistressSignal.SECOND_DIVIDER

class Day13Spec extends Specification {

    DistressInputSignalParser distressInputSignalParser = new DistressInputSignalParser()
    @Subject
    DistressSignal distressSignal = new DistressSignal()

    def 'should calculate pairs in order'() {
        given:
        List<DistressSignal.SignalPair> signalPairs = distressInputSignalParser.parseInput(new FileInput('day13sample.txt').getContent())
        when:
        def ordered = distressSignal.calculateInRightOrder(signalPairs)
        then:
        ordered == 13
    }

    def 'task results'() {
        given:
        List<DistressSignal.SignalPair> signalPairs = distressInputSignalParser.parseInput(new FileInput('day13.txt').getContent())
        when:
        def ordered = distressSignal.calculateInRightOrder(signalPairs)
        then:
        ordered == 6656
    }

    def 'should sort pairs and return'() {
        given:
        List<DistressSignal.SignalPair> signalPairs = distressInputSignalParser.parseInput(new FileInput('day13sample.txt').getContent())
        List<Signal> dividers = [FIRST_DIVIDER, SECOND_DIVIDER]
        when:
        def ordered = distressSignal.calculateIndexOfSortedDividerPacketsInRightOrder(signalPairs, dividers)
        then:
        ordered == 140
    }

    def 'task results part 2'() {
        given:
        List<DistressSignal.SignalPair> signalPairs = distressInputSignalParser.parseInput(new FileInput('day13.txt').getContent())
        List<Signal> dividers = [FIRST_DIVIDER, SECOND_DIVIDER]
        when:
        def ordered = distressSignal.calculateIndexOfSortedDividerPacketsInRightOrder(signalPairs, dividers)
        then:
        ordered == 19716
    }
}
