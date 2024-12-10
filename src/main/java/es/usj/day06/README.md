**Simplified Statement for Day 6: Guard Gallivant**

---

### Part 1: Predict the Guard's Path

**es.usj.day06.GuardPathSimulator**

You are given a 2D map showing:

- The guard's starting position and direction (`^`, `v`, `<`, or `>`).
- Obstacles (`#`) and open spaces (`.`).

The guard moves according to these rules:

1. If there is an obstacle directly in front, turn 90° to the right.
2. Otherwise, take one step forward.

Predict the guard's path until they leave the map boundaries. Mark all visited positions on the map, including the starting position.

**Goal**: Count the number of distinct positions the guard visits.

**Input**: A 2D map with the guard's position, obstacles, and open spaces.  
**Output**: The count of distinct positions visited by the guard.

---

### Part 2: Get the Guard Stuck in a Loop

**es.usj.day06.GuardSimulation**

Add a single new obstacle (`O`) to the map to force the guard into a looping patrol route. The new obstacle:

1. Cannot be placed at the guard's starting position.
2. Must create a situation where the guard endlessly repeats the same path.

**Goal**: Determine how many different positions can be chosen for the new obstacle to create a loop.

**Input**: A 2D map with the guard's position, obstacles, and open spaces.  
**Output**: The number of valid positions for the new obstacle to trap the guard in a loop.

---

**Examples**:

For the map:
```
....#.....
.........#
..........
..#.......
.......#..
..........
.#..^.....
........#.
#.........
......#...
```

- **Part 1 Output**: 41 (distinct positions visited by the guard).
- **Part 2 Output**: 6 (positions where a new obstacle creates a loop).