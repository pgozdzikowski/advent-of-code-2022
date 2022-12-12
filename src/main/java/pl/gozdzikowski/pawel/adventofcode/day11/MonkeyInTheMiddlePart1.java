package pl.gozdzikowski.pawel.adventofcode.day11;

import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class MonkeyInTheMiddlePart1 {


    private List<Monkey> monkeys;

    public MonkeyInTheMiddlePart1(List<Monkey> monkeys) {
        this.monkeys = monkeys;
    }

    public long findProductOfMultiplyOfMostActiveMonkeys(int roundsNum, Function<Long, Long> worryFunction) {
        while (roundsNum > 0) {
            for (Monkey monkey : monkeys) {
                while (monkey.hasAnyItem()) {
                    ItemTransfer itemTransfer = monkey.takeOneItemAndThrow(worryFunction);
                    monkeys.get(itemTransfer.destMonkey).addItem(itemTransfer.worryLevel());
                }
            }
            roundsNum--;
        }
        return monkeys.stream().sorted(Comparator.comparing(Monkey::getNumberOfItemsInspected).reversed())
                .limit(2)
                .map(Monkey::getNumberOfItemsInspected)
                .reduce(1, (acc, item) -> acc * item);
    }

    static class Monkey {
        private Deque<Long> items;
        private Function<Long, Long> operation;
        private Function<Long, Integer> forwardCondtion;
        private int numberOfItemsInspected = 0;

        public Monkey(List<Long> items, Function<Long, Long> operation, Function<Long, Integer> forwardCondition) {
            this.items = new LinkedList<>(items);
            this.operation = operation;
            this.forwardCondtion = forwardCondition;
        }

        public boolean hasAnyItem() {
            return !items.isEmpty();
        }

        public ItemTransfer takeOneItemAndThrow(Function<Long, Long> worryFunction) {
            Long item = items.poll();
            Long worryLevelAfterApply = operation.apply(item);
            numberOfItemsInspected++;
            long worryLevelAfterDivision = worryFunction.apply(worryLevelAfterApply);
            return new ItemTransfer(worryLevelAfterDivision, forwardCondtion.apply(worryLevelAfterDivision));
        }

        public void addItem(Long item) {
            items.add(item);
        }

        public int getNumberOfItemsInspected() {
            return numberOfItemsInspected;
        }
    }

    record ItemTransfer(
            long worryLevel,
            int destMonkey
    ) {}

    public static boolean isDivisible(long num, int by) {
        return num % by == 0;
    }
}

