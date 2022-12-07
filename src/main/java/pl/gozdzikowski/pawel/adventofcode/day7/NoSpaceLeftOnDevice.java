package pl.gozdzikowski.pawel.adventofcode.day7;

import pl.gozdzikowski.pawel.adventofcode.day7.ElfFileSystem.ElfDeviceDirectory;
import pl.gozdzikowski.pawel.adventofcode.day7.ElfFileSystem.ElfDeviceFile;
import pl.gozdzikowski.pawel.adventofcode.day7.ElfFileSystem.ElfFileSystemObject;
import pl.gozdzikowski.pawel.adventofcode.shared.collections.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NoSpaceLeftOnDevice {

    private static Pattern commandPattern = Pattern.compile("\\$ (ls|cd)\\s*(.*)");
    private static Pattern fileSystemPattern = Pattern.compile("(dir|\\d+) (.+)");

    public Long calculateSumDirectoriesLowerThan(List<String> input, long size) {
        ElfFileSystemObject root = createFileSystemTree(input);
        Map<String, Long> directoryPathToSize = new HashMap<>();
        walkThroughFilesystem(root, directoryPathToSize);
        return directoryPathToSize.values()
                .stream()
                .filter(directorySize -> directorySize <= size)
                .mapToLong(Long::longValue)
                .sum();
    }

    public Long findDirectorySizeClosestToNeededSpace(List<String> input, long neededSpace, long discSize) {
        ElfFileSystemObject root = createFileSystemTree(input);
        Map<String, Long> directoryPathToSize = new HashMap<>();
        long neededToReclaim = neededSpace - (discSize - walkThroughFilesystem(root, directoryPathToSize));
        return directoryPathToSize.values()
                .stream()
                .filter(directorySize -> directorySize >= neededToReclaim)
                .sorted()
                .findFirst()
                .get();
    }

    private Long walkThroughFilesystem(ElfFileSystemObject currDir, Map<String, Long> directoryPathToSize) {
        Long dirSize = 0L;
        if (currDir instanceof ElfDeviceDirectory d) {
            for (ElfFileSystemObject o : d.getDirectories()) {
                dirSize += walkThroughFilesystem(o, directoryPathToSize);
            }
            directoryPathToSize.put(d.getAbsolutePath(), dirSize);
        } else if (currDir instanceof ElfDeviceFile f) {
            return f.getSize();
        } else {
            throw new IllegalStateException("Not known file object type");
        }

        return dirSize;
    }

    private ElfFileSystemObject createFileSystemTree(List<String> input) {
        int currentLineNum = 0;

        ElfFileSystemObject root = new ElfDeviceDirectory("/");
        ElfFileSystemObject currentDirectory = root;
        while (currentLineNum < input.size()) {
            String line = input.get(currentLineNum);
            Pair<Integer, ElfFileSystemObject> commandResult = executeCommand(currentDirectory, root, line, input, currentLineNum + 1);
            currentLineNum = commandResult.left();
            currentDirectory = commandResult.right();
        }
        return root;
    }

    private boolean isCommand(String line) {
        return commandPattern.matcher(line).matches();
    }

    private Pair<Integer, ElfFileSystemObject> executeCommand(ElfFileSystemObject currentDirectory, ElfFileSystemObject root, String line, List<String> input, int currentLineNum) {
        Matcher matcher = commandPattern.matcher(line);
        matcher.find();

        return switch (matcher.group(1)) {
            case "ls" -> parseAndCreateInFileSystemLsResults(currentDirectory, input, currentLineNum);
            case "cd" -> Pair.of(currentLineNum, executeCdCommand((ElfDeviceDirectory) currentDirectory, root, matcher.group(2)));
            default -> throw new IllegalStateException("Unable to handle command " + line);
        };
    }

    private ElfFileSystemObject executeCdCommand(
            ElfDeviceDirectory currentDirectory,
            ElfFileSystemObject root,
            String goTo
    ) {
        return switch (goTo) {
            case ".." -> currentDirectory.getParent();
            case "/" -> root;
            default -> currentDirectory.getDirectory(goTo).get();
        };
    }

    private Pair<Integer, ElfFileSystemObject> parseAndCreateInFileSystemLsResults(
            ElfFileSystemObject currentDirectory,
            List<String> inputs,
            int currentLineNum
    ) {
        do {
            String line = inputs.get(currentLineNum);
            if (isCommand(line))
                break;
            createFileSystemObject((ElfDeviceDirectory) currentDirectory, line);
            currentLineNum++;
        } while (currentLineNum < inputs.size());
        return Pair.of(currentLineNum, currentDirectory);
    }

    private void createFileSystemObject(ElfDeviceDirectory currentDirectory, String line) {
        Matcher matcher = fileSystemPattern.matcher(line);
        matcher.find();
        switch (matcher.group(1)) {
            case "dir" ->
                    currentDirectory.addFileSystemObject(new ElfDeviceDirectory(matcher.group(2), currentDirectory));
            default ->
                    currentDirectory.addFileSystemObject(new ElfDeviceFile(matcher.group(2), Long.valueOf(matcher.group(1))));
        }
    }

}
