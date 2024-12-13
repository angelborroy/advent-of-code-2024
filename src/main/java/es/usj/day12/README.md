### Garden Groups

The task involves helping the Elves calculate the cost of fencing garden regions based on a map of plots, where each plot contains a single type of plant denoted by a letter.

---

#### Key Concepts:

1. **Regions**: Adjacent plots with the same plant type (touching horizontally or vertically) form a region.  
   Example:
   ```
   AAAA
   BBCD
   BBCC
   EEEC
   ```
   This map has regions of types A, B, C, D, and E.

2. **Area**: Number of plots in a region.
    - Example: Region A has 4 plots → Area = 4.

3. **Perimeter (Part 1)**: Number of sides of plots in a region that are not adjacent to the same region.
    - Example: Region A has a perimeter of 10.

4. **Cost (Part 1)**: Multiply **Area** by **Perimeter** for each region, then sum them up.
    - Example: For region A → Cost = Area (4) × Perimeter (10) = 40.

5. **Sides (Part 2)**: Count the straight sections of fences for each region, including both inside and outside boundaries.
    - Example: Region A has 4 sides.

6. **Cost (Part 2)**: Multiply **Area** by **Number of Sides**, then sum up the costs.
    - Example: For region A → Cost = Area (4) × Sides (4) = 16.

---

#### Examples:

1. **Map:**
   ```
   AAAA
   BBCD
   BBCC
   EEEC
   ```
    - **Part 1 Total Price**: 140.
    - **Part 2 Total Price**: 80.

2. **Map with Complex Shapes:**
   ```
   OOOOO
   OXOXO
   OOOOO
   OXOXO
   OOOOO
   ```
    - **Part 1 Total Price**: 772.
    - **Part 2 Total Price**: 436.

3. **Larger Example:**
   ```
   RRRRIICCFF
   RRRRIICCCF
   VVRRRCCFFF
   VVRCCCJFFF
   VVVVCJJCFE
   VVIVCCJJEE
   VVIIICJJEE
   MIIIIIJJEE
   MIIISIJEEE
   MMMISSJEEE
   ```
    - **Part 1 Total Price**: 1930.
    - **Part 2 Total Price**: 1206.

---

### Implementation:

1. **Part 1**: es.usj.day12.GardenFencing
2. **Part 2**: es.usj.day12.GridRegionAnalyzer