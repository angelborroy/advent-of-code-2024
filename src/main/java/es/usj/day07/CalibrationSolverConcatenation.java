package es.usj.day07;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CalibrationSolverConcatenation {

    private static final int ADD = 0;
    private static final int MULTIPLY = 1;
    private static final int CONCAT = 2;

    public static void main(String[] args) {
        long totalSum = 0;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        CalibrationSolverConcatenation.class.getResourceAsStream("/es/usj/day07/input.txt")))) {
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

        // We need 2 bits per operator (00 for +, 01 for *, 10 for ||)
        int maxCombinations = (int) Math.pow(3, numOperators);

        for (int i = 0; i < maxCombinations; i++) {
            int[] operators = new int[numOperators];
            int num = i;
            for (int j = 0; j < numOperators; j++) {
                operators[j] = num % 3;
                num /= 3;
            }

            if (evaluateCombination(values, operators, target)) {
                return true;
            }
        }

        return false;
    }

    private static boolean evaluateCombination(long[] values, int[] operators, long target) {
        long result = values[0];
        String concatStr = String.valueOf(values[0]);

        for (int i = 0; i < operators.length; i++) {
            long nextValue = values[i + 1];

            switch (operators[i]) {
                case ADD:
                    if (!concatStr.equals(String.valueOf(result))) {
                        result = Long.parseLong(concatStr);
                    }
                    result += nextValue;
                    concatStr = String.valueOf(result);
                    break;

                case MULTIPLY:
                    if (!concatStr.equals(String.valueOf(result))) {
                        result = Long.parseLong(concatStr);
                    }
                    result *= nextValue;
                    concatStr = String.valueOf(result);
                    break;

                case CONCAT:
                    concatStr += String.valueOf(nextValue);
                    result = Long.parseLong(concatStr);
                    break;
            }
        }

        return result == target;
    }
}