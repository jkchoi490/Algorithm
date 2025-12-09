package Recursive_Tree_Graph;

import java.io.*;
import java.util.*;

//HackerRank - The Story of a Tree
public class TheStoryofaTree {

    static class Result {
        static List<Integer>[] tree;
        static Set<String> guessSet;

        public static String storyOfATree(int n, List<List<Integer>> edges,
                                          int k, List<List<Integer>> guesses) {

            tree = new ArrayList[n + 1];
            for (int i = 0; i <= n; i++) {
                tree[i] = new ArrayList<>();
            }

            for (List<Integer> edge : edges) {
                int u = edge.get(0);
                int v = edge.get(1);
                tree[u].add(v);
                tree[v].add(u);
            }

            guessSet = new HashSet<>();
            for (List<Integer> guess : guesses) {
                int u = guess.get(0);
                int v = guess.get(1);
                guessSet.add(u + "-" + v);
            }

            boolean[] visited = new boolean[n + 1];
            int initialCorrect = dfs(1, -1, visited);

            visited = new boolean[n + 1];
            int winningRoots = solve(1, -1, initialCorrect, k, visited);

            int gcd = gcd(winningRoots, n);
            return (winningRoots / gcd) + "/" + (n / gcd);
        }

        private static int dfs(int node, int node2, boolean[] visited) {
            visited[node] = true;
            int count = 0;

            for (int tree_node : tree[node]) {
                if (!visited[tree_node]) {
                    if (guessSet.contains(node + "-" + tree_node)) {
                        count++;
                    }
                    count += dfs(tree_node, node, visited);
                }
            }

            return count;
        }

        private static int solve(int node, int node2, int correctCount,
                                int k, boolean[] visited) {
            visited[node] = true;
            int winCount = 0;

            if (correctCount >= k) {
                winCount = 1;
            }

            for (int tree_node : tree[node]) {
                if (!visited[tree_node]) {
                    int newCorrect = correctCount;

                    if (guessSet.contains(node + "-" + tree_node)) {
                        newCorrect--;
                    }
                    if (guessSet.contains(tree_node + "-" + node)) {
                        newCorrect++;
                    }

                    winCount += solve(tree_node, node, newCorrect, k, visited);
                }
            }

            return winCount;
        }

        private static int gcd(int a, int b) {
            while (b != 0) {
                int temp = b;
                b = a % b;
                a = temp;
            }
            return a;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int q = Integer.parseInt(br.readLine().trim());

        for (int game = 0; game < q; game++) {
            int n = Integer.parseInt(br.readLine().trim());

            List<List<Integer>> edges = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) {
                String[] edgeInput = br.readLine().trim().split(" ");
                List<Integer> edge = new ArrayList<>();
                edge.add(Integer.parseInt(edgeInput[0]));
                edge.add(Integer.parseInt(edgeInput[1]));
                edges.add(edge);
            }

            String[] gk = br.readLine().trim().split(" ");
            int g = Integer.parseInt(gk[0]);
            int k = Integer.parseInt(gk[1]);

            List<List<Integer>> guesses = new ArrayList<>();
            for (int i = 0; i < g; i++) {
                String[] guessInput = br.readLine().trim().split(" ");
                List<Integer> guess = new ArrayList<>();
                guess.add(Integer.parseInt(guessInput[0]));
                guess.add(Integer.parseInt(guessInput[1]));
                guesses.add(guess);
            }

            System.out.println(Result.storyOfATree(n, edges, k, guesses));
        }
    }
}