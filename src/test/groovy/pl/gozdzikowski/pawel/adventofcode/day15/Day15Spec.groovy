package pl.gozdzikowski.pawel.adventofcode.day15

import pl.gozdzikowski.pawel.adventofcode.shared.input.FileInput
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input
import spock.lang.Specification

class Day15Spec extends Specification {

    BeaconExclusionZone beaconExclusionZone = new BeaconExclusionZone()

    def 'should calculate beacons possibly not present at row 10'() {
        given:
        Input input = new FileInput('day15sample.txt')
        expect:
        26L == beaconExclusionZone.countBeaconsWhichArePossiblyOnRow(input.get(), 10)
    }

    def 'task results'() {
        given:
        Input input = new FileInput('day15.txt')
        expect:
        5688618L == beaconExclusionZone.countBeaconsWhichArePossiblyOnRow(input.get(), 2000000)
    }

    def 'part 2'() {
        given:
        Input input = new FileInput('day15sample.txt')
        expect:
        56000011L == beaconExclusionZone.calculateTuningFrequency(input.get(), 20)
    }

    def 'part 2 results'() {
        given:
        Input input = new FileInput('day15.txt')
        expect:
        56000011L == beaconExclusionZone.calculateTuningFrequency(input.get(), 4000_000)
    }
}
