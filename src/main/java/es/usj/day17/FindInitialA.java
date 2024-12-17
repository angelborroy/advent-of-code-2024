package es.usj.day17;

import java.util.*;

public class FindInitialA {
    // Desired output sequence (the program itself)
    private static final int[] DESIRED_OUTPUT = {2,4,1,1,7,5,0,3,1,4,4,5,5,5,3,0};

    // Memoization cache: key is (iteration, A_{i+1}), value is minimal A_i or -1 if none
    private static final Map<String, Long> memo = new HashMap<>();

    public static void main(String[] args) {
        // We know after iteration 16, A_16 = 0
        // Start backwards from iteration 15
        long A_afterLast = 0;

        long result = findAForIteration(15, A_afterLast);
        if (result == -1) {
            System.out.println("No solution found.");
        } else {
            System.out.println("Found A: " + result);
        }
    }

    /**
     * Find the minimal A_i that leads to a given A_{i+1} and correct output_i.
     * iteration ranges from 0 to 15, where iteration=15 is the last iteration that produced the last output.
     * A_{i+1} is the A after this iteration has completed.
     *
     * We want to find A_i in [A_{i+1}*8 ... A_{i+1}*8+7] that:
     *   1) When simulated, produces output_i = DESIRED_OUTPUT[i]
     *   2) Results in A_{i+1} after dividing by 8 at the adv step
     *
     * If iteration=0, we return the minimal A_0 that works directly.
     * Otherwise, we find A_i and then recursively find A_{i-1}, and so forth.
     */
    private static long findAForIteration(int iteration, long A_next) {
        String key = iteration + ":" + A_next;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        // Determine the range for A_i:
        // A_i / 8 = A_{i+1} => A_i in [A_next*8 ... A_next*8+7]
        long start = A_next * 8;
        long end = A_next * 8 + 7;

        long bestA_i = -1;

        // Special case for the last iteration (iteration=15):
        // We must have had a positive A_15 and ended with A_16=0.
        // This means A_15 < 8 and A_15 > 0 if iteration=15 and A_next=0.
        if (iteration == 15 && A_next == 0) {
            start = 1;
            end = 7;
        }

        for (long A_i = start; A_i <= end; A_i++) {
            // Simulate this iteration forward
            IterationResult res = simulateIteration(A_i);

            // Check if output matches DESIRED_OUTPUT[i] and A_after = A_next
            if (res.output == DESIRED_OUTPUT[iteration] && res.A_after == A_next) {
                // This A_i is consistent for this iteration.
                if (iteration == 0) {
                    // We've reached the first iteration, A_i is A_0, return it
                    if (bestA_i == -1 || A_i < bestA_i) {
                        bestA_i = A_i;
                    }
                } else {
                    // Find A_{i-1} given A_i
                    // We know A_{i-1} must yield A_i after iteration i-1 completes
                    long candidate = findAForIteration(iteration - 1, A_i);
                    if (candidate != -1) {
                        // candidate is A_0 at the end of recursion
                        if (bestA_i == -1 || candidate < bestA_i) {
                            bestA_i = candidate;
                        }
                    }
                }
            }
        }

        memo.put(key, bestA_i);
        return bestA_i;
    }

    /**
     * Simulate one iteration of the given instructions forward, given A_i.
     * Returns an IterationResult with A_after, B, C, and output.
     *
     * The instructions each iteration:
     *   1. bst(4): B = A_i % 8
     *   2. bxl(1): B = B ^ 1
     *   3. cdv(5): C = A_i / (2^(B))  where B is now after step 2
     *   4. adv(3): A_after = A_i / 8
     *   5. bxl(4): B = B ^ 4 (Note: B from step 2)
     *   6. bxc(5): B = B ^ C
     *   7. out(5): output = (B % 8)
     *   8. jnz(0): (not relevant for a single iteration simulation; just returns A_after)
     */
    private static IterationResult simulateIteration(long A_i) {
        // Step 1: bst(4)
        long B = A_i % 8;

        // Step 2: bxl(1)
        B = B ^ 1;

        // Now B is used in cdv(5)
        long power = 1L << B; // 2^B
        long C = A_i / power;

        // Step 4: adv(3)
        long A_after = A_i / 8;

        // Step 5: bxl(4)
        B = B ^ 4;

        // Step 6: bxc(5)
        B = B ^ C;

        // Step 7: out(5)
        int output = (int)(B % 8);

        return new IterationResult(A_after, B, C, output);
    }

    private static class IterationResult {
        long A_after;
        long B;
        long C;
        int output;

        IterationResult(long A_after, long B, long C, int output) {
            this.A_after = A_after;
            this.B = B;
            this.C = C;
            this.output = output;
        }
    }
}
