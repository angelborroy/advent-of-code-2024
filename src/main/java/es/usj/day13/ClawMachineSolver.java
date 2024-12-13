package es.usj.day13;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class ClawMachineSolver {

    static class Machine {
        int buttonAX, buttonAY, buttonBX, buttonBY, prizeX, prizeY;

        Machine(int ax, int ay, int bx, int by, int px, int py) {
            buttonAX = ax;
            buttonAY = ay;
            buttonBX = bx;
            buttonBY = by;
            prizeX = px;
            prizeY = py;
        }
    }

    public static void main(String[] args) {
        try {
            List<Machine> machines = readInput();
            int totalTokens = machines.stream()
                    .mapToInt(ClawMachineSolver::calculateTokens)
                    .sum();
            System.out.println("Minimum tokens needed: " + totalTokens);
        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
        }
    }

    private static List<Machine> readInput() throws IOException {
        List<Machine> machines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        ClawMachineSolver.class.getClassLoader().getResourceAsStream("es/usj/day13/input.txt")))) {

            Pattern buttonPattern = Pattern.compile("Button ([AB]): X\\+(\\d+), Y\\+(\\d+)");
            Pattern prizePattern = Pattern.compile("Prize: X=(\\d+), Y=(\\d+)");

            int ax = 0, ay = 0, bx = 0, by = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher buttonMatcher = buttonPattern.matcher(line);
                Matcher prizeMatcher = prizePattern.matcher(line);

                if (buttonMatcher.find()) {
                    int x = Integer.parseInt(buttonMatcher.group(2));
                    int y = Integer.parseInt(buttonMatcher.group(3));
                    if (buttonMatcher.group(1).equals("A")) {
                        ax = x;
                        ay = y;
                    } else {
                        bx = x;
                        by = y;
                    }
                } else if (prizeMatcher.find()) {
                    int px = Integer.parseInt(prizeMatcher.group(1));
                    int py = Integer.parseInt(prizeMatcher.group(2));
                    machines.add(new Machine(ax, ay, bx, by, px, py));
                }
            }
        }
        return machines;
    }

    private static int calculateTokens(Machine m) {
        for (int a = 0; a <= 100; a++) {
            for (int b = 0; b <= 100; b++) {
                if (a * m.buttonAX + b * m.buttonBX == m.prizeX &&
                        a * m.buttonAY + b * m.buttonBY == m.prizeY) {
                    return a * 3 + b;
                }
            }
        }
        return 0;
    }
}
