package es.usj.day10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class HikingTrails {
    private static final int[] DX = {0, 0, 1, -1}; // right, left, down, up
    private static final int[] DY = {1, -1, 0, 0};
    private int[][] grid;
    private int rows;
    private int cols;

    public static void main(String[] args) throws IOException {
        HikingTrails solution = new HikingTrails();
        solution.solve();
    }

    public void solve() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("es/usj/day10/input.txt");
        List<String> lines;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            lines = reader.lines().toList();
        }

        rows = lines.size();
        cols = lines.get(0).length();
        grid = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            String line = lines.get(i);
            for (int j = 0; j < cols; j++) {
                grid[i][j] = line.charAt(j) - '0';
            }
        }

        // Find all trailheads (positions with height 0)
        List<Point> trailheads = findTrailheads();

        // Calculate score for each trailhead
        int totalScore = 0;
        for (Point trailhead : trailheads) {
            int score = calculateTrailheadScore(trailhead);
            totalScore += score;
        }

        System.out.println("Sum of trailhead scores: " + totalScore);
    }

    private List<Point> findTrailheads() {
        List<Point> trailheads = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 0) {
                    trailheads.add(new Point(i, j));
                }
            }
        }
        return trailheads;
    }

    private int calculateTrailheadScore(Point start) {
        Set<Point> reachableNines = new HashSet<>();
        dfs(start, new HashSet<>(), reachableNines);
        return reachableNines.size();
    }

    private void dfs(Point current, Set<Point> visited, Set<Point> reachableNines) {
        visited.add(current);

        // If we reached a 9, add it to reachableNines
        if (grid[current.x][current.y] == 9) {
            reachableNines.add(current);
            return;
        }

        // Try all four directions
        for (int i = 0; i < 4; i++) {
            int newX = current.x + DX[i];
            int newY = current.y + DY[i];
            Point next = new Point(newX, newY);

            if (isValid(newX, newY) && !visited.contains(next)) {
                // Only continue if the height increases by exactly 1
                if (grid[newX][newY] == grid[current.x][current.y] + 1) {
                    dfs(next, visited, reachableNines);
                }
            }
        }
    }

    private boolean isValid(int x, int y) {
        return x >= 0 && x < rows && y >= 0 && y < cols;
    }

    private static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}