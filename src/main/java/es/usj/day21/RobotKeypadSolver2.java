package es.usj.day21;

import es.usj.day20.MazeSolver;

import java.io.*;
import java.util.*;

public class RobotKeypadSolver2 {
    private record Position(int row, int col) {

        @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof Position p)) return false;
            return row == p.row && col == p.col;
            }

        @Override
            public String toString() {
                return "(" + row + "," + col + ")";
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

    private static List<String> getNeighbours(Map<Position, String> reverseKeypad, Position current) {
        int[][] dirs = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        String[] moves = {"<", ">", "^", "v"};
        List<String> neighbours = new ArrayList<>();

        for (int i = 0; i < dirs.length; i++) {
            Position newPos = new Position(current.row + dirs[i][0], current.col + dirs[i][1]);
            if (reverseKeypad.containsKey(newPos)) {
                neighbours.add(moves[i]);
            }
        }
        return neighbours;
    }

    private static List<List<String>> findAllPaths(Map<String, Position> keypad, String start, String end) {
        Map<Position, String> reverseKeypad = new HashMap<>();
        for (Map.Entry<String, Position> entry : keypad.entrySet()) {
            reverseKeypad.put(entry.getValue(), entry.getKey());
        }

        Position currentNode = keypad.get(start);
        List<List<String>> allPaths = new ArrayList<>();
        Set<Position> visited = new HashSet<>();
        visited.add(currentNode);

        dfs(currentNode, new ArrayList<>(), visited, reverseKeypad, end, allPaths);
        return allPaths;
    }

    private static void dfs(Position node, List<String> path, Set<Position> visited,
                            Map<Position, String> reverseKeypad, String end, List<List<String>> allPaths) {
        if (reverseKeypad.get(node).equals(end)) {
            List<String> completePath = new ArrayList<>(path);
            completePath.add("A");
            allPaths.add(completePath);
            return;
        }

        List<String> neighbours = getNeighbours(reverseKeypad, node);
        for (String move : neighbours) {
            Position next = new Position(
                    node.row + (move.equals("v") ? 1 : move.equals("^") ? -1 : 0),
                    node.col + (move.equals(">") ? 1 : move.equals("<") ? -1 : 0)
            );

            if (!visited.contains(next)) {
                visited.add(next);
                path.add(move);
                dfs(next, path, visited, reverseKeypad, end, allPaths);
                path.remove(path.size() - 1);
                visited.remove(next);
            }
        }
    }

    private static long findPathLength(List<String> sequence, Map<Integer, Map<String, Position>> levelKeypads,
                                       int level, int maxLevel) {
        // Change return type to long
        String seqKey = String.join("", sequence) + "," + level;
        if (cache.containsKey(seqKey)) {
            return cache.get(seqKey);
        }

        Map<String, Position> keypad = levelKeypads.get(level);
        List<String> fullSeq = new ArrayList<>();
        fullSeq.add("A");
        fullSeq.addAll(sequence);

        long totalLength = 0;
        for (int i = 0; i < fullSeq.size() - 1; i++) {
            String start = fullSeq.get(i);
            String end = fullSeq.get(i + 1);

            List<List<String>> paths = findAllPaths(keypad, start, end);
            long minLength = Long.MAX_VALUE;

            for (List<String> path : paths) {
                long pathLength;
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

    private static final Map<String, Long> cache = new HashMap<>();

    public static void main(String[] args) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                MazeSolver.class.getClassLoader().getResourceAsStream("es/usj/day21/input.txt")))) {
            List<String> codes = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                codes.add(line.trim());
            }

            // Part 2: 25 levels of directional keypads
            Map<Integer, Map<String, Position>> levelKeypads = new HashMap<>();
            levelKeypads.put(0, NUMERICAL_KEYPAD);
            for (int i = 1; i <= 25; i++) {
                levelKeypads.put(i, DIRECTIONAL_KEYPAD);
            }

            long total = 0;
            for (String code : codes) {
                System.out.println("\nProcessing code: " + code);
                List<String> sequence = new ArrayList<>();
                for (char c : code.toCharArray()) {
                    sequence.add(String.valueOf(c));
                }

                int numericPart = Integer.parseInt(code.substring(0, code.length() - 1)
                        .replaceFirst("^0+", ""));
                long pathLength = findPathLength(sequence, levelKeypads, 0, 25);
                long complexity = (long) numericPart * pathLength;
                total += complexity;

                System.out.println("Path length: " + pathLength);
                System.out.println("Numeric part: " + numericPart);
                System.out.println("Complexity: " + complexity);
            }

            System.out.println("\nFinal total complexity: " + total);

        }
    }
}