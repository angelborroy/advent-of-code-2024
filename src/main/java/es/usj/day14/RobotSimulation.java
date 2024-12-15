package es.usj.day14;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RobotSimulation {

    private static final int WIDTH = 101;
    private static final int HEIGHT = 103;
    private static final List<Robot> robots = new ArrayList<>();
    private static final int[][] DIRECTIONS = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    static class Robot {
        int x, y, vx, vy;

        Robot(int x, int y, int vx, int vy) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
        }

        void move() {
            x = (x + vx + WIDTH) % WIDTH;
            y = (y + vy + HEIGHT) % HEIGHT;
        }
    }

    public static void main(String[] args) throws IOException {
        loadRobots();
        simulate();
    }

    private static void loadRobots() throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                RestroomRedoubt.class.getClassLoader().getResourceAsStream("es/usj/day14/input.txt")))) {
            String line;
            Pattern pattern = Pattern.compile("p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+)");

            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line.trim());
                if (matcher.matches()) {
                    robots.add(new Robot(
                            Integer.parseInt(matcher.group(1)),
                            Integer.parseInt(matcher.group(2)),
                            Integer.parseInt(matcher.group(3)),
                            Integer.parseInt(matcher.group(4))
                    ));
                }
            }
        }
    }

    private static void simulate() {
        for (int t = 1; t < 1_000_000; t++) {
            robots.forEach(Robot::move);

            char[][] grid = createGrid();
            robots.forEach(r -> grid[r.y][r.x] = '#');

            if (countConnectedComponents(grid) <= 200) {
                System.out.println("Fewest seconds to display the Easter egg: " + t);
                break;
            }
        }
    }

    private static char[][] createGrid() {
        char[][] grid = new char[HEIGHT][WIDTH];
        for (char[] row : grid) Arrays.fill(row, '.');
        return grid;
    }

    private static int countConnectedComponents(char[][] grid) {
        boolean[][] visited = new boolean[HEIGHT][WIDTH];
        int components = 0;

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (grid[y][x] == '#' && !visited[y][x]) {
                    components++;
                    explore(grid, visited, x, y);
                }
            }
        }
        return components;
    }

    private static void explore(char[][] grid, boolean[][] visited, int x, int y) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{x, y});

        while (!queue.isEmpty()) {
            int[] pos = queue.poll();
            int px = pos[0], py = pos[1];

            if (visited[py][px]) continue;
            visited[py][px] = true;

            for (int[] dir : DIRECTIONS) {
                int nx = px + dir[0], ny = py + dir[1];
                if (nx >= 0 && nx < WIDTH && ny >= 0 && ny < HEIGHT && grid[ny][nx] == '#' && !visited[ny][nx]) {
                    queue.add(new int[]{nx, ny});
                }
            }
        }
    }
}
