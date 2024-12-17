### Day 17: Chronospatial Computer

The Historians’ device turns into a 3-bit computer with three registers: **A**, **B**, and **C** (holding any integer values). Programs consist of a sequence of **3-bit numbers** (0–7). Each instruction is identified by an **opcode** (a 3-bit number) and is followed by an **operand** (another 3-bit number).

The computer processes instructions one by one, incrementing the instruction pointer by 2 (opcode + operand) unless a jump occurs. If the pointer reaches the program's end, the computer halts.

### Operand Types:
- **Literal**: The operand itself (e.g., 7 → value 7).
- **Combo**: Values depend on the operand:
    - 0–3 → Literal 0–3
    - 4 → Value in Register A
    - 5 → Value in Register B
    - 6 → Value in Register C
    - 7 → Reserved (invalid)

### Instructions (Opcodes 0–7):

1. **`adv` (0):** Divide `A` by \(2^{\text{operand}}\), truncating the result, and store in `A`.
2. **`bxl` (1):** XOR `B` with the **literal operand** and store in `B`.
3. **`bst` (2):** Set `B` to `operand % 8`.
4. **`jnz` (3):** Jump to the **literal operand** if `A` ≠ 0.
5. **`bxc` (4):** XOR `B` with `C`. (Operand is ignored.)
6. **`out` (5):** Output `operand % 8`.
7. **`bdv` (6):** Like `adv`, but store the result in `B`.
8. **`cdv` (7):** Like `adv`, but store the result in `C`.

### Example Behavior:
- Program `5,0,5,1,5,4` outputs: **0,1,2**.
- Program `0,1,5,4,3,0` with `A = 2024` outputs: **4,2,5,6,7,7,7,7,3,1,0**.

---

### Part 1:

**es.usj.day17.ThreeBitComputer**

Given register values and a program, run the program and collect outputs from `out` instructions, combining them into a comma-separated string.

---

### Part 2:

**es.usj.day17.FindInitialA**

Find the lowest positive value for `A` that causes the program to **output a copy of itself**. For example:
- With `A = 2024`, the program doesn’t work.
- Setting `A = 117440` makes the program output a copy of itself.

---

