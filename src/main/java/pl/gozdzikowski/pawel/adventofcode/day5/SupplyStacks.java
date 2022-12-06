package pl.gozdzikowski.pawel.adventofcode.day5;

import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SupplyStacks {
    private static Pattern movePattern = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");
    private List<Stack<String>> supplyStacks;

    public SupplyStacks(List<Stack<String>> supplyStacks) {
        this.supplyStacks = supplyStacks;
    }

    public String getMessageFromStacksWithChangeOrder(String moves) {
        return convertToActionAndExecute(moves, this::onlyOneItemAtOnce);
    }

    public String getMessageFromStacksChangeOrderWithLeaveOrder(String moves) {
        return convertToActionAndExecute(moves, this::manyItemsAtOnce);
    }

    private String convertToActionAndExecute(String moves, Consumer<ActionDescription> actionConsumer) {
        String[] movesArray = moves.split("\\n");

        Arrays.stream(movesArray).forEach((move) -> executeMove(move, actionConsumer));

        return formatMessageFromStacks();
    }

    private void executeMove(String move, Consumer<ActionDescription> consumer) {
        Matcher matcher = movePattern.matcher(move);
        matcher.find();
        int numOfItems = Integer.parseInt(matcher.group(1));
        int sourceStack = Integer.parseInt(matcher.group(2)) - 1;
        int destStack = Integer.parseInt(matcher.group(3)) - 1;
        consumer.accept(new ActionDescription(numOfItems, sourceStack, destStack));
    }

    void onlyOneItemAtOnce(ActionDescription action) {
        int itemsLefts = action.numOfItems;
        while(itemsLefts > 0) {
            String poppedCreate = supplyStacks.get(action.sourceStack()).pop();
            supplyStacks.get(action.destStack()).push(poppedCreate);
            itemsLefts--;
        }
    }

    void manyItemsAtOnce(ActionDescription action) {
        Deque<String> takenCrafts = new LinkedList<>();
        int itemsLeft = action.numOfItems;
        while(itemsLeft > 0) {
            String poppedCreate = supplyStacks.get(action.sourceStack()).pop();
            takenCrafts.offerFirst(poppedCreate);
            itemsLeft--;
        }
        supplyStacks.get(action.destStack()).addAll(takenCrafts);
    }

    private String formatMessageFromStacks() {
        return supplyStacks.stream()
                .filter((s) -> !s.isEmpty())
                .map(Stack::pop)
                .reduce("", (acc, next) -> acc + next);
    }

    record ActionDescription(
            int numOfItems,
            int sourceStack,
            int destStack
    ) {

    }
}
