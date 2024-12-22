package es.usj.day21;

import es.usj.day20.MazeSolver;

import java.io.*;
import java.util.*;

public class RobotKeypadSolver {
    private record Position(int row, int col) {

        @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof Position p)) return false;
            return row == p.row && col == p.col;
            }

    }

    private static final Map<String, Position> NUMERICAL_KEYPAD = new HashMap<>() {{
        put("7", new Position(0, 0));
        put("8", new Position(0, 1));
        put("9", new Position(0, 2));
        put("4", new Position(1, 0));
        put("5", new Position(1, 1));
        put("6", new Position(1, 2));
        put("1", new Position(2, 0));
        put("2", new Position(2, 1));
        put("3", new Position(2, 2));
        put("0", new Position(3, 1));
        put("A", new Position(3, 2));
    }};

    private static final Map<String, Position> DIRECTIONAL_KEYPAD = new HashMap<>() {{
        put("^", new Position(0, 1));
        put("A", new Position(0, 2));
        put("<", new Position(1, 0));
        put("v", new Position(1, 1));
        put(">", new Position(1, 2));
    }};

    private static final int[][] DIRECTIONS = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
    private static final String[] DIRECTION_SYMBOLS = {"<", ">", "^", "v"};

    private static final Map<String, Integer> cache = new HashMap<>();

    private static List<List<String>> findAllPaths(Map<String, Position> keypad, String start, String end) {
        Map<Position, String> reverseKeypad = new HashMap<>();
        keypad.forEach((k, v) -> reverseKeypad.put(v, k));

        Position startPos = keypad.get(start);
        List<List<String>> allPaths = new ArrayList<>();

        dfs(startPos, new ArrayList<>(), new HashSet<>(Collections.singleton(startPos)),
                reverseKeypad, end, allPaths);

        return allPaths;
    }

    private static void dfs(Position current, List<String> path, Set<Position> visited,
                            Map<Position, String> reverseKeypad, String end, List<List<String>> allPaths) {
        if (reverseKeypad.get(current).equals(end)) {
            List<String> completePath = new ArrayList<>(path);
            completePath.add("A");
            allPaths.add(completePath);
            return;
        }

        for (int i = 0; i < DIRECTIONS.length; i++) {
            Position next = new Position(
                    current.row + DIRECTIONS[i][0],
                    current.col + DIRECTIONS[i][1]
            );

            if (reverseKeypad.containsKey(next) && !visited.contains(next)) {
                visited.add(next);
                path.add(DIRECTION_SYMBOLS[i]);
                dfs(next, path, visited, reverseKeypad, end, allPaths);
                path.remove(path.size() - 1);
                visited.remove(next);
            }
        }
    }

    private static int findPathLength(List<String> sequence, Map<Integer, Map<String, Position>> levelKeypads,
                                      int level, int maxLevel) {
        String seqKey = String.join("", sequence) + "," + level;
        if (cache.containsKey(seqKey)) {
            return cache.get(seqKey);
        }

        Map<String, Position> keypad = levelKeypads.get(level);
        List<String> fullSeq = new ArrayList<>();
        fullSeq.add("A");
        fullSeq.addAll(sequence);

        int totalLength = 0;
        for (int i = 0; i < fullSeq.size() - 1; i++) {
            String start = fullSeq.get(i);
            String end = fullSeq.get(i + 1);
            List<List<String>> paths = findAllPaths(keypad, start, end);

            int minLength = Integer.MAX_VALUE;
            for (List<String> path : paths) {
                int pathLength;
                if (level < maxLevel) {
                    pathLength = findPathLength(path, levelKeypads, level + 1, maxLevel);
                } else {
                    pathLength = path.size();
                }
                minLength = Math.min(minLength, pathLength);
            }
            totalLength += minLength;
        }

        cache.put(seqKey, totalLength);
        return totalLength;
    }

    public static void main(String[] args) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                MazeSolver.class.getClassLoader().getResourceAsStream("es/usj/day21/input.txt")))) {
            List<String> codes = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                codes.add(line.trim());
            }

            Map<Integer, Map<String, Position>> levelKeypads = new HashMap<>();
            levelKeypads.put(0, NUMERICAL_KEYPAD);
            levelKeypads.put(1, DIRECTIONAL_KEYPAD);
            levelKeypads.put(2, DIRECTIONAL_KEYPAD);

            long total = 0;
            for (String code : codes) {
                List<String> sequence = new ArrayList<>();
                for (char c : code.toCharArray()) {
                    sequence.add(String.valueOf(c));
                }

                int numericPart = Integer.parseInt(code.substring(0, code.length() - 1)
                        .replaceFirst("^0+", ""));
                int pathLength = findPathLength(sequence, levelKeypads, 0, 2);

                total += (long) numericPart * pathLength;

                System.out.println("Code: " + code);
                System.out.println("Path length: " + pathLength);
                System.out.println("Numeric part: " + numericPart);
                System.out.println("Complexity: " + ((long) numericPart * pathLength));
                System.out.println();
            }

            System.out.println("Total complexity: " + total);

        }
    }
}