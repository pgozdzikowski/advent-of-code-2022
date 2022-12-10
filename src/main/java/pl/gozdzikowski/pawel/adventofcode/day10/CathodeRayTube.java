package pl.gozdzikowski.pawel.adventofcode.day10;

import pl.gozdzikowski.pawel.adventofcode.shared.collections.Pair;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CathodeRayTube {

    Pattern cpuCommandPattern = Pattern.compile("(addx|noop)\\s*(-?\\d+)*");

    public Long calculateForProbes(List<String> cpuInstruction, List<Integer> probesList) {
        List<Long> probes = calculateSignalStrength(cpuInstruction, probesList);
        return probesList
                .stream().map(
                        (probeNum) -> probes.get(probeNum - 2)
                )
                .mapToLong(Long::longValue)
                .sum();
    }

    public char[][] drawOnCrtScreen(List<String> cpuInstructions) {
        long currentRegisterValue = 1L;
        int currentInstruction = 0;
        int currentCpuCycle = 0;
        int screenWidth = 40;
        int screenHight = 6;
        char[][] crtScreen = new char[screenHight][screenWidth];
        boolean startedAddx = false;

        while (currentCpuCycle < screenHight * screenWidth && currentInstruction < cpuInstructions.size()) {
            String instruction = cpuInstructions.get(currentInstruction);
            Matcher matcher = cpuCommandPattern.matcher(instruction);
            matcher.find();
            Pair<Integer, Integer> point = drawingPosition(currentCpuCycle, screenWidth);
            crtScreen[point.right()][point.left()] = spritePosition((int)currentRegisterValue).contains(currentCpuCycle % screenWidth) ? '#' : '.';
            switch (matcher.group(1)) {
                case "noop" -> currentCpuCycle++;
                case "addx" -> {
                    if(!startedAddx) {
                        startedAddx = true;
                    } else {
                        startedAddx = false;
                        currentRegisterValue += Long.parseLong(matcher.group(2));
                    }
                    currentCpuCycle++;
                }
            }
            if(!startedAddx)
                currentInstruction++;
        }
        return crtScreen;
    }

    private List<Long> calculateSignalStrength(List<String> cpuInstructions, List<Integer> cycles) {
        long currentRegisterValue = 1L;
        int currentInstruction = 0;
        int currentCpuCycle = 1;
        int maxCycles = cycles.stream().max(Integer::compareTo).get();
        boolean startedAddx = false;
        List<Long> probes = new LinkedList<>();
        while (currentCpuCycle < maxCycles && currentInstruction < cpuInstructions.size()) {
            String instruction = cpuInstructions.get(currentInstruction);
            Matcher matcher = cpuCommandPattern.matcher(instruction);
            matcher.find();
            switch (matcher.group(1)) {
                case "noop" -> currentCpuCycle++;
                case "addx" -> {
                    if(!startedAddx) {
                        startedAddx = true;
                    } else {
                        startedAddx = false;
                        currentRegisterValue += Long.parseLong(matcher.group(2));
                    }
                    currentCpuCycle++;
                }
            }

            if(!startedAddx)
                currentInstruction++;

            probes.add(currentRegisterValue * currentCpuCycle);
        }
        return probes;
    }


    private List<Integer> spritePosition(Integer value) {
        return List.of(value - 1, value, value, value + 1);
    }

    private Pair<Integer, Integer> drawingPosition(Integer currentCpuInstruction, int screenWidth) {
        return new Pair(currentCpuInstruction % screenWidth, currentCpuInstruction / screenWidth);
    }
}
