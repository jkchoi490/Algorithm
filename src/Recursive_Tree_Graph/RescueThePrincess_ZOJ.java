package Recursive_Tree_Graph;

import java.io.*;
import java.util.*;

// ZOJ - Rescue the Princess
public class RescueThePrincess_ZOJ {

    static List<Integer>[] adj, graph;
    static int[] dfn, low, array;
    static int n, m, num;
    static int count, time;
    static int[][] arr;
    static int[] depth;
    static int LOG;

    // 공주님을 구하고 돕기 위한 메서드 작성
    static void SaveAndHelpPrincess(int node, int number, Stack<Integer> stack) {
        dfn[node] = low[node] = ++time;
        stack.push(node);
        boolean check = false;
        for (int value : adj[node]) {
            if (value == number && !check) { check = true; continue; }
            if (dfn[value] == 0) {
                // 공주님을 구하고 돕기 위한 메서드 실행
                SaveAndHelpPrincess(value, node, stack);
                low[node] = Math.min(low[node], low[value]);
            } else {
                low[node] = Math.min(low[node], dfn[value]);
            }
        }
        if (low[node] == dfn[node]) {
            count++;
            while (true) {
                int num = stack.pop();
                array[node] = count;
                if (num == node) break;
            }
        }
    }

    static void DFS(int node, int num, int d, int r, int[] nums) {
        depth[node] = d;
        arr[node][0] = num;
        nums[node] = r;
        for (int value : graph[node]) {
            if (value != num) DFS(value, node, d + 1, r, nums);
        }
    }

    static int getLCA(int node, int value) {
        if (depth[node] < depth[value]) { int tmp = node; node = value; value = tmp; }
        for (int i = LOG - 1; i >= 0; i--) {
            if (depth[node] - (1 << i) >= depth[value]) node = arr[node][i];
        }
        if (node == value) return node;
        for (int i = LOG - 1; i >= 0; i--) {
            if (arr[node][i] != arr[value][i]) {
                node = arr[node][i]; value = arr[value][i];
            }
        }
        return arr[node][0];
    }

    static boolean isCheck(int node, int value) {
        int lca = getLCA(node, value);
        return lca == node;
    }

    static class FastReader {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        String next() {
            while (st == null || !st.hasMoreElements()) {
                try { st = new StringTokenizer(br.readLine()); }
                catch (IOException e) { return null; }
            }
            return st.nextToken();
        }
        int nextInt() { return Integer.parseInt(next()); }
    }


    public static void main(String[] args) {
        FastReader sc = new FastReader();
        PrintWriter out = new PrintWriter(System.out);
        String line = sc.next();
        if (line == null) return;
        int T = Integer.parseInt(line);

        while (T-- > 0) {
            n = sc.nextInt(); m = sc.nextInt(); num = sc.nextInt();
            adj = new ArrayList[n + 1];
            for (int i = 1; i <= n; i++) adj[i] = new ArrayList<>();
            for (int i = 0; i < m; i++) {
                int node = sc.nextInt(), Node = sc.nextInt();
                if (node == Node) continue;
                adj[node].add(Node); adj[Node].add(node);
            }

            dfn = new int[n + 1]; low = new int[n + 1];
            array = new int[n + 1];
            time = 0; count = 0;
            Stack<Integer> stack = new Stack<>();
            for (int i = 1; i <= n; i++) {
                if (dfn[i] == 0) SaveAndHelpPrincess(i, -1, stack);
            }

            graph = new ArrayList[count + 1];
            for (int i = 1; i <= count; i++) graph[i] = new ArrayList<>();
            for (int node = 1; node <= n; node++) {
                for (int Node : adj[node]) {
                    if (array[node] != array[Node]) {
                        graph[array[node]].add(array[Node]);
                    }
                }
            }

            arr = new int[count + 1][LOG];
            depth = new int[count + 1];
            int[] numbers = new int[count + 1];
            for (int i = 1; i <= count; i++) {
                if (depth[i] == 0) {
                    DFS(i, i, 1, i, numbers);
                }
            }

            for (int j = 1; j < LOG; j++) {
                for (int i = 1; i <= count; i++) {
                    arr[i][j] = arr[arr[i][j - 1]][j - 1];
                }
            }

            for (int i = 0; i < num; i++) {
                int node = array[sc.nextInt()];
                int Node = array[sc.nextInt()];
                int adjNode = array[sc.nextInt()];

                if (numbers[node] != numbers[Node] || numbers[node] != numbers[adjNode]) {
                    out.println("No");
                    continue;
                }

                int lca = getLCA(Node, adjNode);

                if (isCheck(lca, node) && (isCheck(node, Node) || isCheck(node, adjNode))) {
                    out.println("Yes");
                } else {
                    out.println("No");
                }
            }
        }
        out.flush();
    }

}