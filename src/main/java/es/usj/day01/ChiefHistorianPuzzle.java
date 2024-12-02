package es.usj.day01;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChiefHistorianPuzzle {
    public static void main(String[] args) {

        List<Integer> leftList = new ArrayList<>();
        List<Integer> rightList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(ChiefHistorianPuzzle.class.getResourceAsStream("/es/usj/day01/input.txt")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String left = line.substring(0, 5).trim();
                String right = line.substring(8, 13).trim();

                leftList.add(Integer.parseInt(left));
                rightList.add(Integer.parseInt(right));
            }
        } catch (Exception e) {
            System.err.println("Error reading input file: " + e.getMessage());
            System.exit(1);
        }

        Map<Integer, Integer> rightCounts = new HashMap<>();
        for (int num : rightList) {
            rightCounts.put(num, rightCounts.getOrDefault(num, 0) + 1);
        }

        long similarityScore = 0;
        for (int leftNum : leftList) {
            int occurrences = rightCounts.getOrDefault(leftNum, 0);
            long contribution = (long) leftNum * occurrences;
            similarityScore += contribution;
        }

        System.out.println("Similarity score: " + similarityScore);
    }
}
