package pl.gozdzikowski.pawel.adventofcode.day8;

import java.util.LinkedList;
import java.util.List;

public class TreetopTreeHouse {

    private int[][] treeMap;

    public TreetopTreeHouse(List<String> input) {
        treeMap = new int[input.size()][];
        for (int i = 0; i < input.size(); ++i) {
            String[] row = input.get(i).split("");
            treeMap[i] = new int[row.length];
            for (int j = 0; j < row.length; ++j) {
                treeMap[i][j] = Integer.parseInt(row[j]);
            }
        }
    }

    public int countVisibleFromEdge() {
        int visibleFromEdge = 0;
        for (int y = 0; y < treeMap.length; ++y) {
            for (int x = 0; x < treeMap[y].length; ++x) {
                int currentTreeHigh = treeMap[y][x];
                if (goesToEdge(currentTreeHigh, y, x))
                    visibleFromEdge++;
            }
        }
        return visibleFromEdge;
    }

    public int findMaxViewingDistance() {
        List<Integer> viewingDistances = new LinkedList<>();
        for (int y = 0; y < treeMap.length; ++y) {
            for (int x = 0; x < treeMap[y].length; ++x) {
                int currentTreeHigh = treeMap[y][x];
                viewingDistances.add(calculateViewingDistance(currentTreeHigh, y, x));
            }
        }
        return viewingDistances.stream()
                .max(Integer::compareTo).get();
    }

    private Integer calculateViewingDistance(int currentTreeHigh, int y, int x) {
        int viewingLeft = 0;
        int viewingRight = 0;
        int viewingTop = 0;
        int viewingBottom = 0;

        for (int direction = x - 1; direction >= 0; --direction) {
            viewingLeft++;
            if (treeMap[y][direction] >= currentTreeHigh) {
                break;
            }
        }

        for (int direction = x + 1; direction < treeMap[y].length; ++direction) {
            viewingRight++;
            if (treeMap[y][direction] >= currentTreeHigh) {
                break;
            }
        }

        for (int direction = y - 1; direction >= 0; --direction) {
            viewingTop++;
            if (treeMap[direction][x] >= currentTreeHigh) {
                break;
            }
        }

        for (int direction = y + 1; direction < treeMap.length; ++direction) {
            viewingBottom++;
            if (treeMap[direction][x] >= currentTreeHigh) {
                break;
            }
        }

        return viewingLeft*viewingRight*viewingBottom*viewingTop;
    }


    private boolean goesToEdge(int currentTreeHigh, int y, int x) {
        boolean goesToLeftEdge = true;
        boolean goesToRightEdge = true;
        boolean goesToTop = true;
        boolean goesToBottom = true;

        for (int direction = x - 1; direction >= 0; --direction) {
            if (treeMap[y][direction] >= currentTreeHigh) {
                goesToLeftEdge = false;
                break;
            }
        }

        for (int direction = x + 1; direction < treeMap[y].length; ++direction) {
            if (treeMap[y][direction] >= currentTreeHigh) {
                goesToRightEdge = false;
                break;
            }
        }

        for (int direction = y - 1; direction >= 0; --direction) {
            if (treeMap[direction][x] >= currentTreeHigh) {
                goesToTop = false;
                break;
            }
        }

        for (int direction = y + 1; direction < treeMap.length; ++direction) {
            if (treeMap[direction][x] >= currentTreeHigh) {
                goesToBottom = false;
                break;
            }
        }

        return goesToLeftEdge || goesToRightEdge || goesToTop || goesToBottom;
    }

}
