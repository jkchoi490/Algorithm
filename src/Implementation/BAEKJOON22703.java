package Implementation;

import java.io.*;
import java.util.*;

//Private Teacher
public class BAEKJOON22703 {

    static class Edge {
        int to, cap, rev;
        Edge(int to, int cap, int rev) {
            this.to = to;
            this.cap = cap;
            this.rev = rev;
        }
    }

    static class Dinic {
        int N;
        List<Edge>[] G;
        int[] level;
        int[] prog;

        Dinic(int N) {
            this.N = N;
            G = new ArrayList[N];
            for (int i = 0; i < N; i++) G[i] = new ArrayList<>();
            level = new int[N];
            prog = new int[N];
        }

        void addEdge(int fr, int to, int cap) {
            G[fr].add(new Edge(to, cap, G[to].size()));
            G[to].add(new Edge(fr, 0, G[fr].size() - 1));
        }

        boolean bfs(int s, int t) {
            Arrays.fill(level, -1);
            level[s] = 0;
            Queue<Integer> q = new ArrayDeque<>();
            q.add(s);

            while (!q.isEmpty()) {
                int v = q.poll();
                for (Edge e : G[v]) {
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

            for (; prog[v] < G[v].size(); prog[v]++) {
                Edge e = G[v].get(prog[v]);
                if (e.cap > 0 && level[v] < level[e.to]) {
                    int d = dfs(e.to, t, Math.min(f, e.cap));
                    if (d > 0) {
                        e.cap -= d;
                        G[e.to].get(e.rev).cap += d;
                        return d;
                    }
                }
            }
            return 0;
        }

        long maxFlow(int s, int t) {
            long flow = 0;
            while (bfs(s, t)) {
                Arrays.fill(prog, 0);
                int f;
                while ((f = dfs(s, t, Integer.MAX_VALUE)) > 0) {
                    flow += f;
                }
            }
            return flow;
        }
    }

    static Map<String, Integer> dayIndex = new HashMap<>();

    static {
        dayIndex.put("Sunday", 0);
        dayIndex.put("Monday", 1);
        dayIndex.put("Tuesday", 2);
        dayIndex.put("Wednesday", 3);
        dayIndex.put("Thursday", 4);
        dayIndex.put("Friday", 5);
        dayIndex.put("Saturday", 6);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            long W = Long.parseLong(st.nextToken());

            if (N == 0 && W == 0) break;

            long[] t = new long[N];
            List<Integer>[] ok = new List[N];

            long totalLessons = 0;

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                t[i] = Long.parseLong(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                totalLessons += t[i];

                ok[i] = new ArrayList<>();

                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < c; j++) {
                    String day = st.nextToken();
                    ok[i].add(dayIndex.get(day));
                }
            }

            if (totalLessons > W * 7) {
                System.out.println("No");
                continue;
            }

            int S = 0;
            int T = N + 7 + 1;
            int nodeCount = T + 1;

            Dinic dinic = new Dinic(nodeCount);

            for (int i = 0; i < N; i++) {
                if (t[i] > Integer.MAX_VALUE) t[i] = Integer.MAX_VALUE; // W, t ≤ 1e10 대비
                dinic.addEdge(S, 1 + i, (int)t[i]);
            }

            for (int i = 0; i < N; i++) {
                for (int d : ok[i]) {
                    dinic.addEdge(1 + i, 1 + N + d, (int)W);
                }
            }

            for (int d = 0; d < 7; d++) {
                dinic.addEdge(1 + N + d, T, (int)W);
            }

            long flow = dinic.maxFlow(S, T);

            if (flow == totalLessons) System.out.println("Yes");
            else System.out.println("No");
        }
    }
}