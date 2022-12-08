package pl.gozdzikowski.pawel.adventofcode.day8

import pl.gozdzikowski.pawel.adventofcode.shared.input.FileInput
import spock.lang.Specification

class Day8Spec extends Specification {

    def 'should calculate trees visible from outside'() {
        given:
        List<String> input = [
                "30373",
                "25512",
                "65332",
                "33549",
                "35390"
        ]
        TreetopTreeHouse treetopTreeHouse = new TreetopTreeHouse(input)
        when:
        def result = treetopTreeHouse.countVisibleFromEdge()
        then:
        result == 21
    }

    def 'result'() {
        given:
        List<String> input = new FileInput('day8.txt').get()
        TreetopTreeHouse treetopTreeHouse = new TreetopTreeHouse(input)
        when:
        def result = treetopTreeHouse.countVisibleFromEdge()
        then:
        result == 1669
    }

    def 'should calculate max viewing distance'() {
        given:
        List<String> input = [
                "30373",
                "25512",
                "65332",
                "33549",
                "35390"
        ]
        TreetopTreeHouse treetopTreeHouse = new TreetopTreeHouse(input)
        when:
        def result = treetopTreeHouse.findMaxViewingDistance()
        then:
        result == 8
    }

    def 'result 2'() {
        given:
        List<String> input = new FileInput('day8.txt').get()
        TreetopTreeHouse treetopTreeHouse = new TreetopTreeHouse(input)
        when:
        def result = treetopTreeHouse.findMaxViewingDistance()
        then:
        result == 331344
    }

}
