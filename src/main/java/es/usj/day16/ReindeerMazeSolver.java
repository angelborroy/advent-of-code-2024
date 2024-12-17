package es.usj.day16;

import java.io.*;
import java.util.*;

public class ReindeerMazeSolver {
    static final int TURN_COST = 1000;
    static final int MOVE_COST = 1;

    static char[][] maze;
    static int[] start = new int[2];
    static int[] end = new int[2];

    // Movement directions (right, down, left, up)
    static final int[][] DIRECTIONS = {{0,1}, {1,0}, {0,-1}, {-1,0}};

    public static void main(String[] args) throws IOException {
        loadMaze();
        System.out.println("Lowest score: " + findPath());
    }

    static void loadMaze() throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                ReindeerMazePathTracker.class.getClassLoader().getResourceAsStream("es/usj/day16/input.txt")))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
        }

        maze = new char[lines.size()][lines.get(0).length()];

        for (int i = 0; i < lines.size(); i++) {
            maze[i] = lines.get(i).toCharArray();
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == 'S') {
                    start[0] = i; start[1] = j;
                } else if (maze[i][j] == 'E') {
                    end[0] = i; end[1] = j;
                }
            }
        }
    }

    static int findPath() {
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[3]));
        boolean[][][] visited = new boolean[maze.length][maze[0].length][4];

        queue.offer(new int[]{start[0], start[1], 0, 0});

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0], col = current[1], dir = current[2], cost = current[3];

            if (row == end[0] && col == end[1]) {
                return cost;
            }

            if (visited[row][col][dir]) continue;
            visited[row][col][dir] = true;

            // Try turning
            queue.offer(new int[]{row, col, (dir + 1) % 4, cost + TURN_COST});  // right
            queue.offer(new int[]{row, col, (dir + 3) % 4, cost + TURN_COST});  // left

            // Try moving forward
            int newRow = row + DIRECTIONS[dir][0];
            int newCol = col + DIRECTIONS[dir][1];
            if (isValid(newRow, newCol)) {
                queue.offer(new int[]{newRow, newCol, dir, cost + MOVE_COST});
            }
        }

        return -1;
    }

    static boolean isValid(int row, int col) {
        return row >= 0 && row < maze.length &&
                col >= 0 && col < maze[0].length &&
                maze[row][col] != '#';
    }
}