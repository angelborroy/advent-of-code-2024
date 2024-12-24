package es.usj.day24;

import java.io.*;
import java.util.*;
import java.nio.charset.StandardCharsets;

public class ParallelSuspectSwapper {

    static class Gate {
        String in1, op, in2, out;
        Gate(String i1, String op, String i2, String o) {
            this.in1 = i1; this.op = op; this.in2 = i2; this.out = o;
        }
        Gate copy() { return new Gate(in1, op, in2, out); }
    }

    private static List<Gate> swapGates(List<Gate> gates, int i1, int i2) {
        List<Gate> copy = new ArrayList<>(gates.size());
        for (Gate g : gates) copy.add(g.copy());
        String tmp = copy.get(i1).out;
        copy.get(i1).out = copy.get(i2).out;
        copy.get(i2).out = tmp;
        return copy;
    }

    private static int checkCarryChain(List<Gate> gates) {
        String carryForward = "";

        for (int i = 0; i <= 44; i++) {
            String x = String.format("x%02d", i);
            String y = String.format("y%02d", i);
            String z = String.format("z%02d", i);

            String digit1 = findGateOutput(gates, x, "XOR", y);
            String carry1 = findGateOutput(gates, x, "AND", y);

            if (digit1.isEmpty() || carry1.isEmpty()) return i;

            if (i == 0) {
                if (!digit1.equals(z)) return 0;
                carryForward = carry1;
            } else {
                String digit2 = findGateOutput(gates, carryForward, "XOR", digit1);
                if (digit2.isEmpty() || !digit2.equals(z)) return i;

                String newCarry = findGateOutput(gates, carryForward, "AND", digit1);
                carryForward = findGateOutput(gates, carry1, "OR", newCarry);

                if (newCarry.isEmpty() || carryForward.isEmpty()) return i;
            }
        }
        return 45;
    }

    private static String findGateOutput(List<Gate> gates, String in1, String op, String in2) {
        for (Gate g : gates) {
            if ((g.in1.equals(in1) && g.in2.equals(in2) && g.op.equals(op)) ||
                (g.in1.equals(in2) && g.in2.equals(in1) && g.op.equals(op))) {
                return g.out;
            }
        }
        return "";
    }

    public static void main(String... args) throws IOException {
        InputStream inputStream = ParallelSuspectSwapper.class.getClassLoader().getResourceAsStream("es/usj/day24/input.txt");
        String content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        String[] parts = content.trim().split("\n\n");
        
        List<Gate> gates = new ArrayList<>();
        for (String ln : parts[1].split("\n")) {
            ln = ln.trim();
            if (!ln.isEmpty()) {
                String[] tok = ln.split("\\s+");
                if (tok.length == 5 && "->".equals(tok[3])) {
                    gates.add(new Gate(tok[0], tok[1], tok[2], tok[4]));
                }
            }
        }

        List<Gate> current = gates;
        Set<String> swappedOutputs = new HashSet<>();
        
        for (int round = 0; round < 4; round++) {
            int bestLength = 0;
            int bestI = -1;
            int bestJ = -1;

            for (int i = 0; i < gates.size(); i++) {
                if (swappedOutputs.contains(current.get(i).out)) continue;
                
                for (int j = i + 1; j < gates.size(); j++) {
                    if (swappedOutputs.contains(current.get(j).out)) continue;
                    
                    List<Gate> candidate = swapGates(current, i, j);
                    int chainLength = checkCarryChain(candidate);
                    
                    if (chainLength > bestLength) {
                        bestLength = chainLength;
                        bestI = i;
                        bestJ = j;
                        if (chainLength == 45) break;
                    }
                }
            }

            if (bestI == -1) break;

            current = swapGates(current, bestI, bestJ);
            swappedOutputs.add(current.get(bestI).out);
            swappedOutputs.add(current.get(bestJ).out);
            
            if (bestLength == 45) break;
        }

        List<String> finalSwaps = new ArrayList<>(swappedOutputs);
        Collections.sort(finalSwaps);
        System.out.println("\n== Final swapped wires ==");
        System.out.println(String.join(",", finalSwaps));
    }
}


