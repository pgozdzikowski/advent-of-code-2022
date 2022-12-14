package pl.gozdzikowski.pawel.adventofcode.day13;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static pl.gozdzikowski.pawel.adventofcode.day13.DistressSignal.ComparisionResult.*;
import static pl.gozdzikowski.pawel.adventofcode.day13.DistressSignal.ComparisionResult.INORDER;

public class DistressSignal {
    public static final Holder FIRST_DIVIDER = new ListHolder(List.of(new ListHolder(List.of(new IntegerHolder(2)))));
    public static final Holder SECOND_DIVIDER = new ListHolder(List.of(new ListHolder(List.of(new IntegerHolder(6)))));

    public long calculateInRightOrder(List<SignalPair> signalPairs) {
        return signalPairs.stream()
                .filter(SignalPair::isInRightOrder)
                .map((signalPair -> signalPairs.indexOf(signalPair) + 1))
                .mapToLong(Long::valueOf)
                .sum();
    }

    public long calculateIndexOfSortedDividerPacketsInRightOrder(List<SignalPair> signals, List<Holder> dividers) {
        List<Holder> holders = Stream.concat(signals.stream()
                                .flatMap((pair) -> Stream.of(pair.left, pair.right)),
                        dividers.stream()
                )
                .sorted(Comparator.reverseOrder())
                .toList();

        return (long) (holders.indexOf(FIRST_DIVIDER) + 1) * (holders.indexOf(SECOND_DIVIDER) + 1);
    }

    record SignalPair(
            Holder left,
            Holder right
    ) {
        boolean isInRightOrder() {
            return isInRightOrder(left, right) == INORDER;
        }

        ComparisionResult isInRightOrder(Holder left, Holder right) {
            if (left instanceof IntegerHolder leftInteger) {
                if (right instanceof IntegerHolder rightInteger) {
                    if (leftInteger.getValue() < rightInteger.getValue()) {
                        return INORDER;
                    } else if (leftInteger.getValue().equals(rightInteger.getValue())) {
                        return CONTINUE;
                    } else {
                        return NOTINORDER;
                    }
                } else {
                    return isInRightOrder(new ListHolder(List.of(left)), right);
                }
            } else if (left instanceof ListHolder leftListHolder) {
                if (right instanceof IntegerHolder) {
                    return isInRightOrder(left, new ListHolder(List.of(right)));
                } else {
                    ListHolder rightListHolder = (ListHolder) right;
                    int iterator;
                    int listSize = Math.min(leftListHolder.getValues().size(), rightListHolder.getValues().size());
                    for (iterator = 0; iterator < listSize; ++iterator) {
                        ComparisionResult comparisionResult = isInRightOrder(
                                leftListHolder.getValues().get(iterator),
                                rightListHolder.getValues().get(iterator)
                        );

                        if (comparisionResult == CONTINUE)
                            continue;

                        return comparisionResult;
                    }

                    if (leftListHolder.getValues().size() == rightListHolder.getValues().size())
                        return CONTINUE;

                    return iterator == leftListHolder.getValues().size() ? INORDER : NOTINORDER;
                }
            }
            return INORDER;
        }
    }


    static sealed class Holder implements Comparable<Holder> permits IntegerHolder, ListHolder {
        @Override
        public int compareTo(Holder other) {
            if(this == other)
                return 0;

            SignalPair signalPair = new SignalPair(this, other);

            return signalPair.isInRightOrder() ? 1 : -1;
        }
    }

    static final class IntegerHolder extends Holder {
        Integer value;

        public IntegerHolder(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            IntegerHolder that = (IntegerHolder) o;
            return value.equals(that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }

    static final class ListHolder extends Holder {
        List<Holder> values;

        public ListHolder(List<Holder> values) {
            this.values = values;
        }

        public List<Holder> getValues() {
            return values;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ListHolder that = (ListHolder) o;
            return Objects.equals(values, that.values);
        }

        @Override
        public int hashCode() {
            return Objects.hash(values);
        }

        @Override
        public String toString() {
            return values.toString().replaceAll(" ", "");
        }

    }

    enum ComparisionResult {
        INORDER, NOTINORDER, CONTINUE
    }
}
