### Simplified Version

Santa's starship is in trouble: one of the Historians has teleported to a locked area of the ship. To unlock the door, you need to type codes (your input) on a numeric keypad. Unfortunately, due to various hazards, only robots can operate the keypads.

#### Key Details:
- **Numeric Keypad**: Arranged as:
  ```
  7 8 9
  4 5 6
  1 2 3
    0 A
  ```
- **Directional Keypad**: Used to control the robot's arm. Buttons are:
  ```
    ^ A
  < v >
  ```
    - **^**: Move up
    - **v**: Move down
    - **<**: Move left
    - **>**: Move right
    - **A**: Press the button

#### Robot Workflow:
1. The first robot uses a directional keypad to type the door code on the numeric keypad.
2. You control the first robot remotely using your directional keypad.
3. The process repeats, forming a chain of robots controlling each other.

#### Example:
To type `029A`, the sequence might involve:
1. Your keypad: `<vA<AA>>^AvAA<^A>A<v<A>>^AvA^A...`
2. Robot keypads: Various directional moves to guide the next robot.
3. Numeric keypad: Final typing of `029A`.

Each code has a **complexity**, calculated as:
1. The number of button presses required to type the code.
2. Multiplied by the numeric part of the code (ignoring leading zeroes).

#### Problem:
- For five codes (e.g., `029A`, `980A`), find the fewest button presses needed to type each.
- Compute the total complexity for all codes.

**es.usj.day21.RobotKeypadSolver**

#### Part Two:
After unlocking the first door, you learn another Historian is trapped. This time, **25 robots** are involved in the control chain, but the process is the same. Calculate the total complexity again, factoring in the longer chain.

**es.usj.day21.RobotKeypadSolver2**

---

This streamlined explanation focuses on the main idea and avoids unnecessary repetition while retaining all key details.