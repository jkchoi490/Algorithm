package Recursive_Tree_Graph;

import java.io.*;
import java.util.*;

// Zhejiang University Programming Contest - Rescue the Princess
public class RescueThePrincess {

    static class Edge {
        int to, id;
        Edge(int t, int i) { to = t; id = i; }
    }

    static int n, m, q;
    static ArrayList<Edge>[] g;
    static int timer;
    static int[] tin, low;
    static boolean[] check;

    static int[] comp;
    static int compCnt;
    static ArrayList<Integer>[] graph;

    static final int LOG = 20;
    static int[][] root;
    static int[] depth;

    static void dfs(int v, int pEdge) {
        tin[v] = low[v] = ++timer;
        for (Edge e : g[v]) {
            if (e.id == pEdge) continue;
            int u = e.to;

            if (tin[u] != 0) {
                low[v] = Math.min(low[v], tin[u]);  // back edge
            } else {
                dfs(u, e.id);
                low[v] = Math.min(low[v], low[u]);

                if (low[u] > tin[v]) {
                    check[e.id] = true;
                }
            }
        }
    }

    static void dfsComp(int v, int cid) {
        comp[v] = cid;
        for (Edge e : g[v]) {
            if (comp[e.to] == -1 && !check[e.id])
                dfsComp(e.to, cid);
        }
    }

    static void dfsTree(int v, int p) {
        for (int to : graph[v]) {
            if (to == p) continue;
            depth[to] = depth[v] + 1;
            root[0][to] = v;
            dfsTree(to, v);
        }
    }

    static int lca(int a, int b) {
        if (depth[a] < depth[b]) {
            int t = a; a = b; b = t;
        }
        int diff = depth[a] - depth[b];
        for (int i = 0; i < LOG; i++) {
            if (((diff >> i) & 1) == 1) a = root[i][a];
        }
        if (a == b) return a;

        for (int i = LOG - 1; i >= 0; i--) {
            if (root[i][a] != root[i][b]) {
                a = root[i][a];
                b = root[i][b];
            }
        }
        return root[0][a];
    }

    // -------------- MAIN --------------
    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {

            StringTokenizer st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());
            q = Integer.parseInt(st.nextToken());

            g = new ArrayList[n + 1];
            for (int i = 1; i <= n; i++) g[i] = new ArrayList<>();

            for (int i = 0; i < m; i++) {
                st = new StringTokenizer(br.readLine());
                int l = Integer.parseInt(st.nextToken());
                int r = Integer.parseInt(st.nextToken());
                g[l].add(new Edge(r, i));
                g[r].add(new Edge(l, i));
            }

            tin = new int[n + 1];
            low = new int[n + 1];
            check = new boolean[m];
            timer = 0;

            for (int i = 1; i <= n; i++) {
                if (tin[i] == 0)
                    dfs(i, -1);
            }

            comp = new int[n + 1];
            Arrays.fill(comp, -1);
            compCnt = 0;

            for (int i = 1; i <= n; i++) {
                if (comp[i] == -1) {
                    dfsComp(i, compCnt++);
                }
            }

            graph = new ArrayList[compCnt];
            for (int i = 0; i < compCnt; i++) graph[i] = new ArrayList<>();

            for (int i = 1; i <= n; i++) {
                for (Edge e : g[i]) {
                    if (comp[i] != comp[e.to] && check[e.id]) {
                        graph[comp[i]].add(comp[e.to]);
                    }
                }
            }

            root = new int[LOG][compCnt];
            depth = new int[compCnt];
            for (int i = 0; i < LOG; i++)
                Arrays.fill(root[i], -1);

            for (int i = 0; i < compCnt; i++) {
                if (root[0][i] == -1) {
                    depth[i] = 0;
                    dfsTree(i, -1);
                }
            }

            for (int k = 1; k < LOG; k++) {
                for (int i = 0; i < compCnt; i++) {
                    int p = root[k - 1][i];
                    root[k][i] = (p < 0 ? -1 : root[k - 1][p]);
                }
            }

            for (int i = 0; i < q; i++) {
                st = new StringTokenizer(br.readLine());
                int u = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());
                int w = Integer.parseInt(st.nextToken());

                int cu = comp[u];
                int cv = comp[v];
                int cw = comp[w];

                if (cu == cv || cu == cw) {
                    sb.append("Yes\n");
                    continue;
                }

                int l = lca(cv, cw);
                if (l == cu) sb.append("Yes\n");
                else sb.append("No\n");
            }
        }

        System.out.print(sb.toString());
    }
}
