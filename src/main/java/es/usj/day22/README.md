### Simplified Version

---

**Day 22: Monkey Market**

In the jungle, a monkey steals The Historians' device! To get it back, you’ll need to trade bananas with the monkey. To gather enough bananas, you must sell information about hiding spots on the Monkey Exchange Market.

### **How the Market Works:**
- Buyers use "secret numbers" to determine their prices for hiding spot info.
- Secret numbers evolve in a predictable way:
    1. Multiply the number by 64, then XOR it with itself, and take the result modulo \(16,777,216\).
    2. Divide by 32, round down, XOR, and apply the same modulo.
    3. Multiply by 2048, XOR, and apply the same modulo.

Each buyer’s secret numbers form a sequence. The starting number is given, and you must simulate the sequence to predict prices.

### **Your Goal (Part 1):**

**es.usj.day22.MonkeyMarket**

Generate 2000 secret numbers for each buyer. The price for a hiding spot is the last digit of each secret number. Sum the 2000th secret number for all buyers.

---

### **Part 2: Maximizing Bananas**

**es.usj.day22.MonkeyMarketPart2**

Instead of directly using secret numbers as prices, buyers' prices are based on the **last digit** of their secret numbers. You can’t directly choose when to sell but must rely on a monkey who watches for specific patterns of **four price changes**.

- For example, given an initial number, the price might evolve as:
  3 → 0 (-3) → 6 (+6) → 5 (-1) → 4 (-1)

- If you instruct the monkey to look for a specific pattern (e.g., `-2, 1, -1, 3`), it will sell at the first price that matches.

You need to figure out which pattern will generate the most bananas when applied to all buyers’ price sequences.

---

**Example:**
- Buyers start with secret numbers: `1, 2, 3, 2024`.
- Using the pattern `-2, 1, -1, 3`:
    - Buyer with `1` sells at 7 bananas.
    - Buyer with `2` sells at 7 bananas.
    - Buyer with `3` doesn’t match the pattern.
    - Buyer with `2024` sells at 9 bananas.

**Total Bananas:** \( 7 + 7 + 9 = 23 \).

### **Your Task:**
Find the best sequence of 4 price changes to maximize the total bananas sold across all buyers.