package es.usj.day04;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SimplifiedXMASWordSearch {
    private char[][] grid;
    private static final String TARGET = "XMAS";
    private static final int[][] DIRS = {
            {-1,-1}, {-1,0}, {-1,1}, {0,-1}, {0,1}, {1,-1}, {1,0}, {1,1}
    };

    public int solve(String filename) {
        try (var reader = new BufferedReader(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream(filename)))) {

            grid = reader.lines()
                    .map(String::toCharArray)
                    .toArray(char[][]::new);

            int count = 0;
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (grid[i][j] == 'X') {
                        count += searchFromPoint(i, j);
                    }
                }
            }
            return count;
        } catch (Exception e) {
            return -1;
        }
    }

    private int searchFromPoint(int row, int col) {
        int found = 0;
        for (int[] dir : DIRS) {
            int r = row, c = col;
            int matches = 0;

            for (char target : TARGET.toCharArray()) {
                if (r < 0 || r >= grid.length || c < 0 || c >= grid[0].length
                        || grid[r][c] != target) {
                    break;
                }
                matches++;
                r += dir[0];
                c += dir[1];
            }
            if (matches == TARGET.length()) found++;
        }
        return found;
    }

    public static void main(String[] args) {
        System.out.println("XMAS count: " + new SimplifiedXMASWordSearch().solve("es/usj/day04/input.txt"));
    }
}