package BFS;

import java.io.*;
import java.util.*;

//공주를 구해라.
public class 공주를구해라 {
    static ArrayList<Integer>[] graph;
    static int N, M;
    static int S, E;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        graph = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            graph[a].add(b);
            graph[b].add(a);
        }

        st = new StringTokenizer(br.readLine());
        S = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());

        System.out.println(bfs(S, E));
    }

    static int bfs(int start, int end) {
        boolean[] visited = new boolean[N + 1];
        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[]{start, 0});
        visited[start] = true;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int node = cur[0];
            int dist = cur[1];

            if (node == end) return dist;

            for (int next : graph[node]) {
                if (!visited[next]) {
                    visited[next] = true;
                    q.add(new int[]{next, dist + 1});
                }
            }
        }
        return 0; // 도달 불가능
    }
}
