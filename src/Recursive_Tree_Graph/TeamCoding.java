package Recursive_Tree_Graph;

import java.util.*;
import java.io.*;

//Kattis - Team Coding
public class TeamCoding {
    static int n, k;
    static int[] language;
    static int[] root;
    static List<Integer>[] nodes;
    static int[] depth;
    static boolean[] inSubT;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        language = new int[n];
        root = new int[n];
        nodes = new ArrayList[n];
        depth = new int[n];

        for (int i = 0; i < n; i++) {
            nodes[i] = new ArrayList<>();
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            language[i] = Integer.parseInt(st.nextToken());
        }

        root[0] = -1; // Anneke has no boss
        for (int i = 1; i < n; i++) {
            root[i] = Integer.parseInt(br.readLine());
            nodes[root[i]].add(i);
        }

        // Calculate depths
        calculateDepth(0, 0);

        int maxEmployees = 0;
        int minSwaps = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            int targetLang = language[i];

            inSubT = new boolean[n];
            markSubT(i);

            // Count by depth
            Map<Integer, Integer> sameInSubT = new HashMap<>();
            Map<Integer, Integer> diffInSubT = new HashMap<>();
            Map<Integer, Integer> sameOutside = new HashMap<>();

            for (int k = 0; k < n; k++) {
                int d = depth[k];
                if (inSubT[k]) {
                    if (language[k] == targetLang) {
                        sameInSubT.put(d, sameInSubT.getOrDefault(d, 0) + 1);
                    } else {
                        diffInSubT.put(d, diffInSubT.getOrDefault(d, 0) + 1);
                    }
                } else {
                    if (language[k] == targetLang) {
                        sameOutside.put(d, sameOutside.getOrDefault(d, 0) + 1);
                    }
                }
            }

            // Calculate result
            int totalEmployees = 0;
            int swaps = 0;

            for (int d = 0; d < n; d++) {
                int same = sameInSubT.getOrDefault(d, 0);
                int diff = diffInSubT.getOrDefault(d, 0);
                int outside = sameOutside.getOrDefault(d, 0);

                totalEmployees += same;

                // Swap diff from inside with same from outside
                int canSwap = Math.min(diff, outside);
                totalEmployees += canSwap;
                swaps += canSwap;
            }

            if (totalEmployees > maxEmployees ||
                    (totalEmployees == maxEmployees && swaps < minSwaps)) {
                maxEmployees = totalEmployees;
                minSwaps = swaps;
            }
        }

        System.out.println(maxEmployees + " " + minSwaps);
    }

    static void calculateDepth(int node, int d) {
        depth[node] = d;
        for (int child : nodes[node]) {
            calculateDepth(child, d + 1);
        }
    }

    static void markSubT(int node) {
        inSubT[node] = true;
        for (int n : nodes[node]) {
            markSubT(n);
        }
    }
}

