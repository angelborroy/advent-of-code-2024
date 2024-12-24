package es.usj.day23;

import java.io.*;
import java.util.*;

public class LANPartyFinder {
    private final Map<String, Set<String>> network = new HashMap<>();
    private Set<String> largestGroup = new HashSet<>();

    public void loadNetwork() throws IOException {
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

    public String findPassword() {
        for (String start : network.keySet()) {
            Set<String> candidates = new HashSet<>(network.get(start));
            Set<String> group = new HashSet<>();
            group.add(start);

            while (!candidates.isEmpty()) {
                String bestNode = null;
                int maxConnections = -1;

                for (String node : candidates) {
                    Set<String> connections = new HashSet<>(network.get(node));
                    connections.retainAll(candidates);
                    if (connections.size() > maxConnections) {
                        maxConnections = connections.size();
                        bestNode = node;
                    }
                }

                if (bestNode == null) break;

                group.add(bestNode);
                Set<String> newCandidates = new HashSet<>(network.get(bestNode));
                newCandidates.retainAll(candidates);

                newCandidates.removeIf(node -> !network.get(node).containsAll(group));

                if (newCandidates.isEmpty()) break;
                candidates = newCandidates;
            }

            if (group.size() > largestGroup.size()) {
                largestGroup = group;
            }
        }

        return String.join(",", new TreeSet<>(largestGroup));
    }

    public static void main(String[] args) throws IOException {
        LANPartyFinder finder = new LANPartyFinder();
        finder.loadNetwork();
        System.out.println("Password: " + finder.findPassword());
    }
}