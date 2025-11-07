package Recursive_Tree_Graph;

import java.io.*;
import java.util.*;

//Escape
public class Escape {
    // 그래프의 간선(Edge)을 표현하는 클래스
    static class Edge {
        int to;    // 간선이 향하는 노드
        int rev;   // 역방향 간선이 graph[to]에서 몇 번째 인덱스에 있는지 (잔여 용량 갱신을 위해 필요)
        int cap;   // 현재 간선의 잔여 용량
        Edge(int to, int rev, int cap) {
            this.to = to; this.rev = rev; this.cap = cap;
        }
    }

    // 최대 유량 알고리즘인 Dinic 알고리즘 구현
    static class Dinic {
        int N;     // 전체 노드의 수
        ArrayList<Edge>[] graph; // 인접 리스트: 그래프 구조를 저장
        int[] level, it; // level: BFS를 통해 계산되는 레벨 그래프의 깊이 (층), it: DFS에서 사용할 현재 간선 인덱스

        Dinic(int n) {
            N = n;
            graph = new ArrayList[n];
            for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
            level = new int[n];
            it = new int[n];
        }

        // 간선 추가 (정방향 간선(용량 c)과 역방향 간선(용량 0)을 함께 추가)
        void addEdge(int u, int v, int c) {
            // 정방향 간선 (u -> v, 용량 c)
            graph[u].add(new Edge(v, graph[v].size(), c));
            // 역방향 간선 (v -> u, 용량 0). graph[v].size()는 정방향 간선이 추가된 후의 v의 간선 리스트 크기이므로, 역방향 간선이 추가될 위치의 인덱스를 가리킨다.
            graph[v].add(new Edge(u, graph[u].size()-1, 0));
        }

        // BFS: 레벨 그래프를 구성하고 s에서 t로 가는 경로가 있는지 확인
        boolean bfs(int s, int t) {
            Arrays.fill(level, -1);
            Queue<Integer> q = new ArrayDeque<>();
            level[s] = 0; q.add(s);
            while (!q.isEmpty()) {
                int u = q.poll();
                for (Edge e : graph[u]) {
                    if (level[e.to] < 0 && e.cap > 0) {
                        level[e.to] = level[u] + 1;
                        q.add(e.to);
                    }
                }
            }
            return level[t] >= 0; // 도착점(t)에 도달 가능하면 true
        }

        // DFS: 레벨 그래프를 따라 유량을 흘려보냄 (블로킹 플로우 찾기)
        int dfs(int u, int t, int f) {
            if (u == t) return f; // 도착점에 도달하면 유량 반환
            // 현재 노드 u의 it[u]번째 간선부터 탐색
            for (int i = it[u]; i < graph[u].size(); i++, it[u]++) {
                Edge e = graph[u].get(i);
                // 잔여 용량이 있고, 레벨 그래프의 다음 레벨이면 (최단 경로만 탐색)
                if (e.cap > 0 && level[e.to] == level[u] + 1) {
                    // 흘려보낼 수 있는 유량 계산
                    int ret = dfs(e.to, t, Math.min(f, e.cap));
                    if (ret > 0) {
                        // 유량 갱신: 정방향 간선 용량 감소, 역방향 간선 용량 증가
                        e.cap -= ret;
                        graph[e.to].get(e.rev).cap += ret;
                        return ret; // 흘려보낸 유량 반환
                    }
                }
            }
            return 0; // 더 이상 경로가 없으면 0 반환
        }

