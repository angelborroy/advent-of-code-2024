package es.usj.day19;

import es.usj.day18.MemoryPathfindingPart1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LinenLayoutPart2 {
    public static void main(String[] args) throws Exception {
        List<String> patterns = new ArrayList<>();
        List<String> designs = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                MemoryPathfindingPart1.class.getClassLoader().getResourceAsStream("es/usj/day19/input.txt")))) {
            String firstLine = br.readLine();
            if (firstLine != null) {
                for (String p : firstLine.split(",")) {
                    String pattern = p.trim();
                    if (!pattern.isEmpty()) {
                        patterns.add(pattern);
                    }
                }
            }

            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                String design = line.trim();
                if (!design.isEmpty()) {
                    designs.add(design);
                }
            }
        }

        long total = 0;
        for (String design : designs) {
            total += countWays(design, patterns);
        }

        System.out.println(total);
    }

    private static long countWays(String design, List<String> patterns) {
        long[] dp = new long[design.length() + 1];
        dp[design.length()] = 1;

        for (int i = design.length() - 1; i >= 0; i--) {
            for (String p : patterns) {
                if (i + p.length() <= design.length() && design.startsWith(p, i)) {
                    dp[i] += dp[i + p.length()];
                }
            }
        }

        return dp[0];
    }
}