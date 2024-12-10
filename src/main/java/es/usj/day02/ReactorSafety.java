package es.usj.day02;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class ReactorSafety {
    public static void main(String[] args) {
        try {
            List<String> lines =
                    Files.readAllLines(Path.of(ReactorSafety.class.getResource("/es/usj/day02/input.txt").toURI()));
            int safeReports = 0;

            for (String line : lines) {
                if (line.trim().isEmpty()) continue;

                int[] levels = Arrays.stream(line.trim().split("\\s+"))
                        .mapToInt(Integer::parseInt)
                        .toArray();

                if (isSafe(levels)) {
                    safeReports++;
                }
            }

            System.out.println("Number of safe reports: " + safeReports);

        } catch (Exception e) {
            System.err.println("Error reading input file: " + e.getMessage());
        }
    }

    private static boolean isSafe(int[] levels) {
        if (levels.length < 2) return true;

        boolean increasing = true;
        boolean decreasing = true;

        for (int i = 1; i < levels.length; i++) {
            int diff = levels[i] - levels[i-1];

            if (Math.abs(diff) < 1 || Math.abs(diff) > 3) {
                return false;
            }

            if (diff > 0) {
                decreasing = false;
            } else {
                increasing = false;
            }

            if (!increasing && !decreasing) {
                return false;
            }
        }

        return true;
    }
}