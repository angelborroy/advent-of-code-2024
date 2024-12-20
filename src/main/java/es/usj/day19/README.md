### Simplified Statement

---

**Day 19: Linen Layout**

The Historians take you to the hot springs on Gear Island. Nothing goes wrong as they search the field of helixes, but could this be your chance to visit the nearby onsen?

After speaking with the onsen reception, you find you don't have the right money for admission. However, the staff offers you a deal: help arrange their towels, and they'll let you in for free!

Each towel has a pattern of colored stripes: white (w), blue (u), black (b), red (r), or green (g). For example, a towel with the pattern `ggr` has two green stripes followed by a red stripe. Towels cannot be flipped.

The onsen provides a list of available towel patterns and the designs they want displayed. Your task is to figure out if a design can be created using the towels, and if so, in how many ways. For example:

#### Towel Patterns:
`r, wr, b, g, bwu, rb, gb, br`

#### Desired Designs:
```
brwrr
bggr
gbbr
rrbgbr
ubwu
bwurrg
brgr
bbrgwb
```

#### Example Breakdown:
- `brwrr` can be made in 2 ways: e.g., `br, wr, r` or `b, r, wr, r`.
- `bggr` can be made in 1 way: `b, g, g, r`.
- `ubwu` is **impossible**.
- Adding all possible arrangements for each design gives the total.

#### Tasks:
1. Determine how many designs can be created.
   **es.usj.day19.LinenLayout**
2. Calculate the total number of ways all possible designs can be arranged.
   **es.usj.day19.LinenLayoutPart2**
