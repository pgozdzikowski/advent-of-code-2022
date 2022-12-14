package pl.gozdzikowski.pawel.adventofcode.day13;

import java.util.List;
import java.util.Objects;

public class DistressSignal {


    public long calculateInRightOrder(List<SignalPair> signalPairs) {
        return signalPairs.stream()
                .filter(SignalPair::isInRightOrder)
                .map((signalPair -> signalPairs.indexOf(signalPair) + 1))
                .mapToLong(Long::valueOf)
                .sum();
    }

    record SignalPair(
            Holder left,
            Holder right
    ) {
        boolean isInRightOrder() {
            return isInRightOrder(left, right);
        }

        boolean isInRightOrder(Holder left, Holder right) {
            if (left instanceof IntegerHolder leftInteger) {
                if (right instanceof IntegerHolder rightInteger) {
                    return leftInteger.getValue() < rightInteger.getValue();
                } else {
                    return isInRightOrder(new ListHolder(List.of(left)), right);
                }
            } else if (left instanceof ListHolder leftListHolder) {
                if (right instanceof IntegerHolder) {
                    return isInRightOrder(left, new ListHolder(List.of(right)));
                } else {
                    ListHolder rightListHolder = (ListHolder) right;
                    int iterator;
                    for (iterator = 0; iterator < Math.min(leftListHolder.getValues().size(), rightListHolder.getValues().size()); ++iterator) {
                        if (leftListHolder.getValues().get(iterator) instanceof IntegerHolder leftEl && rightListHolder.getValues().get(iterator) instanceof IntegerHolder rightEl
                                && leftEl.equals(rightEl))
                            continue;
                        return isInRightOrder(leftListHolder.getValues().get(iterator), rightListHolder.getValues().get(iterator));
                    }
                    return iterator == leftListHolder.getValues().size();
                }
            }
            throw new IllegalStateException("Should not be here");
        }
    }


    static sealed class Holder permits IntegerHolder, ListHolder {
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


}
