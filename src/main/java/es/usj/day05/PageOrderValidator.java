package es.usj.day05;

import java.io.*;
import java.util.*;

public class PageOrderValidator {

    private final Map<Integer, Set<Integer>> graph = new HashMap<>();
    private final List<List<Integer>> updates = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        PageOrderValidator validator = new PageOrderValidator();
        validator.readInput();
        System.out.println("Sum of middle pages: " + validator.solve());
    }

    public void readInput() throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream("es/usj/day05/input.txt")))) {
            String line;
            boolean parsingRules = true;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    parsingRules = false;
                    continue;
                }

                if (parsingRules) {
                    // Parse rule like "47|53"
                    String[] parts = line.split("\\|");
                    int before = Integer.parseInt(parts[0]);
                    int after = Integer.parseInt(parts[1]);
                    graph.computeIfAbsent(before, k -> new HashSet<>()).add(after);
                    graph.putIfAbsent(after, new HashSet<>());
                } else {
                    // Parse update like "75,47,61,53,29"
                    List<Integer> update = Arrays.stream(line.split(","))
                            .map(Integer::parseInt)
                            .toList();
                    updates.add(update);
                }
            }
        }
    }

    public int solve() {
        int sum = 0;
        for (List<Integer> update : updates) {
            if (isValidOrder(update)) {
                int middleIndex = update.size() / 2;
                sum += update.get(middleIndex);
            }
        }
        return sum;
    }

    private boolean isValidOrder(List<Integer> update) {
        Map<Integer, Set<Integer>> subgraph = new HashMap<>();
        Set<Integer> updatePages = new HashSet<>(update);

        for (int page : updatePages) {
            Set<Integer> edges = graph.get(page);
            Set<Integer> relevantEdges = new HashSet<>();

            if (edges != null) {
                for (int nextPage : edges) {
                    if (updatePages.contains(nextPage)) {
                        relevantEdges.add(nextPage);
                    }
                }
            }
            subgraph.put(page, relevantEdges);
        }

        for (int i = 0; i < update.size(); i++) {
            int currentPage = update.get(i);

            Set<Integer> laterPages = new HashSet<>();
            for (int j = i + 1; j < update.size(); j++) {
                laterPages.add(update.get(j));
            }

            Set<Integer> edges = subgraph.get(currentPage);
            if (edges != null) {
                for (int nextPage : edges) {
                    if (!laterPages.contains(nextPage)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}