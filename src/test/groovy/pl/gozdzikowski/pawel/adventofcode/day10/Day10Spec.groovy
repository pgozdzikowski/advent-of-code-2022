package pl.gozdzikowski.pawel.adventofcode.day10

import pl.gozdzikowski.pawel.adventofcode.shared.input.FileInput
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input
import spock.lang.Specification

class Day10Spec extends Specification {

    CathodeRayTube cathodeRayTube = new CathodeRayTube()

    def 'should calculate signal strength for sample at given #cycles'() {
        given:
        Input input = new FileInput('day10sample.txt')
        expect:
        cathodeRayTube.calculateForProbes(input.get(), cycles) == result
        where:
        cycles || result
        [20]     || 420
        [60]     || 1140
        [100]    || 1800
        [140]    || 2940
        [180]    || 2880
        [220]    || 3960
    }

    def 'should sum probes [20, 60, 100, 140, 180, 220]'() {
        given:
        Input input = new FileInput('day10sample.txt')
        expect:
        cathodeRayTube.calculateForProbes(input.get(), [20, 60, 100, 140, 180, 220]) == 13140
    }

    def 'task results '() {
        given:
        Input input = new FileInput('day10.txt')
        expect:
        cathodeRayTube.calculateForProbes(input.get(), [20, 60, 100, 140, 180, 220]) == 14420

    }

    def 'should print crt screen result'() {
        given:
        Input input = new FileInput('day10sample.txt')
        CrtPrinter.print(cathodeRayTube.drawOnCrtScreen(input.get()))
        expect:
        1 == 1
    }

    def 'should print crt screen result - task result'() {
        given:
        Input input = new FileInput('day10.txt')
        CrtPrinter.print(cathodeRayTube.drawOnCrtScreen(input.get()))
        expect:
        1 == 1
    }
}
