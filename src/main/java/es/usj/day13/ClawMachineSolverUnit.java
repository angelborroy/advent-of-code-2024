package es.usj.day13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClawMachineSolverUnit {

    static class Machine {
        long buttonAX, buttonAY, buttonBX, buttonBY, prizeX, prizeY;
        static final long OFFSET = 10000000000000L;

        Machine(long buttonAX, long buttonAY, long buttonBX, long buttonBY, long prizeX, long prizeY) {
            this.buttonAX = buttonAX;
            this.buttonAY = buttonAY;
            this.buttonBX = buttonBX;
            this.buttonBY = buttonBY;
            this.prizeX = prizeX + OFFSET;
            this.prizeY = prizeY + OFFSET;
        }
    }

    public static void main(String[] args) {
        try {
            List<Machine> machines = readInput();
            long totalTokens = calculateTokens(machines);
            System.out.println("Total minimum tokens needed: " + totalTokens);
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

            long buttonAX = 0, buttonAY = 0, buttonBX = 0, buttonBY = 0, prizeX = 0, prizeY = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                Matcher buttonMatcher = buttonPattern.matcher(line);
                Matcher prizeMatcher = prizePattern.matcher(line);

                if (buttonMatcher.find()) {
                    long x = Long.parseLong(buttonMatcher.group(2));
                    long y = Long.parseLong(buttonMatcher.group(3));
                    if ("A".equals(buttonMatcher.group(1))) {
                        buttonAX = x;
                        buttonAY = y;
                    } else {
                        buttonBX = x;
                        buttonBY = y;
                    }
                } else if (prizeMatcher.find()) {
                    prizeX = Long.parseLong(prizeMatcher.group(1));
                    prizeY = Long.parseLong(prizeMatcher.group(2));
                    machines.add(new Machine(buttonAX, buttonAY, buttonBX, buttonBY, prizeX, prizeY));
                }
            }
        }
        return machines;
    }

    private static long calculateTokens(List<Machine> machines) {
        long totalTokens = 0;
        for (Machine m : machines) {
            Long cost = solveMachine(m);
            if (cost != null) {
                totalTokens += cost;
            }
        }
        return totalTokens;
    }

    private static Long solveMachine(Machine m) {
        long Ax = m.buttonAX, Ay = m.buttonAY, Bx = m.buttonBX, By = m.buttonBY;
        long Px = m.prizeX, Py = m.prizeY;

        long D = Ax * By - Ay * Bx;
        if (D == 0) {
            return solveCollinear(Ax, Ay, Bx, By, Px, Py);
        }

        long a = (Px * By - Py * Bx) / D;
        long b = (Ax * Py - Ay * Px) / D;

        if ((Px * By - Py * Bx) % D != 0 || (Ax * Py - Ay * Px) % D != 0 || a < 0 || b < 0) {
            return null;
        }

        return 3 * a + b;
    }

    private static Long solveCollinear(long Ax, long Ay, long Bx, long By, long Px, long Py) {
        if (Ax * Py != Ay * Px) {
            return null;
        }

        long t = (Ax != 0) ? Px / Ax : Py / Ay;
        long lambda = (Ax != 0) ? Bx / Ax : By / Ay;

        if (t < 0 || (Ax != 0 && Px % Ax != 0) || (Ay != 0 && Py % Ay != 0)) {
            return null;
        }

        long bMax = (lambda > 0) ? t / lambda : 0;
        long a = t - lambda * bMax;

        if (a < 0) {
            return null;
        }

        return Math.min(3 * a + bMax, 3 * t);
    }
}
