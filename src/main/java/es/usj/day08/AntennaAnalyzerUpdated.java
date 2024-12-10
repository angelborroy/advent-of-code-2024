package es.usj.day08;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class AntennaAnalyzerUpdated {
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

    public AntennaAnalyzerUpdated() throws IOException {
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

        for (Map.Entry<Character, List<Point>> entry : frequencyMap.entrySet()) {
            List<Point> antennas = entry.getValue();

            if (antennas.size() < 2) continue;

            antinodes.addAll(antennas);

            for (int i = 0; i < antennas.size(); i++) {
                for (int j = i + 1; j < antennas.size(); j++) {
                    findAntinodesBetweenPair(antennas.get(i), antennas.get(j));
                }
            }
        }

        return antinodes.size();
    }

    private void findAntinodesBetweenPair(Point a1, Point a2) {
        int dx = a2.x - a1.x;
        int dy = a2.y - a1.y;

        if (dx == 0) {
            int minY = Math.min(a1.y, a2.y);
            int maxY = Math.max(a1.y, a2.y);
            for (int y = 0; y < rows; y++) {
                if (y < minY || y > maxY) {
                    antinodes.add(new Point(a1.x, y));
                }
            }
            return;
        }

        if (dy == 0) {
            int minX = Math.min(a1.x, a2.x);
            int maxX = Math.max(a1.x, a2.x);
            for (int x = 0; x < cols; x++) {
                if (x < minX || x > maxX) {
                    antinodes.add(new Point(x, a1.y));
                }
            }
            return;
        }

        double m = (double) dy / dx;
        double b = a1.y - m * a1.x;

        int minX = 0;
        int maxX = cols - 1;

        for (int x = minX; x <= maxX; x++) {
            double y = m * x + b;
            int roundedY = (int) Math.round(y);

            if (roundedY >= 0 && roundedY < rows &&
                    isValidDouble(y) &&
                    !isBetweenPoints(x, roundedY, a1, a2)) {
                antinodes.add(new Point(x, roundedY));
            }
        }
    }

    private boolean isValidDouble(double d) {
        return Math.abs(d - Math.round(d)) < 0.000001;
    }

    private boolean isBetweenPoints(int x, int y, Point p1, Point p2) {
        return x >= Math.min(p1.x, p2.x) &&
                x <= Math.max(p1.x, p2.x) &&
                y >= Math.min(p1.y, p2.y) &&
                y <= Math.max(p1.y, p2.y);
    }

    public static void main(String[] args) {
        try {
            AntennaAnalyzerUpdated analyzer = new AntennaAnalyzerUpdated();
            int result = analyzer.calculateAntinodes();
            System.out.println("Number of unique antinode locations: " + result);
        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
        }
    }
}