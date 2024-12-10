**Simplified Statement for Day 8: Resonant Collinearity**

---

### Part 1: Calculate Antinodes by Distance Rule

**es.usj.day08.AntennaAnalyzer**

You are given a 2D grid map showing:

- Antennas with specific frequencies (letters or digits).
- Empty spaces (represented as `.`).

Antinodes are created by pairs of antennas with the **same frequency** and must satisfy the following conditions:

1. The two antennas are perfectly in line (vertically, horizontally, or diagonally).
2. One antenna is **exactly twice as far** from the antinode as the other.

**Steps**:

1. Identify all pairs of antennas with the same frequency.
2. For each pair, calculate the antinodes they create based on the distance rule.
3. Count all **unique positions** containing an antinode within the grid's bounds.

**Input**: A 2D grid map.  
**Output**: The number of unique positions containing antinodes.

---

### Part 2: Updated Antinode Rule (Harmonics)

**es.usj.day08.AntennaAnalyzerUpdated**

Now, antinodes occur at **any position exactly in line with at least two antennas of the same frequency**, regardless of distance.

**Steps**:

1. Identify all groups of two or more antennas with the same frequency.
2. For each group:
    - Mark all positions along straight lines (vertical, horizontal, or diagonal) between any two antennas in the group as antinodes.
    - Include the positions of all antennas in the group as antinodes.
3. Count all **unique positions** containing an antinode within the grid's bounds.

**Input**: A 2D grid map.  
**Output**: The number of unique positions containing antinodes.

---

**Examples**:

For the grid:
```
......#....#
...#....0...
....#0....#.
..#....0....
....0....#..
.#....A.....
...#........
#......#....
........A...
.........A..
..........#.
..........#.
```

- **Part 1 Output**: 14 (unique positions with antinodes based on the distance rule).
- **Part 2 Output**: 34 (unique positions with antinodes, including harmonics and antenna positions).