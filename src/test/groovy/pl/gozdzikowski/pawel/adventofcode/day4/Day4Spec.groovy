package pl.gozdzikowski.pawel.adventofcode.day4

import pl.gozdzikowski.pawel.adventofcode.input.FileInput
import pl.gozdzikowski.pawel.adventofcode.input.Input
import pl.gozdzikowski.pawel.adventofcode.input.ListInput
import spock.lang.Specification

class Day4Spec extends Specification {

    CampCleanup campCleanup = new CampCleanup()

    def 'should return overlapping pair'() {
        given:
        Input input = new ListInput([
                "2-4,6-8",
                "2-3,4-5",
                "5-7,7-9",
                "2-8,3-7",
                "6-6,4-6",
                "2-6,4-8"
        ])
        when:
        def count = campCleanup.countPairsWhichFullyOverlaps(input)
        then:
        count == 2
    }

    def 'solution part'() {
        given:
        Input input = new FileInput('day4.txt')
        when:
        def count = campCleanup.countPairsWhichFullyOverlaps(input)
        then:
        count == 413
    }

    def 'should return total overlap'() {
        given:
        Input input = new ListInput([
                "2-4,6-8",
                "2-3,4-5",
                "5-7,7-9",
                "2-8,3-7",
                "6-6,4-6",
                "2-6,4-8"
        ])
        when:
        def count = campCleanup.countOverlappingPairsPartially(input)
        then:
        count == 4
    }

    def 'solution part 2'() {
        given:
        Input input = new FileInput('day4.txt')
        when:
        def count = campCleanup.countOverlappingPairsPartially(input)
        then:
        count == 806
    }

}
