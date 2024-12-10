**Simplified Statement for Day 5: Print Queue**

---

### Part 1: Validate Update Order

**es.usj.day05.PageOrderValidator**

You are given:

1. **Page ordering rules**: Each rule is `X|Y`, meaning page `X` must appear before page `Y` if both pages are present.
2. **Updates**: Each update is a list of page numbers.

Determine which updates follow the ordering rules and calculate the sum of the middle page numbers for the valid updates.

**Steps**:

1. For each update:
    - Check if the pages are in the correct order based on the given rules. Ignore rules for pages not in the update.
2. For valid updates:
    - Find the middle page number of the update (the median when sorted).
3. Sum the middle page numbers of the valid updates.

**Input**: A list of page ordering rules and a list of updates.  
**Output**: The sum of middle page numbers from valid updates.

---

### Part 2: Fix Invalid Updates

**es.usj.day05.PageOrderFixer**

Now fix the invalid updates:

1. For each invalid update:
    - Reorder its pages to satisfy the ordering rules.
    - Find the middle page number of the reordered update.
2. Sum the middle page numbers of the fixed updates.

**Input**: A list of page ordering rules and a list of updates.  
**Output**: The sum of middle page numbers from fixed updates.

---

**Examples**:

For the rules:
```
47|53, 97|13, 97|61, 97|47, 75|29, ...
```
And the updates:
```
75,47,61,53,29
97,61,53,29,13
75,29,13
75,97,47,61,53
61,13,29
97,13,75,29,47
```

- **Part 1 Output**: 143 (sum of middle page numbers from valid updates: 61, 53, and 29).
- **Part 2 Output**: 123 (sum of middle page numbers from fixed invalid updates: 47, 29, and 47).