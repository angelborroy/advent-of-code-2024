package es.usj.day11;

import java.math.BigInteger;
import java.util.*;

public class PlutoStonesPart2 {
    private static final Map<String, Long> memorizationMap = new HashMap<>();
    private static final List<BigInteger> inputNumbers = new ArrayList<>();

    public static void main(String[] args) {
        String input = "64554 35 906 6 6960985 5755 975820 0";
        for (String value : input.split(" ")) {
            inputNumbers.add(new BigInteger(value));
        }
        long totalSum = 0;
        for (BigInteger number : inputNumbers) {
            totalSum += computeResult(number, 75);
        }
        System.out.println(totalSum);
    }

    private static long computeResult(BigInteger number, int iterations) {
        String key = number.toString() + "_" + iterations;
        if (memorizationMap.containsKey(key)) {
            return memorizationMap.get(key);
        }

        long result;
        if (iterations == 0) {
            result = 1;
        } else if (number.equals(BigInteger.ZERO)) {
            // If number == 0
            result = computeResult(BigInteger.ONE, iterations - 1);
        } else {
            String numberString = number.toString();
            if (numberString.length() % 2 == 0) {
                // Even number of digits, split
                int mid = numberString.length() / 2;
                String leftString = numberString.substring(0, mid);
                String rightString = numberString.substring(mid);
                BigInteger leftPart = new BigInteger(leftString);
                BigInteger rightPart = new BigInteger(rightString);
                result = computeResult(leftPart, iterations - 1) + computeResult(rightPart, iterations - 1);
            } else {
                // Odd number of digits, multiply by 2024
                BigInteger multipliedValue = number.multiply(BigInteger.valueOf(2024));
                result = computeResult(multipliedValue, iterations - 1);
            }
        }

        memorizationMap.put(key, result);
        return result;
    }
}
