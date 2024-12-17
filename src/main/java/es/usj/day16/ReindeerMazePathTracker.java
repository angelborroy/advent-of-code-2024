package es.usj.day16;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class ReindeerMazePathTracker {
    private static final int COST_TO_ROTATE = 1000;
    private static final int COST_TO_MOVE = 1;
    private static final int[][] DIRECTIONS = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    private char[][] maze;
    private int rows, cols;
    private Position start, end;

    private static class Position {
        int x, y;
        Position(int x, int y) { this.x = x; this.y = y; }
        @Override public boolean equals(Object o) {
            return o instanceof Position p && x == p.x && y == p.y;
        }
        @Override public int hashCode() { return Objects.hash(x, y); }
    }

    private static class MazeState implements Comparable<MazeState> {
        int cost, x, y, direction;
        MazeState(int cost, int x, int y, int direction) {
            this.cost = cost; this.x = x; this.y = y; this.direction = direction;
        }
        @Override public int compareTo(MazeState other) { return Integer.compare(cost, other.cost); }
    }

    public static void main(String[] args) throws IOException {
        ReindeerMazePathTracker tracker = new ReindeerMazePathTracker();
        tracker.loadMaze();
        System.out.println("Tiles on optimal paths: " + tracker.findOptimalPathTiles());
    }

    private void loadMaze() throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream("es/usj/day16/input.txt")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) lines.add(line);
            }
        }
        rows = lines.size();
        cols = lines.get(0).length();
        maze = new char[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                maze[r][c] = lines.get(r).charAt(c);
                if (maze[r][c] == 'S') start = new Position(r, c);
                if (maze[r][c] == 'E') end = new Position(r, c);
            }
        }
    }

    private int findOptimalPathTiles() {
        var forwardCosts = search(start, false);
        var backwardCosts = search(end, true);
        int bestCost = Integer.MAX_VALUE;
        for (String key : forwardCosts.keySet()) {
            if (backwardCosts.containsKey(key)) {
                bestCost = Math.min(bestCost, forwardCosts.get(key) + backwardCosts.get(key));
            }
        }

        Set<Position> optimalPositions = new HashSet<>();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                for (int dir = 0; dir < 4; dir++) {
                    String key = r + "," + c + "," + dir;
                    if (forwardCosts.containsKey(key) && backwardCosts.containsKey(key) &&
                            forwardCosts.get(key) + backwardCosts.get(key) == bestCost) {
                        optimalPositions.add(new Position(r, c));
                    }
                }
            }
        }
        return optimalPositions.size();
    }

    private Map<String, Integer> search(Position start, boolean reverse) {
        PriorityQueue<MazeState> queue = new PriorityQueue<>();
        for (int dir = 0; dir < 4; dir++) {
            queue.offer(new MazeState(0, start.x, start.y, reverse ? (dir + 2) % 4 : dir));
        }
        Map<String, Integer> costs = new HashMap<>();

        while (!queue.isEmpty()) {
            MazeState current = queue.poll();
            String key = current.x + "," + current.y + "," + current.direction;
            if (costs.containsKey(key)) continue;
            costs.put(key, current.cost);

            int dir = reverse ? (current.direction + 2) % 4 : current.direction;
            int nx = current.x + DIRECTIONS[dir][0], ny = current.y + DIRECTIONS[dir][1];
            if (isValid(nx, ny)) {
                queue.offer(new MazeState(current.cost + COST_TO_MOVE, nx, ny, current.direction));
            }
            queue.offer(new MazeState(current.cost + COST_TO_ROTATE, current.x, current.y, (current.direction + 1) % 4));
            queue.offer(new MazeState(current.cost + COST_TO_ROTATE, current.x, current.y, (current.direction + 3) % 4));
        }
        return costs;
    }

    private boolean isValid(int x, int y) {
        return x >= 0 && x < rows && y >= 0 && y < cols && maze[x][y] != '#';
    }
}