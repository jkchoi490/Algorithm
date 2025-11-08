package Recursive_Tree_Graph;

import java.io.*;
import java.util.*;

// Escape
public class Solution_AtCoder_Escape {

    static int N, M;
    static int[] w;
    static List<Integer>[] graph;
    static boolean[] visited;
    static long maxScore = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        w = new int[N + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            w[i] = Integer.parseInt(st.nextToken());
        }

        graph = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) graph[i] = new ArrayList<>();

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            graph[u].add(v);
            graph[v].add(u);
        }

        visited = new boolean[N + 1];
        dfs(1, -1, 0);

        System.out.println(maxScore);
    }

    static void dfs(int now, int prev, long score) {
        boolean firstVisit = !visited[now];
        if (firstVisit) score += w[now];
        visited[now] = true;

        maxScore = Math.max(maxScore, score);

        for (int next : graph[now]) {
            if (next == prev) continue; // 직전 간선은 재사용 금지
            dfs(next, now, score);
        }

        if (firstVisit) visited[now] = false; // 백트래킹
    }
}
