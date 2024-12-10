**Simplified Statement for Day 10: Hoof It**

---

### Part 1: Trailhead Scores

**es.usj.day10.HikingTrails**

You are given a **topographic map**, a 2D grid of numbers (0–9) representing heights. A **hiking trail**:

1. Starts at a height `0` (trailhead).
2. Ends at a height `9`.
3. Always increases by exactly 1 per step.
4. Moves only up, down, left, or right (no diagonal steps).

Each trailhead's **score** is the number of height `9` positions reachable from it via valid hiking trails.

**Steps**:

1. Identify all trailheads (positions with height `0`).
2. For each trailhead, count how many height `9` positions can be reached by valid hiking trails.
3. Sum the scores of all trailheads.

**Input**: A 2D grid of numbers.  
**Output**: The sum of the scores of all trailheads.

---

### Part 2: Trailhead Ratings

**es.usj.day10.HikingTrailsRating**

Now calculate a **rating** for each trailhead, which is the total number of **distinct hiking trails** starting at that trailhead.

**Steps**:

1. For each trailhead:
    - Identify all valid hiking trails starting from it.
    - Count all distinct hiking trails.
2. Sum the ratings of all trailheads.

**Input**: A 2D grid of numbers.  
**Output**: The sum of the ratings of all trailheads.

---

**Examples**:

For the map:
```
0123
1234
8765
9876
```

- **Part 1 Output**: 1 (only one `9` is reachable from the single trailhead).
- **Part 2 Output**: 1 (only one distinct trail exists).

For the larger map:
```
89010123
78121874
87430965
96549874
45678903
32019012
01329801
10456732
```

- **Part 1 Output**: 36 (sum of scores for all trailheads).
- **Part 2 Output**: 81 (sum of ratings for all trailheads).