package es.usj.day06;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GuardPathSimulator {
    private char[][] grid;
    private int rows;
    private int cols;
    private Point guardPos;
    private Direction guardDir;
    private final Set<Point> visitedPositions;
    private final Set<State> visitedStates;

    private enum Direction {
        UP(0, -1), RIGHT(1, 0), DOWN(0, 1), LEFT(-1, 0);

        final int dx, dy;

        Direction(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        Direction turnRight() {
            return values()[(ordinal() + 1) % 4];
        }
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
            if (!(o instanceof Point)) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return 31 * x + y;
        }
    }

    private static class State {
        Point pos;
        Direction dir;

        State(Point pos, Direction dir) {
            this.pos = new Point(pos.x, pos.y);
            this.dir = dir;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof State)) return false;
            State state = (State) o;
            return pos.equals(state.pos) && dir == state.dir;
        }

        @Override
        public int hashCode() {
            return 31 * pos.hashCode() + dir.hashCode();
        }
    }

    public GuardPathSimulator(String filename) throws IOException {
        loadGrid(filename);
        visitedPositions = new HashSet<>();
        visitedStates = new HashSet<>();
        findGuardStartPosition();
    }

    private void loadGrid(String filename) throws IOException {
        List<String> lines;
        try (InputStream inputStream = GuardPathSimulator.class.getResourceAsStream(filename)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Resource not found");
            }
            lines = new BufferedReader(new InputStreamReader(inputStream)).lines().toList();
        }
        rows = lines.size();
        cols = lines.get(0).length();
        grid = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            grid[i] = lines.get(i).toCharArray();
        }
    }

    private void findGuardStartPosition() {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                if (grid[y][x] == '^') {
                    guardPos = new Point(x, y);
                    guardDir = Direction.UP;
                    visitedPositions.add(new Point(x, y));
                    visitedStates.add(new State(guardPos, guardDir));
                    return;
                }
            }
        }
    }

    private boolean isInBounds(int x, int y) {
        return x >= 0 && x < cols && y >= 0 && y < rows;
    }

    private boolean hasObstacleAhead() {
        int newX = guardPos.x + guardDir.dx;
        int newY = guardPos.y + guardDir.dy;
        return !isInBounds(newX, newY) || grid[newY][newX] == '#';
    }

    public int simulateGuardPath() {
        while (true) {
            // Check if we're about to move out of bounds
            int nextX = guardPos.x + guardDir.dx;
            int nextY = guardPos.y + guardDir.dy;
            if (!isInBounds(nextX, nextY)) {
                break;  // Guard is about to leave the map
            }

            if (hasObstacleAhead()) {
                // Turn right if blocked
                guardDir = guardDir.turnRight();
            } else {
                // Move forward
                guardPos = new Point(nextX, nextY);
                visitedPositions.add(new Point(nextX, nextY));
            }

            // Check for cycle using state (position + direction)
            State currentState = new State(guardPos, guardDir);
            if (!visitedStates.add(currentState)) {
                // We've seen this exact state before, guard must have left the map
                // by this point or will never leave
                break;
            }
        }

        return visitedPositions.size();
    }

    public static void main(String[] args) {
        try {
            GuardPathSimulator simulator = new GuardPathSimulator("/es/usj/day06/input.txt");
            int distinctPositions = simulator.simulateGuardPath();
            System.out.println("The guard will visit " + distinctPositions + " distinct positions.");
        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
        }
    }
}