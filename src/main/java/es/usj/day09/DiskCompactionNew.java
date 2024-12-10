package es.usj.day09;

import java.util.ArrayList;
import java.util.List;

public class DiskCompactionNew {
    public static void main(String[] args) throws Exception {
        String input = new String(DiskCompaction.class.getResourceAsStream("/es/usj/day09/input.txt").readAllBytes()).trim();
        System.out.println(solve(input));
    }

    static long solve(String input) {
        List<FileBlock> files = new ArrayList<>();
        List<Space> spaces = new ArrayList<>();
        List<Integer> finalState = new ArrayList<>();
        int fileId = 0;
        int pos = 0;

        // Parse input
        for (int i = 0; i < input.length(); i++) {
            int size = Character.getNumericValue(input.charAt(i));
            if (i % 2 == 0) { // File
                files.add(new FileBlock(pos, size, fileId));
                for (int j = 0; j < size; j++) {
                    finalState.add(fileId);
                }
                fileId++;
            } else { // Space
                spaces.add(new Space(pos, size));
                for (int j = 0; j < size; j++) {
                    finalState.add(null);
                }
            }
            pos += size;
        }

        // Process files from right to left
        for (int i = files.size() - 1; i >= 0; i--) {
            FileBlock file = files.get(i);
            for (int j = 0; j < spaces.size(); j++) {
                Space space = spaces.get(j);
                if (space.pos < file.pos && file.size <= space.size) {
                    // Move file blocks to space
                    for (int k = 0; k < file.size; k++) {
                        finalState.set(file.pos + k, null);
                        finalState.set(space.pos + k, file.fileId);
                    }
                    // Update space
                    spaces.set(j, new Space(space.pos + file.size, space.size - file.size));
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

    static class FileBlock {
        int pos;
        int size;
        int fileId;

        FileBlock(int pos, int size, int fileId) {
            this.pos = pos;
            this.size = size;
            this.fileId = fileId;
        }
    }

    static class Space {
        int pos;
        int size;

        Space(int pos, int size) {
            this.pos = pos;
            this.size = size;
        }
    }
}