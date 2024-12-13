package es.usj.day12;

import java.util.*;

public class GardenFencing {
    private final char[][] grid;
    private final boolean[][] visited;
    private final int rows, cols;
    private static final int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};

    public GardenFencing(String input) {
        String[] lines = input.trim().split("\n");
        rows = lines.length;
        cols = lines[0].length();
        grid = new char[rows][cols];
        visited = new boolean[rows][cols];

        for (int i = 0; i < rows; i++) {
            grid[i] = lines[i].toCharArray();
        }
    }

    public long calculateTotalPrice() {
        long totalPrice = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!visited[i][j]) {
                    totalPrice += calculateRegion(i, j);
                }
            }
        }
        return totalPrice;
    }

    private long calculateRegion(int x, int y) {
        char type = grid[x][y];
        int area = 0, perimeter = 0;
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{x, y});
        visited[x][y] = true;

        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            area++;

            for (int i = 0; i < 4; i++) {
                int nx = cell[0] + dx[i], ny = cell[1] + dy[i];

                if (nx < 0 || ny < 0 || nx >= rows || ny >= cols || grid[nx][ny] != type) {
                    perimeter++;
                } else if (!visited[nx][ny]) {
                    visited[nx][ny] = true;
                    queue.add(new int[]{nx, ny});
                }
            }
        }
        return (long) area * perimeter;
    }

    public static void main(String[] args) throws Exception {
        String input = new String(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("es/usj/day12/input.txt").readAllBytes());
        System.out.println("Total price of fencing: " + new GardenFencing(input).calculateTotalPrice());
    }
}
