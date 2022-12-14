package pl.gozdzikowski.pawel.adventofcode.day13

import pl.gozdzikowski.pawel.adventofcode.shared.input.FileInput
import spock.lang.Specification
import spock.lang.Subject

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
        ordered == 13
    }
}
