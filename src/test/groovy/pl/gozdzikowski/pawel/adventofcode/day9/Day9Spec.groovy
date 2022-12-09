package pl.gozdzikowski.pawel.adventofcode.day9

import pl.gozdzikowski.pawel.adventofcode.shared.input.FileInput
import spock.lang.Specification

class Day9Spec extends Specification {

    def 'should find all tail positions'() {
        given:
            List<String> inputs = [
                    "R 4",
                    "U 4",
                    "L 3",
                    "D 1",
                    "R 4",
                    "D 1",
                    "L 5",
                    "R 2"
            ]
        RopeBridge ropeBridge = new RopeBridge()
        when:
        def count = ropeBridge.countAllVisitedPointsByTailWithSize(inputs,2)
        then:
        count == 13
    }

    def 'task solution'() {
        given:
        List<String> inputs = new FileInput('day9.txt').get()
        RopeBridge ropeBridge = new RopeBridge()
        when:
        def count = ropeBridge.countAllVisitedPointsByTailWithSize(inputs,2)
        then:
        count == 5710
    }

    def 'should find all tail positions rope length 10'() {
        given:
        List<String> inputs = [
                "R 5",
                "U 8",
                "L 8",
                "D 3",
                "R 17",
                "D 10",
                "L 25",
                "U 20"
        ]
        RopeBridge ropeBridge = new RopeBridge()
        when:
        def count = ropeBridge.countAllVisitedPointsByTailWithSize(inputs, 10)
        then:
        count == 36
    }

    def 'task solution 2'() {
        given:
        List<String> inputs = new FileInput('day9.txt').get()
        RopeBridge ropeBridge = new RopeBridge()
        when:
        def count = ropeBridge.countAllVisitedPointsByTailWithSize(inputs, 10)
        then:
        count == 2259
    }

}
