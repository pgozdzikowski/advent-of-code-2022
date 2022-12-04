package pl.gozdzikowski.pawel.adventofcode.day4;

import pl.gozdzikowski.pawel.adventofcode.shared.input.Input;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;

public class CampCleanup {

    Pattern pairRegexp = Pattern.compile("(\\d+)-(\\d+),(\\d+)-(\\d+)");

    public long countPairsWhichFullyOverlaps(Input input) {
        return convertInputToPairs(input)
                .filter(ElfPair::overlapInPair)
                .count();
    }

    public long countOverlappingPairsPartially(Input input) {
        return convertInputToPairs(input)
                .filter(ElfPair::overlapPartially)
                .count();
    }

    private Stream<ElfPair> convertInputToPairs(Input input) {
        return input.get()
                .stream()
                .map(this::convertToElfPair);
    }

    private ElfPair convertToElfPair(String line) {
        Matcher matcher = pairRegexp.matcher(line);

        if(!matcher.find())
            throw new IllegalArgumentException(String.format("Illegal format of line %s", line));

        JobRange leftJobRange = new JobRange(parseInt(matcher.group(1)), parseInt(matcher.group(2)));
        JobRange rightJobRange = new JobRange(parseInt(matcher.group(3)), parseInt(matcher.group(4)));

        return ElfPair.of(leftJobRange, rightJobRange);
    }

    record ElfPair(
            JobRange left,
            JobRange right
    ) {


        public boolean overlapInPair() {
            return left.overlapFully(right) || right.overlapFully(left);
        }

        public boolean overlapPartially() {
            return left.overlapPartially(right);
        }

        public static ElfPair of(JobRange left, JobRange right) {
            return new ElfPair(left, right);
        }

    }

    record JobRange(int start, int end) {
        public boolean overlapFully(JobRange other) {
            return this.start <= other.start && this.end >= other.end;
        }

        public boolean overlapPartially(JobRange other) {
            return !(other.start > end || this.start > other.end);
        }
    }

}
