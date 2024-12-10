**Simplified Statement for Day 2: Red-Nosed Reports**

---

### Part 1: Safe Reports

**es.usj.day02.ReactorSafety**

Given a list of reports (each report is a list of integers), determine how many reports are **safe**. A report is safe if it meets both conditions:

1. The levels are either all increasing or all decreasing.
2. The absolute difference between any two adjacent levels is at least 1 and at most 3.

**Input**: A list of integer lists (reports).  
**Output**: The count of safe reports.

---

### Part 2: Problem Dampener

**es.usj.day02.ReactorSafetyDampener**

Now consider that the reactor's Problem Dampener can ignore **one bad level** in each report. A report is safe if:

1. It satisfies the conditions from Part 1, or
2. Removing exactly one level makes it satisfy the conditions.

**Input**: A list of integer lists (reports).  
**Output**: The count of safe reports considering the Problem Dampener.

---

**Examples**:
For the reports:
```
7 6 4 2 1
1 2 7 8 9
9 7 6 2 1
1 3 2 4 5
8 6 4 4 1
1 3 6 7 9
```

- **Part 1 Output**: 2
- **Part 2 Output**: 4