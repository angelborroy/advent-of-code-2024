package es.usj.day15;

import java.io.*;
import java.util.*;

public class WarehouseRobot {
    public static void main(String[] args) {
        try {
            // Read the input from input.txt
            List<String> lines = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader("/Users/angel.fernandoborroy/Downloads/input.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    lines.add(line);
                }
            }

            // Separate the map lines from the move lines
            // In the provided examples, the map is followed by many lines of moves.
            // We'll assume the map ends once we encounter a line that doesn't match the width or
            // after we read an empty line after the map.
            // However, the problem states a large map followed by moves.
            // We must guess where the map ends and moves begin.
            // The safest approach is:
            // 1) Identify the map: it usually starts and ends with a line of # characters forming a boundary.
            // 2) After the final boundary line, everything else is moves.

            // Find map boundaries. We'll assume the first line is all '#', and that defines map width.
            int mapStart = 0;
            while (mapStart < lines.size() && lines.get(mapStart).trim().isEmpty()) {
                mapStart++;
            }

            if (mapStart >= lines.size()) {
                throw new RuntimeException("No map found in input.");
            }

            int mapWidth = lines.get(mapStart).length();
            int mapEnd = mapStart;
            while (mapEnd < lines.size() && lines.get(mapEnd).length() == mapWidth) {
                mapEnd++;
            }

            // Extract map lines
            List<String> mapLines = lines.subList(mapStart, mapEnd);
            // The remaining lines are moves
            List<String> moveLines = lines.subList(mapEnd, lines.size());

            // Construct the warehouse grid
            char[][] warehouse = new char[mapLines.size()][mapWidth];
            for (int r = 0; r < mapLines.size(); r++) {
                warehouse[r] = mapLines.get(r).toCharArray();
            }

            // Concatenate all moves into a single string
            StringBuilder movesBuilder = new StringBuilder();
            for (String ml : moveLines) {
                movesBuilder.append(ml.trim());
            }
            String moves = movesBuilder.toString().replace("\n", "").replace("\r", "");

            // Find the robot position
            int robotRow = -1;
            int robotCol = -1;
            for (int r = 0; r < warehouse.length; r++) {
                for (int c = 0; c < warehouse[r].length; c++) {
                    if (warehouse[r][c] == '@') {
                        robotRow = r;
                        robotCol = c;
                        break;
                    }
                }
                if (robotRow != -1) break;
            }

            if (robotRow == -1 || robotCol == -1) {
                throw new RuntimeException("Robot (@) not found in the map.");
            }

            // Directions for moves
            // '^' = up (-1,0), 'v' = down (1,0), '<' = left (0,-1), '>' = right (0,1)
            Map<Character, int[]> dirMap = new HashMap<>();
            dirMap.put('^', new int[]{-1, 0});
            dirMap.put('v', new int[]{1, 0});
            dirMap.put('<', new int[]{0, -1});
            dirMap.put('>', new int[]{0, 1});

            // Simulate each move
            for (int i = 0; i < moves.length(); i++) {
                char move = moves.charAt(i);
                if (!dirMap.containsKey(move)) continue; // Ignore invalid characters if any

                int[] d = dirMap.get(move);
                int newR = robotRow + d[0];
                int newC = robotCol + d[1];

                // Check what is at newR, newC
                if (warehouse[newR][newC] == '#') {
                    // Can't move into a wall, do nothing
                    continue;
                } else if (warehouse[newR][newC] == '.') {
                    // Free space, just move the robot
                    warehouse[robotRow][robotCol] = '.';
                    warehouse[newR][newC] = '@';
                    robotRow = newR;
                    robotCol = newC;
                } else if (warehouse[newR][newC] == 'O') {
                    // Attempt to push boxes
                    // Find the chain of boxes in that direction
                    List<int[]> boxPositions = new ArrayList<>();
                    int pushR = newR;
                    int pushC = newC;
                    while (warehouse[pushR][pushC] == 'O') {
                        boxPositions.add(new int[]{pushR, pushC});
                        pushR += d[0];
                        pushC += d[1];
                    }

                    // Now pushR, pushC is the first empty or wall after the chain of boxes
                    if (warehouse[pushR][pushC] == '#') {
                        // Can't push because would push into a wall
                        continue; // No move occurs
                    } else if (warehouse[pushR][pushC] == '.' || warehouse[pushR][pushC] == '@') {
                        // We can push the entire chain forward by one
                        // Move the last box into pushR, pushC
                        warehouse[pushR][pushC] = 'O';
                        // Move all other boxes forward
                        for (int b = boxPositions.size() - 1; b > 0; b--) {
                            int[] prev = boxPositions.get(b - 1);
                            warehouse[boxPositions.get(b)[0]][boxPositions.get(b)[1]] = 'O';
                            warehouse[prev[0]][prev[1]] = '.';
                        }
                        // The first box moves into newR,newC (which was originally the first box)
                        // Actually we already moved them by shifting. Let's clarify:
                        // After shifting, the position of the first box in the chain moves into what was previously its next box.
                        // Let's do it systematically:

                        // Clear the original position of the first box in the chain
                        warehouse[newR][newC] = 'O';
                        // Clear original robot position
                        warehouse[robotRow][robotCol] = '.';
                        // Move robot forward
                        warehouse[robotRow + d[0]][robotCol + d[1]] = '@';
                        robotRow = robotRow + d[0];
                        robotCol = robotCol + d[1];
                    }
                } else if (warehouse[newR][newC] == '@') {
                    // Theoretically shouldn't happen if there's only one robot
                    // Just do nothing
                }
            }

            // After all moves complete, sum up GPS coordinates of all boxes
            // GPS = 100 * row + col (distance from top and left)
            int sum = 0;
            for (int r = 0; r < warehouse.length; r++) {
                for (int c = 0; c < warehouse[r].length; c++) {
                    if (warehouse[r][c] == 'O') {
                        sum += (r * 100) + c;
                    }
                }
            }

            System.out.println(sum);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
