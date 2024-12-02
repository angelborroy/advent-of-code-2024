package es.usj.day01;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HistorianPuzzle {

    public static void main(String[] args) {

        List<Integer> leftList = new ArrayList<>();
        List<Integer> rightList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(HistorianPuzzle.class.getResourceAsStream("/es/usj/day01/input.txt")))) {
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

        Collections.sort(leftList);
        Collections.sort(rightList);

        long totalDistance = 0;
        for (int i = 0; i < leftList.size(); i++) {
            int distance = Math.abs(leftList.get(i) - rightList.get(i));
            totalDistance += distance;
        }

        System.out.println("Total distance: " + totalDistance);
    }
}