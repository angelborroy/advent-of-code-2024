package es.usj.day10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class HikingTrailsRating {
    private static final int[] DX = {0, 0, 1, -1}; // right, left, down, up
    private static final int[] DY = {1, -1, 0, 0};
    private int[][] grid;
    private int rows;
    private int cols;

    public static void main(String[] args) throws IOException {
        HikingTrailsRating solution = new HikingTrailsRating();
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

        // Calculate rating for each trailhead
        long totalRating = 0;
        for (Point trailhead : trailheads) {
            int rating = calculateTrailheadRating(trailhead);
            totalRating += rating;
            //System.out.println("Trailhead at " + trailhead.x + "," + trailhead.y + " has rating: " + rating);
        }

        System.out.println("Sum of trailhead ratings: " + totalRating);
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

    private int calculateTrailheadRating(Point start) {
        return countDistinctPaths(start, new HashSet<>());
    }

    private int countDistinctPaths(Point current, Set<Point> visited) {
        // If we've reached height 9, we've found a valid path
        if (grid[current.x][current.y] == 9) {
            return 1;
        }

        visited.add(current);
        int totalPaths = 0;

        // Try all four directions
        for (int i = 0; i < 4; i++) {
            int newX = current.x + DX[i];
            int newY = current.y + DY[i];
            Point next = new Point(newX, newY);

            if (isValid(newX, newY) && !visited.contains(next)) {
                // Only continue if the height increases by exactly 1
                if (grid[newX][newY] == grid[current.x][current.y] + 1) {
                    totalPaths += countDistinctPaths(next, new HashSet<>(visited));
                }
            }
        }

        return totalPaths;
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