        // 최대 유량 계산
        int maxFlow(int s, int t) {
            int flow = 0;
            while (bfs(s, t)) { // BFS로 레벨 그래프 구성 가능한 동안 반복
                Arrays.fill(it, 0); // 각 노드의 현재 탐색 간선 인덱스 초기화
                int f;
                while ((f = dfs(s, t, Integer.MAX_VALUE)) > 0) { // DFS로 블로킹 플로우 찾기
                    flow += f;
                }
            }
            return flow; // 최종 최대 유량 반환 (이는 최소 컷의 용량과 같다)
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int L = fs.nextInt(); // 협곡 길이 (x축)
        int W = fs.nextInt(); // 협곡 너비 (y축)
        int N = fs.nextInt(); // 병사 수

        int[] x = new int[N], y = new int[N]; // 병사 좌표
        for (int i = 0; i < N; i++) {
            x[i] = fs.nextInt();
            y[i] = fs.nextInt();
        }

        /* * 그래프 구성 (노드 분할 기법, Node Splitting)
         * 각 병사 i에 대해 두 개의 노드 생성: in_i (i)와 out_i (i + N)
         * - SRC(2*N): 소스 노드 (출발 지점, x=0)
         * - SINK(2*N+1): 싱크 노드 (도착 지점, x=L)
         * - 노드 수: 2*N + 2
         */
        int SRC = 2 * N;
        int SINK = 2 * N + 1;
        Dinic dinic = new Dinic(2 * N + 2);
        final int INF = 1000000; // 충분히 큰 용량 (> N). 병사를 제거하는 비용(1)보다 커야 한다.

        /* 1. 병사 노드 간의 간선 (컷의 용량)
         * in_i -> out_i 간선 추가 (용량 1)
         * 이 간선이 컷에 포함되면 '병사 i를 제거한다'는 의미가 되며, 그 비용은 1이다.
         */
        for (int i = 0; i < N; i++) {
            dinic.addEdge(i, i + N, 1);
        }

        /* 2. 경계 연결
         * 병사의 시야(반경 100m)가 협곡의 위/아래 경계에 닿는지 확인
         * (x=0에서 출발, x=L로 도착하는 경로는 y축 전체(0~W)에서 가능하다. 즉, 위/아래 경계에 붙어 탈출 가능)
         */
        for (int i = 0; i < N; i++) {
            // 병사 i의 시야가 위쪽 경계(Y=W)에 닿는지 (y[i] + 100 >= W)
            if (y[i] + 100 >= W) {
                // SRC -> in_i (용량 INF): 소스와 위쪽 경계에 닿는 병사 연결
                dinic.addEdge(SRC, i, INF);
            }
            // 병사 i의 시야가 아래쪽 경계(Y=0)에 닿는지 (y[i] - 100 <= 0)
            if (y[i] - 100 <= 0) {
                // out_i -> SINK (용량 INF): 아래쪽 경계에 닿는 병사와 싱크 연결
                dinic.addEdge(i + N, SINK, INF);
            }
        }

        /* 3. 병사 간 연결
         * 두 병사 i와 j의 시야(반경 100m 원)가 겹치거나 닿는지 확인
         * 두 병사 사이의 거리가 200m(100m + 100m) 이하이면 시야가 닿는다.
         * -> 두 병사 in-out 노드 사이에 무한 용량 간선 연결
         */
        long R2 = 200L * 200L; // 거리 제곱 (100m + 100m = 200m)의 제곱
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                long dx = x[i] - x[j];
                long dy = y[i] - y[j];
                long dist2 = dx * dx + dy * dy; // 두 병사 간 거리의 제곱

                if (dist2 <= R2) {
                    // 시야가 닿거나 겹치면 -> 두 병사의 연결은 비용 없이 통과할 수 없다.
                    // out_i -> in_j, out_j -> in_i (양방향, 용량 INF)
                    // 이 간선들은 '컷'에 포함될 수 없으며, 병사 i와 j 중 하나를 제거해야 한다.
                    dinic.addEdge(i + N, j, INF);
                    dinic.addEdge(j + N, i, INF);
                }
            }
        }

        /*
         * 최대 유량 계산: SRC에서 SINK까지의 최대 유량
         * 이 값은 최소 컷의 용량과 같으며,
         * 최소 컷은 용량 1인 간선(in_k -> out_k)들의 집합으로 구성된다.
         * 따라서, 이는 안전한 통로를 확보하기 위해 제거해야 하는 최소 병사 수와 같다.
         */
        int answer = dinic.maxFlow(SRC, SINK);
        System.out.println(answer);
    }

    // 빠른 입력을 위한 FastScanner 클래스
    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;
        FastScanner(InputStream is) { in = is; }
        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }
        int nextInt() throws IOException {
            int c;
            while ((c = read()) <= ' ') if (c == -1) return Integer.MIN_VALUE;
            int sign = 1;
            if (c == '-') { sign = -1; c = read(); }
            int val = 0;
            while (c > ' ') {
                val = val * 10 + (c - '0');
                c = read();
            }
            return val * sign;
        }
    }
}