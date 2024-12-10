package es.usj.day06;

import java.io.*;
import java.util.*;

public class GuardSimulation {
    private static final char WALL = '#';
    private static final char EMPTY = '.';
    private static final char START = '^';

    public static void main(String[] args) throws IOException {
        List<String> lines = readResource();
        char[][] map = createMap(lines);
        int result = countLoopCausingPositions(map);
        System.out.println(result);
    }

    private static List<String> readResource() throws IOException {
        List<String> lines = new ArrayList<>();
        try (InputStream inputStream = GuardSimulation.class.getResourceAsStream("/es/usj/day06/input.txt");
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    private static char[][] createMap(List<String> lines) {
        int rows = lines.size();
        int cols = lines.get(0).length();
        char[][] map = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            map[i] = lines.get(i).toCharArray();
        }
        return map;
    }

    private static int countLoopCausingPositions(char[][] map) {
        int rows = map.length;
        int cols = map[0].length;
        int[] start = findStart(map);
        int sr = start[0], sc = start[1];

        int count = 0;

        for (int o_r = 0; o_r < rows; o_r++) {
            for (int o_c = 0; o_c < cols; o_c++) {
                if (map[o_r][o_c] != EMPTY) continue;

                // Simulate the guard's patrol with the obstruction
                if (simulateWithObstruction(map, sr, sc, o_r, o_c)) {
                    count++;
                }
            }
        }
        return count;
    }

    private static boolean simulateWithObstruction(char[][] map, int sr, int sc, int o_r, int o_c) {
        int rows = map.length;
        int cols = map[0].length;
        int[] dx = {-1, 0, 1, 0}; // Directions: Up, Right, Down, Left
        int[] dy = {0, 1, 0, -1};

        int x = sr, y = sc, d = 0; // Start position and direction
        Set<String> seenStates = new HashSet<>();

        while (true) {
            String state = x + "," + y + "," + d;
            if (seenStates.contains(state)) {
                return true; // Loop detected
            }
            seenStates.add(state);

            int nx = x + dx[d];
            int ny = y + dy[d];

            // Guard exits the map
            if (nx < 0 || nx >= rows || ny < 0 || ny >= cols) {
                return false;
            }

            // Obstruction or wall
            if (map[nx][ny] == WALL || (nx == o_r && ny == o_c)) {
                d = (d + 1) % 4; // Turn right
            } else {
                x = nx;
                y = ny; // Move forward
            }
        }
    }

    private static int[] findStart(char[][] map) {
        for (int r = 0; r < map.length; r++) {
            for (int c = 0; c < map[0].length; c++) {
                if (map[r][c] == START) {
                    map[r][c] = EMPTY; // Replace starting position with empty
                    return new int[]{r, c};
                }
            }
        }
        throw new IllegalArgumentException("Start position not found");
    }
}