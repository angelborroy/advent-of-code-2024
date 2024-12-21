### Simplified Statement

#### Part One: Two-Picosecond Cheat Rule

**es.usj.day20.MazeSolver**

You're outside a massive CPU, where a nearby program invites you to a "race condition festival." The goal is to navigate a racetrack of walls (`#`) and paths (`.`) as quickly as possible, starting at `S` and ending at `E`. Each move takes 1 picosecond.

However, racers can cheat! Exactly once per race, you can ignore walls for **2 picoseconds**. The cheat must start and end on a path (`.`). This allows you to save time by cutting through walls.

For example:
- Without cheating, completing the track might take 84 picoseconds.
- By cheating, you could save up to 64 picoseconds in one race.

The task is to calculate how many cheats save at least **100 picoseconds**.

#### Part Two: Updated Rule (20-Picosecond Cheat)

**es.usj.day20.MazeSolver2**

The rules have changed! Now, cheats can last up to **20 picoseconds**, significantly increasing the number of possible shortcuts. However:
- The cheat must still start and end on a path.
- Unused cheat time is lost; you can't save it for later.

For example:
- A 6-picosecond cheat could save 76 picoseconds.
- A cheat of up to 20 picoseconds may save even more.

The new task is to calculate how many cheats under the updated rule save at least **100 picoseconds**.

