import java.io.*;
import java.util.*;

public class MazeSolverPart1 {
    private char[][] grid;
    private int rows;
    private int cols;
    private Position start;
    private Position end;
    private final int[][] DIRS = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}}; // up, right, down, left
    private Map<Position, Integer> distances = new HashMap<>();

    static class Position {
        int row, col;

        Position(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Position)) return false;
            Position pos = (Position) o;
            return row == pos.row && col == pos.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }
    }

    static class State {
        int dist;
        Position cheatStart;
        Position cheatEnd;
        Integer cheatTime;
        Position pos;

        State(int dist, Position cheatStart, Position cheatEnd, Integer cheatTime, Position pos) {
            this.dist = dist;
            this.cheatStart = cheatStart;
            this.cheatEnd = cheatEnd;
            this.cheatTime = cheatTime;
            this.pos = pos;
        }
    }

    public MazeSolverPart1(String filename) throws IOException {
        loadGrid(filename);
        findStartEnd();
        calculateDistances();
    }

    private void loadGrid(String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }
        rows = lines.size();
        cols = lines.get(0).length();
        grid = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            grid[i] = lines.get(i).toCharArray();
        }
    }

    private void findStartEnd() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == 'S') {
                    start = new Position(r, c);
                } else if (grid[r][c] == 'E') {
                    end = new Position(r, c);
                }
            }
        }
    }

    private void calculateDistances() {
        Deque<State> queue = new ArrayDeque<>();
        queue.offer(new State(0, null, null, null, end));

        while (!queue.isEmpty()) {
            State current = queue.pollFirst();
            Position pos = current.pos;

            if (distances.containsKey(pos)) {
                continue;
            }

            distances.put(pos, current.dist);

            for (int[] dir : DIRS) {
                int newRow = pos.row + dir[0];
                int newCol = pos.col + dir[1];

                if (isValid(newRow, newCol) && grid[newRow][newCol] != '#') {
                    queue.offer(new State(current.dist + 1, null, null, null,
                            new Position(newRow, newCol)));
                }
            }
        }
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    private int findCheats(int baseDistance, int cheatTime) {
        Set<List<Position>> cheats = new HashSet<>();
        Deque<State> queue = new ArrayDeque<>();
        queue.offer(new State(0, null, null, null, start));
        Set<String> seen = new HashSet<>();
        final int SAVE = 100;

        while (!queue.isEmpty()) {
            State curr = queue.pollFirst();

            if (curr.dist >= baseDistance - SAVE) {
                continue;
            }

            Position pos = curr.pos;
            if (grid[pos.row][pos.col] == 'E') {
                if (curr.cheatEnd == null) {
                    curr.cheatEnd = pos;
                }
                if (curr.dist <= baseDistance - SAVE &&
                        !cheats.contains(Arrays.asList(curr.cheatStart, curr.cheatEnd))) {
                    cheats.add(Arrays.asList(curr.cheatStart, curr.cheatEnd));
                }
            }

            String stateKey = String.format("%d,%d,%s,%s,%s",
                    pos.row, pos.col,
                    curr.cheatStart, curr.cheatEnd, curr.cheatTime);
            if (seen.contains(stateKey)) {
                continue;
            }
            seen.add(stateKey);

            if (curr.cheatStart == null) {
                if (grid[pos.row][pos.col] != '#') {
                    queue.offer(new State(curr.dist, pos, null, cheatTime, pos));
                }
            }

            if (curr.cheatTime != null && grid[pos.row][pos.col] != '#') {
                if (distances.containsKey(pos) &&
                        distances.get(pos) <= baseDistance - 100 - curr.dist) {
                    cheats.add(Arrays.asList(curr.cheatStart, pos));
                }
            }

            if (curr.cheatTime != null && curr.cheatTime == 0) {
                continue;
            }

            for (int[] dir : DIRS) {
                int newRow = pos.row + dir[0];
                int newCol = pos.col + dir[1];
                Position newPos = new Position(newRow, newCol);

                if (curr.cheatTime != null) {
                    if (isValid(newRow, newCol)) {
                        queue.offer(new State(curr.dist + 1, curr.cheatStart, null,
                                curr.cheatTime - 1, newPos));
                    }
                } else {
                    if (isValid(newRow, newCol) && grid[newRow][newCol] != '#') {
                        queue.offer(new State(curr.dist + 1, curr.cheatStart,
                                curr.cheatEnd, null, newPos));
                    }
                }
            }
        }
        return cheats.size();
    }

    public static void main(String[] args) {
        try {
            MazeSolverPart1 solver = new MazeSolverPart1("/Users/angel.fernandoborroy/Downloads/input.txt");
            int baseDistance = solver.distances.get(solver.start);
            System.out.println(solver.findCheats(baseDistance, 2));
        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
        }
    }
}