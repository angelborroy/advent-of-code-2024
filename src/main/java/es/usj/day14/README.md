### Simplified Problem Statement

You're tasked with predicting the movement of robots within a rectangular area that wraps around its edges (like a torus). Each robot has an initial position and velocity, moving predictably over time. Here's what to solve:

#### Part 1: Safety Factor Calculation

**es.usj.day14.RestroomRedoubt**

- **Input**: A list of robots, each with a position `p=x,y` and velocity `v=x,y`.
- **Movement Rules**: Robots move each second according to their velocity. If they go beyond the area's edges, they wrap around to the other side.
- **Area Dimensions**: The area is 101 tiles wide and 103 tiles tall.
- **Task**: After exactly 100 seconds:
  1. Count the number of robots in each quadrant of the area.
  2. Ignore robots exactly on the vertical or horizontal middle lines.
  3. Multiply the counts of robots in the four quadrants to get the **safety factor**.

#### Part 2: Finding the Easter Egg

**es.usj.day14.RobotSimulation**

- **Easter Egg Condition**: The robots will occasionally form a recognizable Christmas tree shape in the area.
- **Task**: Determine the **fewest number of seconds** needed for this pattern to appear.

The solution involves simulating the robots' movement over time and analyzing their positions.