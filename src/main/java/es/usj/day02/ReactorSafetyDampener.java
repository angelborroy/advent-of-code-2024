package es.usj.day02;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class ReactorSafetyDampener {
    public static void main(String[] args) {
        try {
            List<String> lines =
                    Files.readAllLines(Path.of(ReactorSafetyDampener.class.getResource("/es/usj/day02/input.txt").toURI()));
            int safeReports = 0;

            for (String line : lines) {
                if (line.trim().isEmpty()) continue;

                int[] levels = Arrays.stream(line.trim().split("\\s+"))
                        .mapToInt(Integer::parseInt)
                        .toArray();

                if (isReportSafeWithDampener(levels)) {
                    safeReports++;
                }
            }

            System.out.println("Number of safe reports with Problem Dampener: " + safeReports);

        } catch (Exception e) {
            System.err.println("Error reading input file: " + e.getMessage());
        }
    }

    private static boolean isReportSafeWithDampener(int[] levels) {
        if (isSafe(levels)) {
            return true;
        }

        // Try removing each level one at a time
        for (int i = 0; i < levels.length; i++) {
            int[] modifiedLevels = new int[levels.length - 1];
            int index = 0;

            // Create new array without the current level
            for (int j = 0; j < levels.length; j++) {
                if (j != i) {
                    modifiedLevels[index++] = levels[j];
                }
            }

            // Check if removing this level makes the report safe
            if (isSafe(modifiedLevels)) {
                return true;
            }
        }

        return false;
    }

    private static boolean isSafe(int[] levels) {
        if (levels.length < 2) return true;

        boolean increasing = true;
        boolean decreasing = true;

        for (int i = 1; i < levels.length; i++) {
            int diff = levels[i] - levels[i-1];

            // Check if difference is within allowed range (1-3)
            if (Math.abs(diff) < 1 || Math.abs(diff) > 3) {
                return false;
            }

            // Check if sequence maintains consistent direction
            if (diff > 0) {
                decreasing = false;
            } else {
                increasing = false;
            }

            // If neither increasing nor decreasing, sequence is invalid
            if (!increasing && !decreasing) {
                return false;
            }
        }

        return true;
    }
}