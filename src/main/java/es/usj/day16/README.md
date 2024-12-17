**Day 16: Reindeer Maze Simplified**

### Part One: The Reindeer Maze

**es.usj.day16.ReindeerMazeSolver**

The Reindeer Olympics features a maze challenge where Reindeer aim to reach the **End Tile** (E) from the **Start Tile** (S) with the **lowest score** possible.

The scoring rules are:
- Moving forward one tile increases the score by **1 point**.
- Rotating 90 degrees clockwise or counterclockwise increases the score by **1000 points**.
- Reindeer cannot move into walls (`#`).

For example:
```plaintext
###############
#.......#....E#
#.#.###.#.###.#
#.....#.#...#.#
#.###.#####.#.#
#.#.#.......#.#
#.#.#####.###.#
#...........#.#
###.#.#####.#.#
#...#.....#.#.#
#.#.#.###.#.#.#
#.....#...#.#.#
#.###.#.#.#.#.#
#S..#.....#...#
###############
```

The best path in this maze has a score of **7036**, achieved with 36 steps forward and 7 rotations.

---

### Part Two: Best Seats in the Maze

**es.usj.day16.ReindeerMazePathTracker**

Once the lowest score path(s) is determined, you calculate the best spots to watch the maze. A tile is considered a "best spot" if it is part of **any best path**, including the **Start (S)** and **End (E)** tiles. These tiles are marked as `O` on the map.

For example, in the first maze:
```plaintext
###############
#.......#....O#
#.#.###.#.###O#
#.....#.#...#O#
#.###.#####.#O#
#.#.#.......#O#
#.#.#####.###O#
#..OOOOOOOOO#O#
###O#O#####O#O#
#OOO#O....#O#O#
#O#O#O###.#O#O#
#OOOOO#...#O#O#
#O###.#.#.#O#O#
#O..#.....#OOO#
###############
```
Here, 45 tiles are part of at least one best path.

---

### Second Maze Example
Another maze with a different layout:
```plaintext
#################
#...#...#...#..E#
#.#.#.#.#.#.#.#.#
#.#.#.#...#...#.#
#.#.#.#.###.#.#.#
#...#.#.#.....#.#
#.#.#.#.#.#####.#
#.#...#.#.#.....#
#.#.#####.#.###.#
#.#.#.......#...#
#.#.###.#####.###
#.#.#...#.....#.#
#.#.#.#####.###.#
#.#.#.........#.#
#.#.#.#########.#
#S#.............#
#################
```
The best path here has a score of **11048**, and 64 tiles are part of at least one best path.

---

### Summary of Tasks
1. **Part One**: Find the **lowest score** a Reindeer can achieve while completing the maze.
    - Example: In the first maze, the lowest score is **7036**. In the second maze, it's **11048**.

2. **Part Two**: Determine how many tiles are part of at least one of the best paths.
    - Example: In the first maze, there are **45 tiles**; in the second maze, there are **64 tiles**.