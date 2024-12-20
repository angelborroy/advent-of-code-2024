package es.usj.day19;

import es.usj.day18.MemoryPathfindingPart1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LinenLayout {
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

        int count = 0;
        for (String design : designs) {
            if (canFormDesign(design, patterns)) {
                count++;
            }
        }

        System.out.println(count);
    }

    private static boolean canFormDesign(String design, List<String> patterns) {
        boolean[] dp = new boolean[design.length() + 1];
        dp[design.length()] = true;

        for (int i = design.length() - 1; i >= 0; i--) {
            for (String p : patterns) {
                if (i + p.length() <= design.length() &&
                        design.startsWith(p, i) &&
                        dp[i + p.length()]) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[0];
    }
}