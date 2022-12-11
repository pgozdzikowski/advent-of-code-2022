package pl.gozdzikowski.pawel.adventofcode.day11

import spock.lang.Shared
import spock.lang.Specification

import static pl.gozdzikowski.pawel.adventofcode.day11.MonkeyInTheMiddle.*

class Day11Spec extends Specification {

    @Shared
    def sampleConfig = [
            new Monkey(
                    [79L, 98L],
                    { old -> old * 19 },
                    { calc -> isDivisible(calc as long, 23) ? 2 : 3 }
            ),
            new Monkey(
                    [54L, 65L, 75L, 74L],
                    { old -> old + 6 },
                    { calc -> isDivisible(calc as long, 19) ? 2 : 0 }
            ),
            new Monkey(
                    [79L, 60L, 97L],
                    { old -> old * old },
                    { calc -> isDivisible(calc as long, 13) ? 1 : 3 }
            ),
            new Monkey(
                    [74L],
                    { old -> old + 3 },
                    { calc -> isDivisible(calc as long, 17) ? 0 : 1 }
            )

    ]

    @Shared
    def taskConfig = [
            new Monkey(
                    [75L, 63L],
                    { old -> old * 3 },
                    { calc -> isDivisible(calc as long, 11) ? 7 : 2 }
            ),
            new Monkey(
                    [65L, 79L, 98L, 77L, 56L, 54L, 83L, 94L],
                    { old -> old + 3 },
                    { calc -> isDivisible(calc as long, 2) ? 2 : 0 }
            ),
            new Monkey(
                    [66L],
                    { old -> old + 5 },
                    { calc -> isDivisible(calc as long, 5) ? 7 : 5 }
            ),
            new Monkey(
                    [51L, 89L, 90L],
                    { old -> old * 19 },
                    { calc -> isDivisible(calc as long, 7) ? 6 : 4 }
            ),
            new Monkey(
                    [75L, 94L, 66L, 90L, 77L, 82L, 61L],
                    { old -> old + 1 },
                    { calc -> isDivisible(calc as long, 17) ? 6 : 1 }
            ),
            new Monkey(
                    [53L, 76L, 59L, 92L, 95L],
                    { old -> old + 2 },
                    { calc -> isDivisible(calc as long, 19) ? 4 : 3 }
            ),
            new Monkey(
                    [81L, 61L, 75L, 89L, 70L, 92L],
                    { old -> old * old },
                    { calc -> isDivisible(calc as long, 3) ? 0 : 1 }
            ),
            new Monkey(
                    [81L, 86L, 62L, 87L],
                    { old -> old + 8 },
                    { calc -> isDivisible(calc as long, 13) ? 3 : 5 }
            ),
    ]

    def 'should simulate and find product of multiply of most active monkeys'() {
        given:
        MonkeyInTheMiddle monkeyInTheMiddle = new MonkeyInTheMiddle(config);
        expect:
        result == monkeyInTheMiddle.findProductOfMultiplyOfMostActiveMonkeys(20, { (long) (it / 3) })
        where:
        config       || result
        sampleConfig || 10605L
        taskConfig   || 62491
    }

    def 'should simulate'() {
        given:
        MonkeyInTheMiddle monkeyInTheMiddle = new MonkeyInTheMiddle(config);
        expect:
        result == monkeyInTheMiddle.findProductOfMultiplyOfMostActiveMonkeys(10000, {it})
        where:
        config       || result
        sampleConfig || 10605L
    }
}
