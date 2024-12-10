package es.usj.day03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MemoryScanner {

    private static final String MUL_PATTERN = "mul\\((\\d{1,3}),(\\d{1,3})\\)";

    public static int processMemory(String input) {
        int sum = 0;
        List<Multiplication> multiplications = findValidMultiplications(input);

        for (Multiplication mult : multiplications) {
            sum += mult.calculate();
        }

        return sum;
    }

    private static List<Multiplication> findValidMultiplications(String input) {
        List<Multiplication> results = new ArrayList<>();
        Pattern pattern = Pattern.compile(MUL_PATTERN);
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            int x = Integer.parseInt(matcher.group(1));
            int y = Integer.parseInt(matcher.group(2));
            results.add(new Multiplication(x, y));
        }

        return results;
    }

    private static String readFile(String filename) throws IOException {
        StringBuilder content = new StringBuilder();
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        }
        return content.toString();
    }

    public static void main(String[] args) {
        try {
            String corruptedMemory = readFile("es/usj/day03/input.txt");

            int result = processMemory(corruptedMemory);
            System.out.println("Total sum: " + result);

        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
        }
    }

    private record Multiplication(int x, int y) {

        public int calculate() {
            return x * y;
        }

        @Override
        public String toString() {
            return String.format("mul(%d,%d) = %d", x, y, calculate());
        }
    }
}