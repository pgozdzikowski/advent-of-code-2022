package pl.gozdzikowski.pawel.adventofcode.day6

import pl.gozdzikowski.pawel.adventofcode.shared.input.FileInput
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input
import spock.lang.Specification

class Day6Spec extends Specification {

    TuningTrouble tuningTrouble = new TuningTrouble()

    def 'should find position of markers in signals'() {
        when:
        int position = tuningTrouble.findPositionOfMarkerStart(signal)
        then:
        position == result
        where:
        signal                              || result
        "bvwbjplbgvbhsrlpgdmjqwftvncz"      || 5
        "nppdvjthqldpwncqszvftbrmjlhg"      || 6
        "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" || 10
        "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"  || 11
    }

    def 'task result'() {
        given:
        Input input = new FileInput('day6.txt')
        when:
        int position = tuningTrouble.findPositionOfMarkerStart(input.getContent())
        then:
        position == 1802
    }

    def 'should find start-of-message marker in signals'() {
        when:
        int position = tuningTrouble.findPositionOfStartOfMessage(signal)
        then:
        position == result
        where:
        signal                              || result
        "mjqjpqmgbljsphdztnvjfqwrcgsmlb"    || 19
        "bvwbjplbgvbhsrlpgdmjqwftvncz"      || 23
        "nppdvjthqldpwncqszvftbrmjlhg"      || 23
        "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" || 29
        "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"  || 26
    }

    def 'task result 2'() {
        given:
        Input input = new FileInput('day6.txt')
        when:
        int position = tuningTrouble.findPositionOfStartOfMessage(input.getContent())
        then:
        position == 3551
    }
}
