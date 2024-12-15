package es.usj.day14;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class RestroomRedoubt {

    private static final int WIDTH = 101;
    private static final int HEIGHT = 103;
    private static final int SIMULATION_TIME = 100;
    private static final List<Robot> robots = new ArrayList<>();

    static class Robot {
        int x, y, vx, vy;

        Robot(int x, int y, int vx, int vy) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
        }

        void move() {
            x = (x + vx + WIDTH) % WIDTH;
            y = (y + vy + HEIGHT) % HEIGHT;
        }
    }

    public static void main(String[] args) throws IOException {
        loadRobots();

        for (int t = 0; t < SIMULATION_TIME; t++) {
            robots.forEach(Robot::move);
        }

        int[] quadrants = calculateQuadrants();
        System.out.println("Safety Factor: " + Arrays.stream(quadrants).reduce(1, (a, b) -> a * b));
    }

    private static void loadRobots() throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                RestroomRedoubt.class.getClassLoader().getResourceAsStream("es/usj/day14/input.txt")))) {
            String line;
            Pattern pattern = Pattern.compile("p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+)");

            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line.trim());
                if (matcher.matches()) {
                    robots.add(new Robot(
                            Integer.parseInt(matcher.group(1)),
                            Integer.parseInt(matcher.group(2)),
                            Integer.parseInt(matcher.group(3)),
                            Integer.parseInt(matcher.group(4))
                    ));
                }
            }
        }
    }

    private static int[] calculateQuadrants() {
        int midX = WIDTH / 2, midY = HEIGHT / 2;
        int[] quadrants = new int[4];

        for (Robot robot : robots) {
            if (robot.x == midX || robot.y == midY) continue; // Skip middle-axis robots

            if (robot.x < midX && robot.y < midY) quadrants[0]++;
            else if (robot.x >= midX && robot.y < midY) quadrants[1]++;
            else if (robot.x < midX) quadrants[2]++;
            else quadrants[3]++;
        }

        return quadrants;
    }

}