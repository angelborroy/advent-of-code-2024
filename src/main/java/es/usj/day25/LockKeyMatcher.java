package es.usj.day25;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LockKeyMatcher {
    private static class Schematic {
        List<Integer> heights;
        boolean isLock;

        public Schematic(String[] lines) {
            this.heights = new ArrayList<>();
            this.isLock = lines[0].startsWith("#####");

            for (int col = 0; col < lines[0].length(); col++) {
                int count = 0;
                if (isLock) {
                    for (String line : lines) {
                        if (line.charAt(col) == '#') count++;
                        else break;
                    }
                } else {
                    for (int row = lines.length - 1; row >= 0; row--) {
                        if (lines[row].charAt(col) == '#') count++;
                        else break;
                    }
                }
                heights.add(count - 1);
            }
        }
    }

    public static void main(String... args) throws Exception {
        int result = countMatches(readInput());
        System.out.println("Number of valid lock/key pairs: " + result);
    }

    private static List<String> readInput() throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                LockKeyMatcher.class.getClassLoader().getResourceAsStream("es/usj/day25/input.txt")))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    private static int countMatches(List<String> input) {
        List<Schematic> locks = new ArrayList<>();
        List<Schematic> keys = new ArrayList<>();
        List<String> current = new ArrayList<>();

        for (String line : input) {
            if (line.trim().isEmpty() && !current.isEmpty()) {
                Schematic s = new Schematic(current.toArray(new String[0]));
                (s.isLock ? locks : keys).add(s);
                current.clear();
            } else if (!line.trim().isEmpty()) {
                current.add(line);
            }
        }

        if (!current.isEmpty()) {
            Schematic s = new Schematic(current.toArray(new String[0]));
            (s.isLock ? locks : keys).add(s);
        }

        int matches = 0;
        for (Schematic lock : locks) {
            for (Schematic key : keys) {
                if (isMatch(lock, key)) matches++;
            }
        }
        return matches;
    }

    private static boolean isMatch(Schematic lock, Schematic key) {
        if (lock.heights.size() != key.heights.size()) return false;
        for (int i = 0; i < lock.heights.size(); i++) {
            if (lock.heights.get(i) + key.heights.get(i) > 5) return false;
        }
        return true;
    }
}