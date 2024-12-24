package es.usj.day24;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class LogicGateSimulation {

    static class Gate {
        String input1, input2, output, operation;

        Gate(String input1, String input2, String output, String operation) {
            this.input1 = input1;
            this.input2 = input2;
            this.output = output;
            this.operation = operation;
        }
    }

    public static void main(String[] args) throws Exception {
        Map<String, Long> wireValues = new HashMap<>();
        List<Gate> gates = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                LogicGateSimulation.class.getClassLoader().getResourceAsStream("es/usj/day24/input.txt")))) {
            boolean parsingValues = true;

            for (String line; (line = reader.readLine()) != null; ) {
                line = line.trim();
                if (line.isEmpty()) {
                    parsingValues = false;
                    continue;
                }

                if (parsingValues) {
                    String[] parts = line.split(":");
                    wireValues.put(parts[0].trim(), Long.parseLong(parts[1].trim()));
                } else {
                    String[] parts = line.split("->");
                    String[] inputs = parts[0].trim().split(" ");
                    gates.add(new Gate(inputs[0], inputs[2], parts[1].trim(), inputs[1]));
                }
            }
        }

        while (processGates(gates, wireValues)) { }

        List<String> zWires = wireValues.keySet().stream()
                .filter(wire -> wire.startsWith("z"))
                .sorted(Comparator.comparingInt(w -> Integer.parseInt(w.substring(1))))
                .toList();

        StringBuilder binary = new StringBuilder();
        for (int i = zWires.size() - 1; i >= 0; i--) {
            binary.append(wireValues.get(zWires.get(i)));
        }

        System.out.println("The decimal number output is: " + Long.parseLong(binary.toString(), 2));
    }

    private static boolean processGates(List<Gate> gates, Map<String, Long> wireValues) {
        boolean changed = false;
        for (Gate gate : gates) {
            if (!wireValues.containsKey(gate.output) &&
                    wireValues.containsKey(gate.input1) &&
                    wireValues.containsKey(gate.input2)) {

                long input1Value = wireValues.get(gate.input1);
                long input2Value = wireValues.get(gate.input2);

                long result = switch (gate.operation) {
                    case "AND" -> input1Value & input2Value;
                    case "OR" -> input1Value | input2Value;
                    case "XOR" -> input1Value ^ input2Value;
                    default -> throw new IllegalArgumentException("Unknown operation: " + gate.operation);
                };

                wireValues.put(gate.output, result);
                changed = true;
            }
        }
        return changed;
    }
}
