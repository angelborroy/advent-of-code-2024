**Day 15: Warehouse Woes Simplified**

You find yourself back in your mini submarine as other mini submarines scatter in different directions. Nearby, a group of lanternfish looks anxious. Driving closer, you discover the issue: they’ve lost control of a warehouse robot that's now chaotically pushing boxes, disrupting their logistics.

The lanternfish have provided you with:
1. A map of the warehouse showing walls (`#`), boxes (`O`), the robot's starting position (`@`), and open spaces (`.`).
2. A sequence of moves (`^`, `v`, `<`, `>`), representing the robot's attempts to move.

The robot:
- Pushes boxes when attempting to move into them, as long as the destination is clear.
- Cannot move if a wall or another box obstructs its path.
- The goal is to simulate the robot's movements and calculate the sum of GPS coordinates for the final positions of all boxes.

GPS coordinates for a box are calculated as:
\[
\text{GPS coordinate} = (100 \times \text{distance from top}) + \text{distance from left}
\]

### Part One: Single Warehouse

**es.usj.day15.WarehouseRobot**

You simulate the robot's movements and compute the final GPS sum of the boxes' positions.

---

### Part Two: Scaled-Up Warehouse

**es.usj.day15.SolutionPart2**

The lanternfish encounter a similar problem in a second warehouse, but everything (except the robot) is scaled horizontally:
- Walls (`#`) become `##`.
- Boxes (`O`) become `[]`.
- Open spaces (`.`) become `..`.
- The robot (`@`) becomes `@.` but still acts as a single unit.

This new setup introduces wider boxes that can block or push multiple boxes simultaneously. Distances for GPS calculations are now measured to the nearest edge of the scaled-up boxes.

You simulate the robot's moves in this scaled-up warehouse. For the larger example, the sum of GPS coordinates is **9021**.

---

**Your Tasks:**
1. Predict the robot's movements in the original warehouse and compute the GPS sum of the boxes' positions.
2. Scale up the warehouse, simulate the robot's movements, and compute the GPS sum for the new configuration.

