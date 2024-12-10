**Simplified Statement for Day 1: Historian Hysteria**

### Part 1: Total Distance Between Lists

**es.usj.day01.HistorianPuzzle.java**

Given two lists of integers (left and right), calculate the **total distance** by pairing numbers as follows:

1. Sort both lists in ascending order.
2. Pair the smallest number from each list, then the second smallest, and so on.
3. Calculate the absolute difference for each pair and sum these differences.

**Input**: Two lists of integers.  
**Output**: Total distance (sum of all pairwise absolute differences).

---

### Part 2: Similarity Score

**es.usj.day01.ChiefHistorianPuzzle.java**

Given the same two lists of integers, calculate the **similarity score** as follows:

1. For each number in the left list, count how many times it appears in the right list.
2. Multiply the number by its count in the right list.
3. Sum these values for all numbers in the left list.

**Input**: Two lists of integers.  
**Output**: Similarity score (sum of all products from step 2).

---

**Examples**:
For input lists:
```
Left:  [3, 4, 2, 1, 3, 3]
Right: [4, 3, 5, 3, 9, 3]
```

- **Part 1 Output**: 11
- **Part 2 Output**: 31