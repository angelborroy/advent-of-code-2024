package es.usj.day22;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class MonkeyMarketPart2 {
    private static final long PRUNE_MOD = 16777216;

    public static void main(String[] args) throws IOException {
        List<String> lines;
        try (InputStream inputStream = MonkeyMarket.class.getResourceAsStream("/es/usj/day22/input.txt")) {
            lines = new BufferedReader(new InputStreamReader(inputStream)).lines().toList();
        }

        Map<List<Integer>, Long> scoreMap = new HashMap<>();

        for (String line : lines) {
            long initialSecret = Long.parseLong(line.trim());
            List<Integer> prices = convertToPrices(generateSecretNumbers(initialSecret));
            List<Integer> priceChanges = calculateChanges(prices);

            Map<List<Integer>, Integer> patterns = getScores(prices, priceChanges);
            for (Map.Entry<List<Integer>, Integer> entry : patterns.entrySet()) {
                scoreMap.merge(entry.getKey(), (long)entry.getValue(), Long::sum);
            }
        }

        System.out.println("Part 2: " + Collections.max(scoreMap.values()));
    }

    private static List<Long> generateSecretNumbers(long initial) {
        List<Long> numbers = new ArrayList<>();
        numbers.add(initial);

        long current = initial;
        for (int i = 0; i < 2000; i++) {
            current = prune(mix(current, (current * 64) % PRUNE_MOD));
            current = prune(mix(current, current / 32));
            current = prune(mix(current, (current * 2048) % PRUNE_MOD));
            numbers.add(current);
        }
        return numbers;
    }

    private static long mix(long x, long y) {
        return x ^ y;
    }

    private static long prune(long x) {
        return x % PRUNE_MOD;
    }

    private static List<Integer> convertToPrices(List<Long> secrets) {
        return secrets.stream()
                .map(x -> (int)(x % 10))
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    private static List<Integer> calculateChanges(List<Integer> prices) {
        List<Integer> changes = new ArrayList<>();
        for (int i = 0; i < prices.size() - 1; i++) {
            changes.add(prices.get(i + 1) - prices.get(i));
        }
        return changes;
    }

    private static Map<List<Integer>, Integer> getScores(List<Integer> prices, List<Integer> changes) {
        Map<List<Integer>, Integer> scores = new HashMap<>();
        for (int i = 0; i < changes.size() - 3; i++) {
            List<Integer> pattern = Arrays.asList(
                    changes.get(i),
                    changes.get(i + 1),
                    changes.get(i + 2),
                    changes.get(i + 3)
            );
            if (!scores.containsKey(pattern)) {
                scores.put(pattern, prices.get(i + 4));
            }
        }
        return scores;
    }
}