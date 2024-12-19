package es.usj.day18;

import java.io.*;
import java.util.*;

public class MemoryPathfindingPart2 {
    static class Point {
        int x, y;
        Point(int x, int y) { this.x = x; this.y = y; }

        @Override
        public boolean equals(Object o) {
            Point p = (Point) o;
            return x == p.x && y == p.y;
        }

        @Override
        public int hashCode() { return Objects.hash(x, y); }

        @Override
        public String toString() { return x + "," + y; }
    }

    private static final int[] DX = {-1, 1, 0, 0};
    private static final int[] DY = {0, 0, -1, 1};
    private static final int SIZE = 71;

    public static void main(String[] args) throws IOException {
        List<Point> points = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                MemoryPathfindingPart2.class.getClassLoader().getResourceAsStream("es/usj/day18/input.txt")));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] coordinates = line.trim().split(",");
            points.add(new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])));
        }
        reader.close();

        Set<Point> blocked = new HashSet<>();
        for (Point p : points) {
            blocked.add(p);
            if (!hasPath(blocked)) {
                System.out.println(p);
                return;
            }
        }
        System.out.println("No blocking point found");
    }

    // Classic Breadth-First Search (BFS) algorithm
    private static boolean hasPath(Set<Point> blocked) {
        Queue<Point> queue = new LinkedList<>();
        Set<Point> seen = new HashSet<>();
        Point end = new Point(SIZE - 1, SIZE - 1);
        Point start = new Point(0, 0);

        queue.add(start);
        seen.add(start);

        while (!queue.isEmpty()) {
            Point p = queue.poll();
            if (p.equals(end)) return true;

            for (int i = 0; i < 4; i++) {
                Point next = new Point(p.x + DX[i], p.y + DY[i]);
                if (next.x >= 0 && next.x < SIZE && next.y >= 0 && next.y < SIZE
                        && !blocked.contains(next) && !seen.contains(next)) {
                    seen.add(next);
                    queue.add(next);
                }
            }
        }
        return false;
    }
}