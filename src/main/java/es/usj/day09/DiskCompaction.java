package es.usj.day09;

import java.util.ArrayList;
import java.util.List;

public class DiskCompaction {
    public static void main(String[] args) throws Exception {
        String input = new String(DiskCompaction.class.getResourceAsStream("/es/usj/day09/input.txt").readAllBytes()).trim();
        System.out.println(solve(input));
    }

    static long solve(String input) {
        List<DiskBlock> files = new ArrayList<>();
        List<DiskBlock> spaces = new ArrayList<>();
        List<Integer> finalState = new ArrayList<>();
        int fileId = 0;
        int pos = 0;

        // Parse input into files and spaces
        for (int i = 0; i < input.length(); i++) {
            int size = Character.getNumericValue(input.charAt(i));
            if (i % 2 == 0) { // File blocks
                for (int j = 0; j < size; j++) {
                    files.add(new DiskBlock(pos + j, 1));
                    finalState.add(fileId);
                }
                fileId++;
            } else { // Space blocks
                spaces.add(new DiskBlock(pos, size));
                for (int j = 0; j < size; j++) {
                    finalState.add(null);
                }
            }
            pos += size;
        }

        // Process files from right to left
        for (int i = files.size() - 1; i >= 0; i--) {
            DiskBlock file = files.get(i);
            for (int j = 0; j < spaces.size(); j++) {
                DiskBlock space = spaces.get(j);
                if (space.pos < file.pos && file.size <= space.size) {
                    // Move file to space
                    int fileFinalId = finalState.get(file.pos);
                    finalState.set(file.pos, null);
                    finalState.set(space.pos, fileFinalId);

                    // Update space
                    spaces.set(j, new DiskBlock(space.pos + file.size, space.size - file.size));
                    break;
                }
            }
        }

        // Calculate checksum
        long checksum = 0;
        for (int i = 0; i < finalState.size(); i++) {
            if (finalState.get(i) != null) {
                checksum += (long) i * finalState.get(i);
            }
        }
        return checksum;
    }

    static class DiskBlock {
        int pos;
        int size;

        DiskBlock(int pos, int size) {
            this.pos = pos;
            this.size = size;
        }
    }
}