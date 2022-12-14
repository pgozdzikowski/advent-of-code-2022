package pl.gozdzikowski.pawel.adventofcode.day13;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import static pl.gozdzikowski.pawel.adventofcode.day13.DistressSignal.*;

public class DistressInputSignalParser {

    public List<SignalPair> parseInput(String text) {
        String[] pairs = text.split("\n\n");

        return Arrays.stream(pairs)
                .map(this::parseSinglePair)
                .toList();
    }

    private SignalPair parseSinglePair(String s) {
        String[] pairs = s.split("\n");
        return new SignalPair(parseSignal(pairs[0]), parseSignal(pairs[1]));
    }

    public Holder parseSignal(String signal) {
        char[] chars = signal.toCharArray();
        Stack<ListHolder> holders = new Stack<>();
        ListHolder head = null;
        StringBuilder currentDigit = new StringBuilder();
        for (int i = 0; i < chars.length; ++i) {
            if(chars[i] == '[') {
                holders.push(new ListHolder(new LinkedList<>()));
            } else if (Character.isDigit(chars[i])) {
                currentDigit.append(chars[i]);
            } else if (chars[i] == ',' && !currentDigit.isEmpty()) {
                holders.lastElement().getValues().add(new IntegerHolder(Integer.valueOf(currentDigit.toString())));
                currentDigit.setLength(0);
            } else if (chars[i] == ']') {
                if(!currentDigit.isEmpty()) {
                    holders.lastElement().getValues().add(new IntegerHolder(Integer.valueOf(currentDigit.toString())));
                    currentDigit.setLength(0);
                }
                head = holders.pop();
                if(!holders.isEmpty())
                    holders.lastElement().getValues().add(head);
            }
        }
        return head;
    }
}
