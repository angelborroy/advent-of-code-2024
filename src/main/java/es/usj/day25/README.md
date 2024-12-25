**Simplified Version:**

**Part One (Locks and Keys)**  

**es.usj.day25.LockKeyMatcher**

You find the Chief Historian’s office locked. The virtual locks on this floor simulate five-pin tumbler locks. You receive schematics for every lock and key. Each schematic can be converted into a list of five “heights.” For a lock, these heights extend downward from the top; for a key, they extend upward from the bottom.

A lock and key are compatible if, in every column, their pin/key heights do not overlap (i.e., the sum of heights in a given column does not exceed the lock’s total space).

Example:

- Two locks convert to heights:
    - Lock A: `0,5,3,4,3`
    - Lock B: `1,2,0,5,3`

- Three keys convert to heights:
    - Key 1: `5,0,2,1,3`
    - Key 2: `4,3,4,0,2`
    - Key 3: `3,0,2,0,1`

By testing each lock-key pair, you count the number that fit without overlap. In the example, that number is 3. For your actual input, the answer is 3065.

---

**Part Two (The Final Chronicle)**  
Inside the office, the Chief Historian—who’s been busy on a high-priority request—realizes he won’t finish his last 50 stops before Santa’s sleigh launch. Fortunately, you and the other Historians have been keeping detailed notes on every place you visited. Using these notes, you have just enough information to compile and finalize Santa’s chronicle. You agree to deliver it to Santa, while the Chief stays to watch the sleigh launch.