package BFS;

import java.io.*;
import java.util.*;

public class Solution_DMOJ_Escape {
    static class Edge {
        int to, rev;
        int cap;
        Edge(int t, int r, int c) {
            to = t; rev = r; cap = c;
        }
    }

    static class Dinic {
        int N;
        ArrayList<Edge>[] g;
        int[] level, prog;

        Dinic(int n) {
            N = n;
            g = new ArrayList[n];
            for (int i = 0; i < n; i++) g[i] = new ArrayList<>();
            level = new int[n];
            prog = new int[n];
        }

        void addEdge(int s, int t, int cap) {
            g[s].add(new Edge(t, g[t].size(), cap));
            g[t].add(new Edge(s, g[s].size() - 1, 0));
        }

        boolean bfs(int s, int t) {
            Arrays.fill(level, -1);
            Queue<Integer> q = new ArrayDeque<>();
            level[s] = 0;
            q.add(s);
            while (!q.isEmpty()) {
                int v = q.poll();
                for (Edge e : g[v]) {
                    if (e.cap > 0 && level[e.to] < 0) {
                        level[e.to] = level[v] + 1;
                        q.add(e.to);
                    }
                }
            }
            return level[t] >= 0;
        }

        int dfs(int v, int t, int f) {
            if (v == t) return f;
            for (; prog[v] < g[v].size(); prog[v]++) {
                Edge e = g[v].get(prog[v]);
                if (e.cap > 0 && level[e.to] == level[v] + 1) {
                    int ret = dfs(e.to, t, Math.min(f, e.cap));
                    if (ret > 0) {
                        e.cap -= ret;
                        g[e.to].get(e.rev).cap += ret;
                        return ret;
                    }
                }
            }
            return 0;
        }

        int maxFlow(int s, int t) {
            int flow = 0, inf = 1_000_000_000;
            while (bfs(s, t)) {
                Arrays.fill(prog, 0);
                int f;
                while ((f = dfs(s, t, inf)) > 0) {
                    flow += f;
                }
            }
            return flow;
        }
    }

    static double dist2(int x1, int y1, int x2, int y2) {
        long dx = x1 - x2, dy = y1 - y2;
        return dx * dx + dy * dy;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int L = Integer.parseInt(st.nextToken());
        int W = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken());

        int[] x = new int[N];
        int[] y = new int[N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            x[i] = Integer.parseInt(st.nextToken());
            y[i] = Integer.parseInt(st.nextToken());
        }

        int R = 50;
        int R2 = R * R;

        int SRC = 2 * N;
        int SNK = 2 * N + 1;
        int V = 2 * N + 2;
        Dinic dinic = new Dinic(V);

        for (int i = 0; i < N; i++) {
            int in = 2 * i;
            int out = in + 1;
            dinic.addEdge(in, out, 1);
        }

        int INF = 1_000_000_000;
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (dist2(x[i], y[i], x[j], y[j]) <= 4.0 * R2) {
                    int outI = 2 * i + 1;
                    int inJ = 2 * j;
                    int outJ = 2 * j + 1;
                    int inI = 2 * i;

                    dinic.addEdge(outI, inJ, INF);
                    dinic.addEdge(outJ, inI, INF);
                }
            }
        }

        for (int i = 0; i < N; i++) {
            if (y[i] <= R) {
                dinic.addEdge(SRC, 2 * i, INF);
            }
        }

        for (int i = 0; i < N; i++) {
            if (W - y[i] <= R) {
                dinic.addEdge(2 * i + 1, SNK, INF);
            }
        }

        int ans = dinic.maxFlow(SRC, SNK);
        System.out.println(ans);
    }
}
