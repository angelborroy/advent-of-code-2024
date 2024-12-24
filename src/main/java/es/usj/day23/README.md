### Summary of the Statement

**Day 23: LAN Party**

You discover a network map while exploring Easter Bunny HQ. The map lists all direct connections between computers, represented as pairs of names (e.g., `kh-tc`). Connections are bidirectional.

#### Part One: Identifying Triangles

**es.usj.day23.NetworkAnalysis**

Your task is to find **sets of three computers** (triangles) where each computer is directly connected to the other two. From the example provided, there are 12 such triangles. However, you're only interested in triangles that include at least one computer with a name starting with the letter "t." After filtering, 7 triangles remain.

---

#### Part Two: Identifying the LAN Party

**es.usj.day23.LANPartyFinder**

The LAN party involves the **largest clique** in the network, where every computer is directly connected to every other computer in the group. In the example, the largest clique includes four computers: `co`, `de`, `ka`, and `ta`.

The **password to the LAN party** is the names of the computers in the largest clique, sorted alphabetically and joined by commas. For the example, the password is:

**Password:** `co,de,ka,ta`

The actual password for your puzzle would depend on the network map provided in your specific input.