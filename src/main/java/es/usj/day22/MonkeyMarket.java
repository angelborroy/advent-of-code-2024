package es.usj.day22;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MonkeyMarket {
    private static final long PRUNE_MOD = 16777216;
    private static final long MULT_1 = 64;
    private static final long MULT_2 = 2048;
    private static final long DIV_1 = 32;
    private static final int GENERATIONS = 2000;

    public static void main(String[] args) throws Exception {
        List<Long> initialSecrets = readInput();
        long sum = processSecrets(initialSecrets);
        System.out.println("Sum of 2000th secret numbers: " + sum);
    }

    private static List<Long> readInput() throws Exception {
        List<Long> secrets = new ArrayList<>();
        try (Scanner scanner = new Scanner(
                MonkeyMarket.class.getResourceAsStream("/es/usj/day22/input.txt"))) {
            while (scanner.hasNextLine()) {
                secrets.add(Long.parseLong(scanner.nextLine().trim()));
            }
        }
        return secrets;
    }

    private static long processSecrets(List<Long> initialSecrets) {
        long sum = 0;
        for (long secret : initialSecrets) {
            long finalSecret = generateNthSecret(secret, GENERATIONS);
            sum += finalSecret;
        }
        return sum;
    }

    private static long generateNthSecret(long initialSecret, int n) {
        long currentSecret = initialSecret;
        for (int i = 0; i < n; i++) {
            currentSecret = generateNextSecret(currentSecret);
        }
        return currentSecret;
    }

    private static long generateNextSecret(long secret) {
        long result = mixAndPrune(secret, secret * MULT_1);
        result = mixAndPrune(result, result / DIV_1);
        result = mixAndPrune(result, result * MULT_2);
        return result;
    }

    private static long mixAndPrune(long secret, long value) {
        long mixed = secret ^ value;
        return mixed % PRUNE_MOD;
    }
}