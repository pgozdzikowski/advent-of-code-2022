package pl.gozdzikowski.pawel.adventofcode.day11;

import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;

public class MonkeyInTheMiddlePart2 {

    private Monkey[] monkeys;

    public MonkeyInTheMiddlePart2(Monkey[] monkeys) {
        this.monkeys = monkeys;
    }

    public long findProductOfMultiplyOfMostActiveMonkeys(int iterations, Map<Integer, List<Long>> initialState) {
        List<Item> items = initialState.entrySet().stream().flatMap(
                (entry) -> entry.getValue().stream().map((itemValue) -> new Item(itemValue, entry.getKey(), monkeys.length))
        ).toList();

        for (int i = 0; i < items.size(); ++i) {
            System.out.println("Execute operations fro item" +i);
            items.get(i).executeIterations(iterations, monkeys);
        }

        long[] summedPosses = items.stream()
                .map(Item::getHowManyByMonkey)
                .reduce(new long[monkeys.length], this::reduceItems);

        return Arrays.stream(summedPosses)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .limit(2)
                .mapToLong(Long::valueOf)
                .reduce(1, (acc, item) -> acc * item);
    }

    private long[] reduceItems(long[] acc, long[] possessed) {
        for (int i = 0; i < acc.length; ++i) {
            acc[i] += possessed[i];
        }
        return acc;
    }

    static class Monkey {
        private MonkeyOperation operation;
        private Function<Item, Integer> forwardCondtion;

        public Monkey(MonkeyOperation operation, Function<Item, Integer> forwardCondition) {
            this.operation = operation;
            this.forwardCondtion = forwardCondition;
        }



        public Function<Item, Integer> getForwardCondtion() {
            return forwardCondtion;
        }
    }

    class Item {
        private long[] howManyByMonkey;
        private int currentMonkey;

        private List<MonkeyOperation> monkeyOperations = new LinkedList<>();

        public Item(Long item, int currentMonkey,  int sizeOfMonkeys) {
            this.currentMonkey = currentMonkey;
            this.howManyByMonkey = new long[sizeOfMonkeys];
            monkeyOperations.add(new MonkeyOperation(item, ArithmeticOperation.NOOP));
        }

        void executeIterations(int iterations, Monkey[] monkeys) {
            while (iterations > 0) {
                int prevMonkey = currentMonkey;
                while(currentMonkey >= prevMonkey) {
                    howManyByMonkey[currentMonkey]++;
                    Monkey monkey = monkeys[currentMonkey];
                    monkeyOperations.add(monkey.operation);
                    prevMonkey = currentMonkey;
                    currentMonkey = monkey.getForwardCondtion().apply(this);
                }
                --iterations;
            }
        }

        public long[] getHowManyByMonkey() {
            return howManyByMonkey;
        }
    }

    record MonkeyOperation(
            long secondOperand,
            ArithmeticOperation operation
    ) {}

    enum ArithmeticOperation {
        ADD, MUL, POW, NOOP
    }

    public static boolean isDivisible(Item item, int by) {
        BigInteger result = BigInteger.ZERO;
        for (MonkeyOperation monkeyOperation : item.monkeyOperations) {
            switch (monkeyOperation.operation) {
                case MUL -> result = result.multiply(BigInteger.valueOf(monkeyOperation.secondOperand() % by));
                case ADD -> result = result.add(BigInteger.valueOf(monkeyOperation.secondOperand() % by));
                case POW -> result = (result.multiply(result.remainder(BigInteger.valueOf(by))));
                case NOOP -> result = BigInteger.valueOf(monkeyOperation.secondOperand() % by);
            }
        }
        return result.remainder(BigInteger.valueOf(by)).compareTo(BigInteger.ZERO) == 0;
    }
}
