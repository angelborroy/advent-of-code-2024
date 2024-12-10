package es.usj.day04;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SimplifiedXMASFinder {
    private final char[][] grid = new char[140][140];

    public void loadInput() {
        try (var reader = new BufferedReader(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream("es/usj/day04/input.txt")))) {

            String line;
            int row = 0;
            while ((line = reader.readLine()) != null) {
                grid[row++] = line.toCharArray();
            }
        } catch (Exception e) {
            System.err.println("Error reading input: " + e.getMessage());
        }
    }

    public int countXMASPatterns() {
        int count = 0;
        for (int r = 1; r < 139; r++) {
            for (int c = 1; c < 139; c++) {
                if (grid[r][c] == 'A') {
                    count += countPatternsAt(r, c);
                }
            }
        }
        return count;
    }

    private int countPatternsAt(int r, int c) {
        int patterns = 0;
        for (boolean d1Forward : new boolean[]{true, false}) {
            for (boolean d2Forward : new boolean[]{true, false}) {
                char[] diag1 = {grid[r-1][c-1], grid[r][c], grid[r+1][c+1]};
                char[] diag2 = {grid[r-1][c+1], grid[r][c], grid[r+1][c-1]};

                if (isValidMAS(diag1, d1Forward) && isValidMAS(diag2, d2Forward)) {
                    patterns++;
                }
            }
        }
        return patterns;
    }

    private boolean isValidMAS(char[] chars, boolean forward) {
        return forward ?
                chars[0] == 'M' && chars[1] == 'A' && chars[2] == 'S' :
                chars[0] == 'S' && chars[1] == 'A' && chars[2] == 'M';
    }

    public static void main(String[] args) {
        SimplifiedXMASFinder finder = new SimplifiedXMASFinder();
        finder.loadInput();
        System.out.println("X-MAS patterns found: " + finder.countXMASPatterns());
    }
}