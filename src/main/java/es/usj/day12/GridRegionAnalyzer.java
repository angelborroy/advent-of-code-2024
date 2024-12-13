package es.usj.day12;

import java.io.*;
import java.util.*;

public class GridRegionAnalyzer {
    private static final int[][] DIRECTIONS = {
            {-1, 0}, // UP
            {0, 1},  // RIGHT
            {1, 0},  // DOWN
            {0, -1}  // LEFT
    };

    private final List<String> grid;
    private final int rowCount;
    private final int colCount;
    private final Set<Position> visitedCells;
    private long totalAreaScore;

    record Position(int row, int col) {
        @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof Position other)) return false;
                return row == other.row && col == other.col;
            }
    }

    public GridRegionAnalyzer(String filename) throws IOException {
        this.visitedCells = new HashSet<>();
        this.grid = loadGridFromFile(filename);
        this.rowCount = grid.size();
        this.colCount = grid.get(0).length();
        this.totalAreaScore = 0;
    }

    private List<String> loadGridFromFile(String filename) throws IOException {
        List<String> gridData = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream(filename)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                gridData.add(line.trim());
            }
        }
        return gridData;
    }

    public long analyzeGrid() {
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < colCount; col++) {
                Position currentPos = new Position(row, col);
                if (!visitedCells.contains(currentPos)) {
                    analyzeRegion(row, col);
                }
            }
        }
        return totalAreaScore;
    }

    private void analyzeRegion(int startRow, int startCol) {
        Queue<Position> cellsToVisit = new ArrayDeque<>();
        cellsToVisit.add(new Position(startRow, startCol));

        int regionArea = 0;
        Map<Position, Set<Position>> borderCells = new HashMap<>();

        // First pass: Calculate region area and collect border cells
        while (!cellsToVisit.isEmpty()) {
            Position current = cellsToVisit.poll();
            if (visitedCells.contains(current)) {
                continue;
            }

            visitedCells.add(current);
            regionArea++;

            for (int[] dir : DIRECTIONS) {
                int nextRow = current.row + dir[0];
                int nextCol = current.col + dir[1];

                if (isWithinGrid(nextRow, nextCol) &&
                        hasSameValue(nextRow, nextCol, current.row, current.col)) {
                    cellsToVisit.add(new Position(nextRow, nextCol));
                } else {
                    Position direction = new Position(dir[0], dir[1]);
                    borderCells.computeIfAbsent(direction, k -> new HashSet<>())
                            .add(current);
                }
            }
        }

        // Second pass: Count distinct border segments
        int distinctBorderSegments = countDistinctBorderSegments(borderCells);

        // Update total score
        totalAreaScore += (long) regionArea * distinctBorderSegments;
    }

    private int countDistinctBorderSegments(Map<Position, Set<Position>> borderCells) {
        int segments = 0;
        for (Set<Position> borderSection : borderCells.values()) {
            Set<Position> processedBorderCells = new HashSet<>();

            for (Position borderCell : borderSection) {
                if (!processedBorderCells.contains(borderCell)) {
                    segments++;
                    floodFillBorderSegment(borderCell, borderSection, processedBorderCells);
                }
            }
        }
        return segments;
    }

    private void floodFillBorderSegment(Position start, Set<Position> borderSection,
                                        Set<Position> processedCells) {
        Queue<Position> cellsToProcess = new ArrayDeque<>();
        cellsToProcess.add(start);

        while (!cellsToProcess.isEmpty()) {
            Position current = cellsToProcess.poll();
            if (processedCells.contains(current)) {
                continue;
            }

            processedCells.add(current);

            for (int[] dir : DIRECTIONS) {
                Position neighbor = new Position(
                        current.row + dir[0],
                        current.col + dir[1]
                );
                if (borderSection.contains(neighbor)) {
                    cellsToProcess.add(neighbor);
                }
            }
        }
    }

    private boolean isWithinGrid(int row, int col) {
        return row >= 0 && row < rowCount && col >= 0 && col < colCount;
    }

    private boolean hasSameValue(int row1, int col1, int row2, int col2) {
        return grid.get(row1).charAt(col1) == grid.get(row2).charAt(col2);
    }

    public static void main(String[] args) throws Exception {
        GridRegionAnalyzer analyzer = new GridRegionAnalyzer("/es/usj/day12/input.txt");
        System.out.println("Total price of fencing: " + analyzer.analyzeGrid());
    }
}