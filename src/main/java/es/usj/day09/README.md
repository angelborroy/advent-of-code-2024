**Simplified Statement for Day 9: Disk Fragmenter**

---

### Part 1: Block-by-Block Compaction

**es.usj.day09.DiskCompaction**

You are given a **disk map**, a string where digits represent files and their block sizes, alternating with free space. The goal is to compact the disk by moving file blocks to the leftmost available free spaces, **one block at a time**, until there are no gaps between file blocks.

**Steps**:

1. Parse the disk map into alternating file blocks and free spaces.
2. Move file blocks one by one to the leftmost available free space.
3. When the compaction is complete, calculate the **checksum**:
    - Multiply each block's position by its file ID, and sum these values.
    - Ignore free spaces when calculating the checksum.

**Input**: A string representing the disk map.  
**Output**: The resulting checksum after block-by-block compaction.

---

### Part 2: File-by-File Compaction

**es.usj.day09.DiskCompactionNew**

Now compact the disk by moving entire files to the leftmost span of free space that can fit the file. Files are moved **in order of decreasing file ID** (highest first). If no free space large enough for a file exists to its left, the file does not move.

**Steps**:

1. Parse the disk map into alternating file blocks and free spaces.
2. For each file (starting with the highest ID):
    - Move the file to the leftmost free space large enough to fit it, if possible.
    - Leave the file in place if no such free space exists.
3. Calculate the **checksum** in the same way as Part 1.

**Input**: A string representing the disk map.  
**Output**: The resulting checksum after file-by-file compaction.

---

**Examples**:

For the disk map:
```
2333133121414131402
```

- **Part 1 Output**: 1928 (checksum after block-by-block compaction).
- **Part 2 Output**: 2858 (checksum after file-by-file compaction).