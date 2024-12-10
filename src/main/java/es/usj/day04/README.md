**Simplified Statement for Day 4: Ceres Search**

---

### Part 1: Count All "XMAS" Occurrences

**es.usj.day04.SimplifiedXMASWordSearch**

You are given a 2D word search grid of letters. Count how many times the word **XMAS** appears in the grid, considering:

1. Words can appear horizontally (left-to-right or right-to-left).
2. Words can appear vertically (top-to-bottom or bottom-to-top).
3. Words can appear diagonally (in any direction).

**Input**: A 2D grid of characters.  
**Output**: The total number of times "XMAS" appears.

---

### Part 2: Count "X-MAS" Patterns

**es.usj.day04.SimplifiedXMASFinder**

Now, count how many **X-MAS** patterns appear in the grid. An **X-MAS** pattern is defined as two "MAS" words forming the shape of an "X":

- Each "MAS" can be forwards or backwards.
- The letters in the two "MAS" words must cross at their "A."

**Input**: A 2D grid of characters.  
**Output**: The total number of "X-MAS" patterns.

---

**Examples**:
For the grid:
```
MMMSXXMASM
MSAMXMSMSA
AMXSXMAAMM
MSAMASMSMX
XMASAMXAMM
XXAMMXXAMA
SMSMSASXSS
SAXAMASAAA
MAMMMXMMMM
MXMXAXMASX
```

- **Part 1 Output**: 18 (total "XMAS" occurrences).
- **Part 2 Output**: 9 (total "X-MAS" patterns).