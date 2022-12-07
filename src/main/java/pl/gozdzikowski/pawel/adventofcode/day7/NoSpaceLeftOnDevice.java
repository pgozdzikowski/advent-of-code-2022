package pl.gozdzikowski.pawel.adventofcode.day7;

import pl.gozdzikowski.pawel.adventofcode.day7.ElvesFileSystem.ElvesDeviceDirectory;
import pl.gozdzikowski.pawel.adventofcode.day7.ElvesFileSystem.ElvesDeviceFile;
import pl.gozdzikowski.pawel.adventofcode.day7.ElvesFileSystem.ElvesFileSystemObject;
import pl.gozdzikowski.pawel.adventofcode.shared.collections.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NoSpaceLeftOnDevice {

    private static Pattern commandPattern = Pattern.compile("\\$ (ls|cd)\\s*(.*)");
    private static Pattern lsOutputPattern = Pattern.compile("(dir|\\d+) (.+)");

    public Long calculateSumDirectoriesLowerThan(List<String> input, long size) {
        ElvesFileSystemObject root = createFileSystemTree(input);
        Map<String, Long> directoryPathToSize = new HashMap<>();
        walkThroughFilesystem(root, directoryPathToSize);
        return directoryPathToSize.values()
                .stream()
                .filter(directorySize -> directorySize <= size)
                .mapToLong(Long::longValue)
                .sum();
    }

    public Long findDirectorySizeClosestToNeededSpace(List<String> input, long neededSpace, long discSize) {
        ElvesFileSystemObject root = createFileSystemTree(input);
        Map<String, Long> directoryPathToSize = new HashMap<>();
        long neededToReclaim = neededSpace - (discSize - walkThroughFilesystem(root, directoryPathToSize));
        return directoryPathToSize.values()
                .stream()
                .filter(directorySize -> directorySize >= neededToReclaim)
                .sorted()
                .findFirst()
                .get();
    }

    private Long walkThroughFilesystem(ElvesFileSystemObject currDir, Map<String, Long> directoryPathToSize) {
        Long dirSize = 0L;
        if (currDir instanceof ElvesDeviceDirectory d) {
            for (ElvesFileSystemObject o : d.getDirectories()) {
                dirSize += walkThroughFilesystem(o, directoryPathToSize);
            }
            directoryPathToSize.put(d.getAbsolutePath(), dirSize);
        } else if (currDir instanceof ElvesDeviceFile f) {
            return f.getSize();
        } else {
            throw new IllegalStateException("Not known file object type");
        }

        return dirSize;
    }

    private ElvesFileSystemObject createFileSystemTree(List<String> input) {
        int currentLineNum = 0;

        ElvesFileSystemObject root = new ElvesDeviceDirectory("/");
        ElvesFileSystemObject currentDirectory = root;
        while (currentLineNum < input.size()) {
            String line = input.get(currentLineNum);
            Pair<Integer, ElvesFileSystemObject> commandResult = executeCommand(currentDirectory, root, line, input, currentLineNum + 1);
            currentLineNum = commandResult.left();
            currentDirectory = commandResult.right();
        }
        return root;
    }

    private boolean isCommand(String line) {
        return commandPattern.matcher(line).matches();
    }

    private Pair<Integer, ElvesFileSystemObject> executeCommand(ElvesFileSystemObject currentDirectory, ElvesFileSystemObject root, String line, List<String> input, int currentLineNum) {
        Matcher matcher = commandPattern.matcher(line);
        matcher.find();

        return switch (matcher.group(1)) {
            case "ls" -> parseAndCreateInFileSystemLsResults(currentDirectory, input, currentLineNum);
            case "cd" -> Pair.of(currentLineNum, executeCdCommand((ElvesDeviceDirectory) currentDirectory, root, matcher.group(2)));
            default -> throw new IllegalStateException("Unable to handle command " + line);
        };
    }

    private ElvesFileSystemObject executeCdCommand(
            ElvesDeviceDirectory currentDirectory,
            ElvesFileSystemObject root,
            String goTo
    ) {
        return switch (goTo) {
            case ".." -> currentDirectory.getParent();
            case "/" -> root;
            default -> currentDirectory.getDirectory(goTo).get();
        };
    }

    private Pair<Integer, ElvesFileSystemObject> parseAndCreateInFileSystemLsResults(
            ElvesFileSystemObject currentDirectory,
            List<String> inputs,
            int currentLineNum
    ) {
        do {
            String line = inputs.get(currentLineNum);
            if (isCommand(line))
                break;
            createFileSystemObject((ElvesDeviceDirectory) currentDirectory, line);
            currentLineNum++;
        } while (currentLineNum < inputs.size());
        return Pair.of(currentLineNum, currentDirectory);
    }

    private void createFileSystemObject(ElvesDeviceDirectory currentDirectory, String line) {
        Matcher matcher = lsOutputPattern.matcher(line);
        matcher.find();
        switch (matcher.group(1)) {
            case "dir" ->
                    currentDirectory.addFileSystemObject(new ElvesDeviceDirectory(matcher.group(2), currentDirectory));
            default ->
                    currentDirectory.addFileSystemObject(new ElvesDeviceFile(matcher.group(2), Long.valueOf(matcher.group(1))));
        }
    }

}
