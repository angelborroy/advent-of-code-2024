package es.usj.day05;

import java.io.*;
import java.util.*;

public class PageOrderFixer {

    private Map<Integer, Set<Integer>> graph = new HashMap<>();
    private List<List<Integer>> updates = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        PageOrderFixer validator = new PageOrderFixer();
        validator.readInput();
        System.out.println("Sum of middle pages of reordered invalid updates: " + validator.solvePartTwo());
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

    public int solvePartTwo() {
        int sum = 0;
        for (List<Integer> update : updates) {
            if (!isValidOrder(update)) {
                List<Integer> correctOrder = findCorrectOrder(update);
                int middleIndex = correctOrder.size() / 2;
                sum += correctOrder.get(middleIndex);
            }
        }
        return sum;
    }

    private List<Integer> findCorrectOrder(List<Integer> update) {
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

        Map<Integer, Integer> inDegree = new HashMap<>();
        for (int page : updatePages) {
            inDegree.put(page, 0);
        }

        for (Map.Entry<Integer, Set<Integer>> entry : subgraph.entrySet()) {
            for (int nextPage : entry.getValue()) {
                inDegree.merge(nextPage, 1, Integer::sum);
            }
        }

        PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> b - a);
        for (Map.Entry<Integer, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.offer(entry.getKey());
            }
        }

        List<Integer> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            int current = queue.poll();
            result.add(current);

            Set<Integer> neighbors = subgraph.get(current);
            if (neighbors != null) {
                for (int neighbor : neighbors) {
                    inDegree.merge(neighbor, -1, Integer::sum);
                    if (inDegree.get(neighbor) == 0) {
                        queue.offer(neighbor);
                    }
                }
            }
        }

        return result;
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