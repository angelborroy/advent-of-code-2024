package es.usj.day20;

import java.io.*;
import java.util.*;

public class MazeSolver2 {
    static int gridSize;
    static char[][] grid;
    static int[] start = new int[2];
    static int[] end = new int[2];
    static final int[][] DIRECTIONS = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    public static void main(String[] args) throws IOException {
        List<char[]> tempGrid = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                MazeSolver2.class.getClassLoader().getResourceAsStream("es/usj/day20/input.txt")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                tempGrid.add(line.toCharArray());
            }
        }
        grid = tempGrid.toArray(new char[0][]);
        gridSize = grid.length;

        // Locate start ('S') and end ('E') points in the grid
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (grid[i][j] == 'S') {
                    start[0] = i;
                    start[1] = j;
                } else if (grid[i][j] == 'E') {
                    end[0] = i;
                    end[1] = j;
                }
            }
        }

        // Compute the original path from start to end
        List<int[]> path = computePath();
        int originalPathLength = path.size() - 1;

        // Calculate times for each coordinate on the path
        Map<String, Integer> timeMap = computeTimeMap(path, originalPathLength);

        // Precompute saved calculations for possible transitions
        Map<String, Integer> savedCalculations = computeSavedCalculations(path, timeMap, originalPathLength);

        // Count results
        int countOverThreshold = countResults(savedCalculations);

        System.out.println(countOverThreshold);
    }

    static List<int[]> computePath() {
        List<int[]> path = new ArrayList<>();
        path.add(new int[]{start[0], start[1]});

        while (true) {
            int[] last = path.get(path.size() - 1);
            if (Arrays.equals(last, end)) break;

            boolean moved = false;
            for (int[] direction : DIRECTIONS) {
                int nextRow = last[0] + direction[0];
                int nextCol = last[1] + direction[1];

                if (isInGrid(nextRow, nextCol) && grid[nextRow][nextCol] != '#' &&
                        (path.size() < 2 || !Arrays.equals(new int[]{nextRow, nextCol}, path.get(path.size() - 2)))) {
                    path.add(new int[]{nextRow, nextCol});
                    moved = true;
                    break;
                }
            }
            if (!moved) break;
        }
        return path;
    }

    static Map<String, Integer> computeTimeMap(List<int[]> path, int originalPathLength) {
        Map<String, Integer> timeMap = new HashMap<>();
        for (int t = 0; t < path.size(); t++) {
            int[] coord = path.get(t);
            timeMap.put(coordToString(coord), originalPathLength - t);
        }
        return timeMap;
    }

    static Map<String, Integer> computeSavedCalculations(List<int[]> path, Map<String, Integer> timeMap, int originalPathLength) {
        Map<String, Integer> savedCalculations = new HashMap<>();
        int maxDistance = 20;

        for (int t = 0; t < path.size(); t++) {
            int[] current = path.get(t);
            for (int i = current[0] - maxDistance; i <= current[0] + maxDistance; i++) {
                for (int j = current[1] - maxDistance; j <= current[1] + maxDistance; j++) {
                    if (isInGrid(i, j) && grid[i][j] != '#' && Math.abs(i - current[0]) + Math.abs(j - current[1]) <= maxDistance) {
                        int transitionTime = Math.abs(i - current[0]) + Math.abs(j - current[1]);
                        String key = coordToString(new int[]{current[0], current[1], i, j});
                        int savedTime = originalPathLength - (t + timeMap.getOrDefault(coordToString(new int[]{i, j}), 0) + transitionTime);
                        savedCalculations.put(key, savedTime);
                    }
                }
            }
        }
        return savedCalculations;
    }

    static int countResults(Map<String, Integer> savedCalculations) {
        int count = 0;
        for (int value : savedCalculations.values()) {
            if (value >= 100) {
                count++;
            }
        }
        return count;
    }

    static boolean isInGrid(int row, int col) {
        return row >= 0 && row < gridSize && col >= 0 && col < gridSize;
    }

    static String coordToString(int[] coord) {
        return Arrays.toString(coord);
    }
}