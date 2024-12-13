# Day 13: Claw Contraption

### Part One

**es.usj.day13.ClawMachineSolver**

You're at a resort's arcade, and the claw machines are unusual. Instead of a joystick, there are two buttons:

- **Button A**: Costs 3 tokens and moves the claw by a fixed amount along X and Y axes.
- **Button B**: Costs 1 token and also moves the claw by specific amounts along X and Y axes.

Each machine has one prize. To win, you must align the claw's position exactly with the prize's coordinates. For example:

- **Button A**: X+94, Y+34
- **Button B**: X+22, Y+67
- **Prize**: X=8400, Y=5400

The goal is to find the cheapest way to align the claw with the prize using the fewest tokens. For the first machine:

- Press **Button A** 80 times and **Button B** 40 times.
- Cost: \( 80 \times 3 + 40 \times 1 = 280 \) tokens.

For the second and fourth machines, no combination of A and B presses can win.  
For the third machine, the cheapest way costs 200 tokens.

### Summary for Part One:
- Maximum prizes: 2.
- Minimum tokens: 480.

### Part Two

**es.usj.day13.ClawMachineSolverUnit**

There's a mistake: the prize coordinates are actually **10 trillion units higher** on both axes. After updating the prize positions, the example becomes:

- **Prize**: X=10,000,000,008,400; Y=10,000,000,005,400

Now, only the second and fourth machines are winnable, but it requires more than 100 presses per button.

### Final Results:
- Maximum prizes: 2.
- Minimum tokens: 72,587,986,598,368.
