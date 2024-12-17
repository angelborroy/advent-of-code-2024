package es.usj.day17;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class ThreeBitComputer {
    private final long[] registers = new long[3]; // Registers A, B, C (indexes 0, 1, 2)
    private final List<Integer> program = new ArrayList<>();
    private final List<Integer> output = new ArrayList<>();
    private int ip = 0; // Instruction Pointer

    public void loadProgram() throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream("es/usj/day17/input.txt")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                parseLine(line);
            }
        }
    }

    private void parseLine(String line) {
        Matcher m = Pattern.compile("Register ([ABC]): (-?\\d+)|Program: (.+)").matcher(line);
        if (m.find()) {
            if (m.group(1) != null) registers[m.group(1).charAt(0) - 'A'] = Long.parseLong(m.group(2));
            else Arrays.stream(m.group(3).split(",")).map(String::trim).mapToInt(Integer::parseInt).forEach(program::add);
        }
    }

    private long getOperandValue(int operand) {
        return switch (operand) {
            case 0, 1, 2, 3 -> operand;
            case 4 -> registers[0]; // Register A
            case 5 -> registers[1]; // Register B
            case 6 -> registers[2]; // Register C
            default -> throw new IllegalArgumentException("Invalid operand: " + operand);
        };
    }

    private void executeInstruction() {
        if (ip >= program.size()) return;
        int opcode = program.get(ip++), operand = program.get(ip++);
        long value = getOperandValue(operand), power = 1L << value;
        switch (opcode) {
            case 0 -> registers[0] /= power;        // adv
            case 1 -> registers[1] ^= operand;      // bxl
            case 2 -> registers[1] = value % 8;     // bst
            case 3 -> ip = (registers[0] != 0) ? operand : ip; // jnz
            case 4 -> registers[1] ^= registers[2]; // bxc
            case 5 -> output.add((int) (value % 8));// out
            case 6 -> registers[1] = registers[0] / power; // bdv
            case 7 -> registers[2] = registers[0] / power; // cdv
            default -> throw new IllegalArgumentException("Invalid opcode: " + opcode);
        }
    }

    public void run() {
        while (ip < program.size()) executeInstruction();
    }

    public String getOutput() {
        return String.join(",", output.stream().map(String::valueOf).toList());
    }

    public static void main(String[] args) {
        try {
            ThreeBitComputer computer = new ThreeBitComputer();
            computer.loadProgram();
            computer.run();
            System.out.println(computer.getOutput());
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
