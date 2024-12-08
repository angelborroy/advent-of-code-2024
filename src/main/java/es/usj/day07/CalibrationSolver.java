package es.usj.day07;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CalibrationSolver {
    public static void main(String[] args) {
        long totalSum = 0;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        CalibrationSolver.class.getResourceAsStream("/es/usj/day07/input.txt")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(":");
                long targetValue = Long.parseLong(parts[0].trim());
                String[] numbers = parts[1].trim().split("\\s+");

                long[] values = new long[numbers.length];
                for (int i = 0; i < numbers.length; i++) {
                    values[i] = Long.parseLong(numbers[i]);
                }

                if (canBeSolved(values, targetValue)) {
                    totalSum += targetValue;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return;
        }

        System.out.println("Total calibration result: " + totalSum);
    }

    private static boolean canBeSolved(long[] values, long target) {
        int numOperators = values.length - 1;

        int maxCombinations = 1 << numOperators;

        for (int i = 0; i < maxCombinations; i++) {
            long result = values[0];

            for (int j = 0; j < numOperators; j++) {
                // Get operator: bit 0 means +, bit 1 means *
                boolean isMultiply = ((i >> j) & 1) == 1;

                if (isMultiply) {
                    result *= values[j + 1];
                } else {
                    result += values[j + 1];
                }
            }

            if (result == target) {
                return true;
            }
        }

        return false;
    }
}