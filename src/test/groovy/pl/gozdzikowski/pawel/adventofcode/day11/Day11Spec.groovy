package pl.gozdzikowski.pawel.adventofcode.day11

import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Specification

import static pl.gozdzikowski.pawel.adventofcode.day11.MonkeyInTheMiddle.*

class Day11Spec extends Specification {

    @Shared
    def sampleConfig = [
            new Monkey(
                    new MonkeyOperation(19, ArithmeticOperation.MUL),
                    { calc -> isDivisible(calc, 23) ? 2 : 3 }
            ),
            new Monkey(
                    new MonkeyInTheMiddle.MonkeyOperation(6, ArithmeticOperation.ADD),
                    { calc -> isDivisible(calc, 19) ? 2 : 0 }
            ),
            new Monkey(
                    new MonkeyOperation(0, ArithmeticOperation.POW),
                    { calc -> isDivisible(calc, 13) ? 1 : 3 }
            ),
            new Monkey(
                    new MonkeyInTheMiddle.MonkeyOperation(3, ArithmeticOperation.ADD),
                    { calc -> isDivisible(calc, 17) ? 0 : 1 }
            )

    ]

    Map<Integer, List<Long>> initialStateOfSample = [
            0: [79L, 98L],
            1: [54L, 65L, 75L, 74L],
            2: [79L, 60L, 97L],
            3: [74L]
    ]

    def taskConfig = [
            new Monkey(
                    new MonkeyInTheMiddle.MonkeyOperation(3, ArithmeticOperation.MUL),
                    { calc -> isDivisible(calc , 11) ? 7 : 2 }
            ),
            new Monkey(
                    new MonkeyInTheMiddle.MonkeyOperation(3,ArithmeticOperation.ADD),
                    { calc -> isDivisible(calc, 2) ? 2 : 0 }
            ),
            new Monkey(
                    new MonkeyInTheMiddle.MonkeyOperation(5, ArithmeticOperation.ADD),
                    { calc -> isDivisible(calc, 5) ? 7 : 5 }
            ),
            new Monkey(
                    new MonkeyInTheMiddle.MonkeyOperation(19, ArithmeticOperation.MUL),
                    { calc -> isDivisible(calc, 7) ? 6 : 4 }
            ),
            new Monkey(
                    new MonkeyInTheMiddle.MonkeyOperation(1, ArithmeticOperation.ADD),
                    { calc -> isDivisible(calc, 17) ? 6 : 1 }
            ),
            new Monkey(
                    new MonkeyInTheMiddle.MonkeyOperation(2, ArithmeticOperation.ADD),
                    { calc -> isDivisible(calc, 19) ? 4 : 3 }
            ),
            new Monkey(

                    new MonkeyInTheMiddle.MonkeyOperation(0, ArithmeticOperation.POW),
                    { calc -> isDivisible(calc, 3) ? 0 : 1 }
            ),
            new Monkey(
                    new MonkeyOperation(8, ArithmeticOperation.ADD),
                    { calc -> isDivisible(calc, 13) ? 3 : 5 }
            ),
    ]

    def initialStateOfMonkeyTask = [
            0 : [75L, 63L],
            1: [65L, 79L, 98L, 77L, 56L, 54L, 83L, 94L] ,
            2: [66L],
            3:  [51L, 89L, 90L],
            4:  [75L, 94L, 66L, 90L, 77L, 82L, 61L],
            5:  [53L, 76L, 59L, 92L, 95L],
            6:  [81L, 61L, 75L, 89L, 70L, 92L],
            7:  [81L, 86L, 62L, 87L],
    ]

    def 'should simulate and find product of multiply of most active monkeys'() {
        given:
        MonkeyInTheMiddle monkeyInTheMiddle = new MonkeyInTheMiddle(sampleConfig as Monkey[])
        expect:
        result == monkeyInTheMiddle.findProductOfMultiplyOfMostActiveMonkeys(iterations, initialStateOfSample)
        where:
        result         || iterations
        99L * 103      || 20
        5204L * 5192   || 1000
        52166L * 52013 || 10000
    }


    @Ignore
    def 'task results'() {
        given:
        MonkeyInTheMiddle monkeyInTheMiddle = new MonkeyInTheMiddle(taskConfig as Monkey[])
        expect:
        result == monkeyInTheMiddle.findProductOfMultiplyOfMostActiveMonkeys(iterations, initialStateOfMonkeyTask)
        where:
        result         || iterations
        2713310158 || 10000
    }
}
