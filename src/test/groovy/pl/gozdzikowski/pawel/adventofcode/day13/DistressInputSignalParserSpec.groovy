package pl.gozdzikowski.pawel.adventofcode.day13

import pl.gozdzikowski.pawel.adventofcode.shared.input.FileInput
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input
import spock.lang.Specification
import spock.lang.Subject

import static java.util.List.of
import static pl.gozdzikowski.pawel.adventofcode.day13.DistressSignal.IntegerHolder
import static pl.gozdzikowski.pawel.adventofcode.day13.DistressSignal.ListHolder

class DistressInputSignalParserSpec extends Specification {

    @Subject
    DistressInputSignalParser distressInputSignalParser = new DistressInputSignalParser()


    def 'should parse text properly'() {
        given:
        String text = '[[1],[2,3,4]]'
        when:
        def signal = distressInputSignalParser.parseSignal(text)
        then:
        signal == new ListHolder(
                of(new ListHolder(of(new IntegerHolder(1))),
                        new ListHolder(
                                of(
                                        new IntegerHolder(2),
                                        new IntegerHolder(3),
                                        new IntegerHolder(4)
                                )
                        )
                )
        )
    }

    def 'empty list sample'() {
        given:
        String text = '[[[]]]'
        when:
        def signal = distressInputSignalParser.parseSignal(text)
        then:
        signal == new ListHolder(
                of(new ListHolder(of(new ListHolder(of()))))
        )
    }

    def 'should contains good hierarchy'() {
        given:
        Input file = new FileInput('day13.txt')
        List<String> signals = file.get().findAll {it != ""}
        when:
        def parsedSignal = toHolders(signals)
        then:
        parsedSignal.collect{it.toString()} ==  signals
    }

    List<DistressSignal.Holder> toHolders(List<String> signals) {
        return signals.collect { distressInputSignalParser.parseSignal(it)}
    }
}
