package es.usj.day08;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class AntennaAnalyzer {
    private final char[][] grid;
    private final Set<Point> antinodes = new HashSet<>();
    private final int rows;
    private final int cols;

    private static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Point point)) return false;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    public AntennaAnalyzer() throws IOException {
        List<String> lines = new BufferedReader(
                new InputStreamReader(
                        ClassLoader.getSystemResourceAsStream("es/usj/day08/input.txt")
                )
        ).lines().toList();
        rows = lines.size();
        cols = lines.get(0).length();
        grid = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            grid[i] = lines.get(i).toCharArray();
        }
    }

    public int calculateAntinodes() {
        Map<Character, List<Point>> frequencyMap = new HashMap<>();

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                char c = grid[y][x];
                if (c != '.') {
                    frequencyMap.computeIfAbsent(c, k -> new ArrayList<>())
                            .add(new Point(x, y));
                }
            }
        }

        for (List<Point> antennas : frequencyMap.values()) {
            for (int i = 0; i < antennas.size(); i++) {
                for (int j = i + 1; j < antennas.size(); j++) {
                    Point a1 = antennas.get(i);
                    Point a2 = antennas.get(j);
                    checkAntinodesForPair(a1, a2);
                }
            }
        }

        return antinodes.size();
    }

    private void checkAntinodesForPair(Point a1, Point a2) {
        int dx = a2.x - a1.x;
        int dy = a2.y - a1.y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        checkAntinodeFromCenter(a1, a2);
        checkAntinodeFromCenter(a2, a1);
    }

    private void checkAntinodeFromCenter(Point center, Point other) {
        double dx = other.x - center.x;
        double dy = other.y - center.y;

        int farX = (int) Math.round(center.x - dx);
        int farY = (int) Math.round(center.y - dy);

        if (farX >= 0 && farX < cols && farY >= 0 && farY < rows) {
            antinodes.add(new Point(farX, farY));
        }
    }

    public static void main(String[] args) {
        try {
            AntennaAnalyzer analyzer = new AntennaAnalyzer();
            int result = analyzer.calculateAntinodes();
            System.out.println("Number of unique antinode locations: " + result);
        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
        }
    }
}