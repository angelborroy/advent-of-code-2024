package es.usj.day23;

import java.io.*;
import java.util.*;

public class NetworkAnalysis {
    private final Map<String, Set<String>> network = new HashMap<>();

    public void loadConnections() throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream("es/usj/day23/input.txt")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] nodes = line.trim().split("-");
                if (nodes.length == 2) {
                    network.computeIfAbsent(nodes[0], k -> new HashSet<>()).add(nodes[1]);
                    network.computeIfAbsent(nodes[1], k -> new HashSet<>()).add(nodes[0]);
                }
            }
        }
    }

    public long findTriples() {
        Set<Set<String>> triples = new HashSet<>();
        List<String> nodes = new ArrayList<>(network.keySet());

        for (int i = 0; i < nodes.size() - 2; i++) {
            for (int j = i + 1; j < nodes.size() - 1; j++) {
                for (int k = j + 1; k < nodes.size(); k++) {
                    String n1 = nodes.get(i), n2 = nodes.get(j), n3 = nodes.get(k);
                    if (network.get(n1).contains(n2) &&
                            network.get(n1).contains(n3) &&
                            network.get(n2).contains(n3)) {
                        triples.add(new HashSet<>(Arrays.asList(n1, n2, n3)));
                    }
                }
            }
        }

        return triples.stream()
                .filter(triple -> triple.stream().anyMatch(n -> n.startsWith("t")))
                .count();
    }

    public static void main(String[] args) {
        try {
            NetworkAnalysis network = new NetworkAnalysis();
            network.loadConnections();
            System.out.println("Triples with 't': " + network.findTriples());
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}