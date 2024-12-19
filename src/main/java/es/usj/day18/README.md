### Simplified Version

**Day 18: RAM Run**

You find yourself inside a computer at the North Pole, where memory bytes are falling into your two-dimensional memory grid. These bytes corrupt the memory at specific coordinates, and you need to navigate from the top-left corner `(0,0)` to the bottom-right corner `(70,70)` without stepping on corrupted spaces.

Each falling byte's position is given as an `X,Y` coordinate, and the corruption process is fast—bytes fall every nanosecond. Your task is to:

1. **Part 1**: Simulate the first kilobyte (1024 bytes) falling and determine the **minimum number of steps** required to safely reach the exit. Corrupted coordinates are marked with `#`, and safe coordinates are marked with `.`.

2. **Part 2**: Find the first byte that prevents any path to the exit. This occurs when adding that byte completely blocks the route from `(0,0)` to `(70,70)`.

#### Example

For a small grid `(6x6)` with a sequence of falling bytes, simulate the corruption and find:

- **Part 1**: After 12 bytes fall, determine the shortest safe path to the exit.
  - **es.usj.day18.MemoryPathfindingPart1** 
- **Part 2**: Identify the first byte that cuts off all paths to the exit.
  - **es.usj.day18.MemoryPathfindingPart2** 
