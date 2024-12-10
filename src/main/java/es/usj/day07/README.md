**Simplified Statement for Day 7: Bridge Repair**

---

### Part 1: Valid Equations with Addition and Multiplication

**es.usj.day07.CalibrationSolver**

You are given calibration equations in this format:

```
TestValue: Number1 Number2 Number3 ...
```

- The goal is to determine if the given `TestValue` can be produced by placing **addition (+)** and **multiplication (*)** operators between the numbers.
- Operators are evaluated **left-to-right** (not by precedence), and the order of numbers cannot be rearranged.

**Steps**:

1. For each equation:
    - Test all possible combinations of `+` and `*` between the numbers.
    - Check if any combination equals the `TestValue`.
2. If the equation is valid, add its `TestValue` to the total calibration result.

**Input**: A list of equations with test values and numbers.  
**Output**: The sum of `TestValue` for all valid equations using `+` and `*`.

---

### Part 2: Include Concatenation Operator

**es.usj.day07.CalibrationSolverConcatenation**

Now, include the **concatenation operator (`||`)**:

- Concatenation combines two numbers into a single number. For example, `12 || 34` becomes `1234`.
- All operators (`+`, `*`, `||`) are still evaluated **left-to-right**.

**Steps**:

1. For each equation:
    - Test all possible combinations of `+`, `*`, and `||` between the numbers.
    - Check if any combination equals the `TestValue`.
2. If the equation is valid, add its `TestValue` to the total calibration result.

**Input**: A list of equations with test values and numbers.  
**Output**: The sum of `TestValue` for all valid equations using `+`, `*`, and `||`.

---

**Examples**:

For the equations:
```
190: 10 19
3267: 81 40 27
83: 17 5
156: 15 6
7290: 6 8 6 15
161011: 16 10 13
192: 17 8 14
```

- **Part 1 Output**: 3749  
  (Valid equations: `190`, `3267`, `292`).

- **Part 2 Output**: 11387  
  (Valid equations: `190`, `3267`, `292`, `156`, `7290`, `192`).