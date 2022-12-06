package pl.gozdzikowski.pawel.adventofcode.day5

import pl.gozdzikowski.pawel.adventofcode.shared.input.FileInput
import pl.gozdzikowski.pawel.adventofcode.shared.input.Input
import spock.lang.Specification

class Day5Spec extends Specification {

    def SUPPLY_STACK_DEF = [
            ['Z', 'N'] as Stack<String>,
            ['M', 'C', 'D'] as Stack<String>,
            ['P'] as Stack<String>
    ]

    def MOVES = """move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2
"""


    def 'should create message from test supply stacks with change order'() {
        given:
        SupplyStacks supplyStacks = new SupplyStacks(SUPPLY_STACK_DEF)
        when:
        def message = supplyStacks.getMessageFromStacksWithChangeOrder(MOVES)
        then:
        message == "CMZ"
    }

    def 'task solution 1'() {
        given:
        def supplyStacksDef = [
                ['F', 'C', 'J', 'P', 'H', 'T', 'W'] as Stack<String>,
                ['G', 'R', 'V', 'F', 'Z', 'J', 'B', 'H'] as Stack<String>,
                ['H', 'P', 'T', 'R'] as Stack<String>,
                ['Z', 'S', 'N', 'P', 'H', 'T'] as Stack<String>,
                ['N', 'V', 'F', 'Z', 'H', 'J', 'C', 'D'] as Stack<String>,
                ['P', 'M', 'G', 'F', 'W', 'D', 'Z'] as Stack<String>,
                ['M', 'V', 'Z', 'W', 'S', 'J', 'D', 'P'] as Stack<String>,
                ['N', 'D', 'S'] as Stack<String>,
                ['D', 'Z', 'S', 'F', 'M'] as Stack<String>
        ]

        SupplyStacks supplyStacks = new SupplyStacks(supplyStacksDef)
        Input input = new FileInput("day5.txt")
        def moves = input.getContent().split("\\n\\n")
        when:
        def message = supplyStacks.getMessageFromStacksWithChangeOrder(moves[1])
        then:
        message == "SPFMVDTZT"
    }

    def 'should create message from test supply stacks with take many at once'() {
        given:
        SupplyStacks supplyStacks = new SupplyStacks(SUPPLY_STACK_DEF)
        when:
        def message = supplyStacks.getMessageFromStacksChangeOrderWithLeaveOrder(MOVES)
        then:
        message == "MCD"
    }

    def 'task solution 2'() {
        given:
        def supplyStacksDef = [
                ['F', 'C', 'J', 'P', 'H', 'T', 'W'] as Stack<String>,
                ['G', 'R', 'V', 'F', 'Z', 'J', 'B', 'H'] as Stack<String>,
                ['H', 'P', 'T', 'R'] as Stack<String>,
                ['Z', 'S', 'N', 'P', 'H', 'T'] as Stack<String>,
                ['N', 'V', 'F', 'Z', 'H', 'J', 'C', 'D'] as Stack<String>,
                ['P', 'M', 'G', 'F', 'W', 'D', 'Z'] as Stack<String>,
                ['M', 'V', 'Z', 'W', 'S', 'J', 'D', 'P'] as Stack<String>,
                ['N', 'D', 'S'] as Stack<String>,
                ['D', 'Z', 'S', 'F', 'M'] as Stack<String>
        ]

        SupplyStacks supplyStacks = new SupplyStacks(supplyStacksDef)
        Input input = new FileInput("day5.txt")
        def moves = input.getContent().split("\\n\\n")
        when:
        def message = supplyStacks.getMessageFromStacksChangeOrderWithLeaveOrder(moves[1])
        then:
        message == "ZFSJBPRFP"
    }

}
