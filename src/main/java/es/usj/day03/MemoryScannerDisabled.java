package es.usj.day03;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MemoryScannerDisabled {

    private static final String MUL_PATTERN = "mul\\((\\d{1,3}),(\\d{1,3})\\)";
    private static final String DO_PATTERN = "do\\(\\)";
    private static final String DONT_PATTERN = "don't\\(\\)";

    public static int processMemory(String input) {
        int sum = 0;
        List<Operation> operations = parseOperations(input);
        boolean mulEnabled = true;  // Initially enabled

        for (Operation op : operations) {
            if (op instanceof ControlOperation) {
                mulEnabled = ((ControlOperation) op).isEnabling();
            } else if (op instanceof Multiplication && mulEnabled) {
                sum += ((Multiplication) op).calculate();
            }
        }

        return sum;
    }

    private static List<Operation> parseOperations(String input) {
        List<Operation> operations = new ArrayList<>();

        String combinedPattern = String.format("(%s)|(%s)|(%s)",
                MUL_PATTERN, DO_PATTERN, DONT_PATTERN);
        Pattern pattern = Pattern.compile(combinedPattern);
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String match = matcher.group();
            if (match.startsWith("mul")) {
                Matcher mulMatcher = Pattern.compile(MUL_PATTERN).matcher(match);
                if (mulMatcher.find()) {
                    int x = Integer.parseInt(mulMatcher.group(1));
                    int y = Integer.parseInt(mulMatcher.group(2));
                    operations.add(new Multiplication(x, y));
                }
            } else if (match.equals("do()")) {
                operations.add(new ControlOperation(true));
            } else if (match.equals("don't()")) {
                operations.add(new ControlOperation(false));
            }
        }

        return operations;
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

    private static abstract class Operation {}

    private static class Multiplication extends Operation {
        private final int x;
        private final int y;

        public Multiplication(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int calculate() {
            return x * y;
        }

        @Override
        public String toString() {
            return String.format("mul(%d,%d) = %d", x, y, calculate());
        }
    }

    private static class ControlOperation extends Operation {
        private final boolean enabling;

        public ControlOperation(boolean enabling) {
            this.enabling = enabling;
        }

        public boolean isEnabling() {
            return enabling;
        }

        @Override
        public String toString() {
            return enabling ? "do()" : "don't()";
        }
    }

    public static void main(String[] args) {
        try {
            String corruptedMemory = readFile("es/usj/day03/input.txt");

            int result = processMemory(corruptedMemory);
            System.out.println("Total sum of enabled multiplications: " + result);

        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());
        }
    }
}