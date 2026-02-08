package Recursive_Tree_Graph;

import java.io.*;
import java.util.*;

// QOJ.ac - Rescue the Princess
public class RescueThePrincess_QOJac {

    static int n, m, num, t, ebccCount, LOG;
    static List<Integer>[] adj, graph;
    static int[][] arr;
    static int[] stack;
    static int cursor;
    static boolean[] vis;
    static int[] dfn, low, ebccId, depth;

    // 공주님을 돕고 구하기 위한 메서드들 구현
    static void dfsEbcc(int Node, int pivot) {
        dfn[Node] = low[Node] = ++t;
        stack[cursor++] = Node;

        for (int node : adj[Node]) {
            if (dfn[node] == 0) {
                dfsEbcc(node, Node);
                low[Node] = Math.min(low[Node], low[node]);
            } else if (node != pivot) {
                low[Node] = Math.min(low[Node], dfn[node]);
            }
        }

        if (low[Node] == dfn[Node]) {
            ebccCount++;
            while (true) {
                int node = stack[--cursor];
                ebccId[node] = ebccCount;
                if (node == Node) break;
            }
        }
    }

    static void dfsLca(int node, int point, int d) {
        vis[node] = true;
        depth[node] = d;
        arr[node][0] = point;

        for (int i = 1; i < LOG; i++) {
            arr[node][i] = arr[ arr[node][i - 1] ][i - 1];
        }

        for (int Node : graph[node]) {
            if (Node == point) continue;
            if (!vis[node]) dfsLca(Node, node, d + 1);
        }
    }

    static int getLca(int a, int b) {
        if (depth[a] < depth[b]) { int tmp = a; a = b; b = tmp; }

        int diff = depth[a] - depth[b];
        for (int i = LOG - 1; i >= 0; i--) {
            if (((diff >> i) & 1) == 1) a = arr[a][i];
        }

        if (a == b) return a;

        for (int i = LOG - 1; i >= 0; i--) {
            if (arr[a][i] != arr[b][i]) {
                a = arr[a][i];
                b = arr[b][i];
            }
        }
        return arr[a][0];
    }

    static boolean isPathClear(int pivot, int node, int Node) {
        int lca_node = getLca(node, pivot);
        int lcaNode = getLca(Node, pivot);
        return lca_node == pivot && lcaNode == pivot;
    }

    static class Reader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public Reader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 10000000);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreElements()) {
                try {
                    String line = reader.readLine();
                    if (line == null) return null;
                    tokenizer = new StringTokenizer(line);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }
    }

    public static void main(String[] args) throws IOException {
        Reader sc = new Reader(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int t = sc.nextInt();
        while (t-- > 0) {
            n = sc.nextInt();
            m = sc.nextInt();
            num = sc.nextInt();

            adj = new ArrayList[n + 1];
            for (int i = 1; i <= n; i++) adj[i] = new ArrayList<>();

            for (int i = 0; i < m; i++) {
                int pivot = sc.nextInt(), node = sc.nextInt();
                if (pivot == node) continue;
                adj[pivot].add(node);
                adj[node].add(pivot);
            }

            dfn = new int[n + 1];
            low = new int[n + 1];
            ebccId = new int[n + 1];

            t = 0;
            ebccCount = 0;

            stack = new int[n + 5];
            cursor = 0;

            for (int i = 1; i <= n; i++) {
                if (dfn[i] == 0) dfsEbcc(i, -1);
            }

            graph = new ArrayList[ebccCount + 1];
            for (int i = 1; i <= ebccCount; i++) graph[i] = new ArrayList<>();

            for (int pivot = 1; pivot <= n; pivot++) {
                for (int node : adj[pivot]) {
                    int cur_pivot = ebccId[pivot];
                    int cur_node = ebccId[node];
                    if (cur_pivot != cur_node) graph[cur_pivot].add(cur_node);
                }
            }

            LOG = 1;
            while ((1 << LOG) <= ebccCount) LOG++;

            arr = new int[ebccCount + 1][LOG];
            depth = new int[ebccCount + 1];
            vis = new boolean[ebccCount + 1];

            for (int i = 1; i <= ebccCount; i++) {
                if (!vis[i]) dfsLca(i, i, 0);
            }

            while (num-- > 0) {
                int pivot = ebccId[sc.nextInt()];
                int node = ebccId[sc.nextInt()];
                int Node = ebccId[sc.nextInt()];

                if (pivot == 0 || node == 0 || Node == 0) {
                    out.println("No");
                    continue;
                }

                if (isPathClear(pivot, node, Node)) out.println("Yes");
                else out.println("No");
            }
        }

        out.flush();
    }


}
