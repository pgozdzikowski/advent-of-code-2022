package pl.gozdzikowski.pawel.adventofcode.input;

import java.io.IOException;
import java.util.List;

public class ListInput implements Input {
    List<String> inputs;
    public ListInput(List<String> inputs) {
        this.inputs = inputs;
    }
    @Override
    public List<String> get() {
        return inputs;
    }

    @Override
    public String getContent() {
        throw new UnsupportedOperationException("Unsuported operations");
    }
}
