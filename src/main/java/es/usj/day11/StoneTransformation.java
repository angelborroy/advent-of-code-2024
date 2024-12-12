package es.usj.day11;

import java.util.*;

public class StoneTransformation {
    public static void main(String[] args) {
        String input = "64554 35 906 6 6960985 5755 975820 0";
        List<String> stones = new ArrayList<>(Arrays.asList(input.split(" ")));
        for (int blink = 0; blink < 25; blink++) {
            stones = transformStones(stones);
        }

        System.out.println("Number of stones after 25 blinks: " + stones.size());
    }

    private static List<String> transformStones(List<String> stones) {
        List<String> newStones = new ArrayList<>();

        for (String stone : stones) {
            // Rule 1: If stone is 0, replace with 1
            if (stone.equals("0")) {
                newStones.add("1");
                continue;
            }

            // Rule 2: If stone has even number of digits, split it
            if (stone.length() % 2 == 0) {
                int mid = stone.length() / 2;
                String leftHalf = stone.substring(0, mid);
                String rightHalf = stone.substring(mid);

                // Remove leading zeros
                leftHalf = String.valueOf(Long.parseLong(leftHalf));
                rightHalf = String.valueOf(Long.parseLong(rightHalf));

                newStones.add(leftHalf);
                newStones.add(rightHalf);
                continue;
            }

            // Rule 3: Multiply by 2024
            long num = Long.parseLong(stone);
            newStones.add(String.valueOf(num * 2024));
        }

        return newStones;
    }
}