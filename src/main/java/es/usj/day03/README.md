**Simplified Statement for Day 3: Mull It Over**

---

### Part 1: Sum of Valid Multiplications

**es.usj.day03.MemoryScanner**

Scan a corrupted memory for valid `mul(X,Y)` instructions. A valid instruction has the format `mul(X,Y)` where `X` and `Y` are numbers (1-3 digits). Ignore invalid or malformed instructions.

1. Identify all valid `mul(X,Y)` instructions.
2. Multiply the two numbers in each instruction.
3. Sum the results of all valid multiplications.

**Input**: A string representing corrupted memory.  
**Output**: The sum of results from valid `mul(X,Y)` instructions.

---

### Part 2: Conditional Multiplications

**es.usj.day03.MemoryScannerDisabled**

Add support for `do()` and `don't()` instructions to enable or disable multiplications:

1. At the start, multiplications are enabled.
2. A `do()` instruction enables future `mul(X,Y)` instructions.
3. A `don't()` instruction disables future `mul(X,Y)` instructions.
4. Process `mul(X,Y)` instructions only if they are enabled.

**Input**: A string representing corrupted memory.  
**Output**: The sum of results from valid and enabled `mul(X,Y)` instructions.

---

**Examples**:
For the corrupted memory:
```
xmul(2,4)&mul[3,7]!^do()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))
```

- **Part 1 Output**: 161
- **Part 2 Output**: 48