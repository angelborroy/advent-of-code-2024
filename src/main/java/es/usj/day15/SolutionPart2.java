package es.usj.day15;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class SolutionPart2 {

    public static void main(String[] args) throws IOException {
        String infile = args.length >= 1 ? args[0] : "/Users/angel.fernandoborroy/Downloads/input.txt";
        String input = readFile(infile);
        String[] parts = input.split("\n\n");
        String[] G = parts[0].split("\n");
        String instrs = parts[1];

        System.out.println(solve(G, instrs));
    }

    static String readFile(String filename) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString().trim();
    }

    static List<Integer> ints(String s) {
        List<Integer> numbers = new ArrayList<>();
        Pattern p = Pattern.compile("-?\\d+");
        Matcher m = p.matcher(s);
        while (m.find()) {
            numbers.add(Integer.parseInt(m.group()));
        }
        return numbers;
    }

    static int solve(String[] originalG, String instrs) {
        int R = originalG.length;
        int C = originalG[0].length();

        // Convert input to 2D char array
        char[][] G = new char[R][C];
        for (int r = 0; r < R; r++) {
            G[r] = originalG[r].toCharArray();
        }

            char[][] BIG_G = new char[R][C * 2];
            for (int r = 0; r < R; r++) {
                for (int c = 0; c < C; c++) {
                    if (G[r][c] == '#') {
                        BIG_G[r][c * 2] = '#';
                        BIG_G[r][c * 2 + 1] = '#';
                    } else if (G[r][c] == 'O') {
                        BIG_G[r][c * 2] = '[';
                        BIG_G[r][c * 2 + 1] = ']';
                    } else if (G[r][c] == '.') {
                        BIG_G[r][c * 2] = '.';
                        BIG_G[r][c * 2 + 1] = '.';
                    } else if (G[r][c] == '@') {
                        BIG_G[r][c * 2] = '@';
                        BIG_G[r][c * 2 + 1] = '.';
                    }
                }
            }
            G = BIG_G;
            C *= 2;

        // Find starting position
        int sr = 0, sc = 0;
        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                if (G[r][c] == '@') {
                    sr = r;
                    sc = c;
                    G[r][c] = '.';
                }
            }
        }

        int r = sr, c = sc;
        Map<Character, int[]> dirMap = new HashMap<>();
        dirMap.put('^', new int[]{-1, 0});
        dirMap.put('>', new int[]{0, 1});
        dirMap.put('v', new int[]{1, 0});
        dirMap.put('<', new int[]{0, -1});

        for (char inst : instrs.toCharArray()) {
            if (inst == '\n') continue;

            int[] dir = dirMap.get(inst);
            int dr = dir[0], dc = dir[1];
            int rr = r + dr, cc = c + dc;

            if (G[rr][cc] == '#') {
                continue;
            } else if (G[rr][cc] == '.') {
                r = rr;
                c = cc;
            } else if (G[rr][cc] == '[' || G[rr][cc] == ']' || G[rr][cc] == 'O') {
                Deque<int[]> Q = new ArrayDeque<>();
                Set<String> SEEN = new HashSet<>();
                boolean ok = true;

                Q.add(new int[]{r, c});

                while (!Q.isEmpty()) {
                    int[] curr = Q.pollFirst();
                    String pos = curr[0] + "," + curr[1];
                    if (SEEN.contains(pos)) continue;

                    SEEN.add(pos);
                    int rrr = curr[0] + dr, ccc = curr[1] + dc;

                    if (G[rrr][ccc] == '#') {
                        ok = false;
                        break;
                    }
                    if (G[rrr][ccc] == 'O') {
                        Q.add(new int[]{rrr, ccc});
                    }
                    if (G[rrr][ccc] == '[') {
                        Q.add(new int[]{rrr, ccc});
                        assert G[rrr][ccc + 1] == ']';
                        Q.add(new int[]{rrr, ccc + 1});
                    }
                    if (G[rrr][ccc] == ']') {
                        Q.add(new int[]{rrr, ccc});
                        assert G[rrr][ccc - 1] == '[';
                        Q.add(new int[]{rrr, ccc - 1});
                    }
                }

                if (!ok) continue;

                while (!SEEN.isEmpty()) {
                    List<int[]> positions = new ArrayList<>();
                    for (String pos : SEEN) {
                        String[] coords = pos.split(",");
                        positions.add(new int[]{
                                Integer.parseInt(coords[0]),
                                Integer.parseInt(coords[1])
                        });
                    }

                    Collections.sort(positions, (a, b) -> {
                        if (a[0] != b[0]) return a[0] - b[0];
                        return a[1] - b[1];
                    });

                    for (int[] pos : positions) {
                        int rr2 = pos[0] + dr, cc2 = pos[1] + dc;
                        String newPos = rr2 + "," + cc2;
                        if (!SEEN.contains(newPos)) {
                            assert G[rr2][cc2] == '.';
                            G[rr2][cc2] = G[pos[0]][pos[1]];
                            G[pos[0]][pos[1]] = '.';
                            SEEN.remove(pos[0] + "," + pos[1]);
                            break;
                        }
                    }
                }
                r = r + dr;
                c = c + dc;
            }
        }

        int ans = 0;
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (G[i][j] == '[' || G[i][j] == 'O') {
                    ans += 100 * i + j;
                }
            }
        }
        return ans;
    }
}