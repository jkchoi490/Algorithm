package BFS;

import java.io.*;
import java.util.*;

// F - Save The Princess
public class Solution_SaveThePrincess {

    static int n;
    static int[] h;
    static List<Integer>[] graph;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        h = new int[n + 1];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            h[i] = Integer.parseInt(st.nextToken());
        }

        int q = Integer.parseInt(br.readLine());
        int[] queries = new int[q];
        for (int i = 0; i < q; i++) {
            queries[i] = Integer.parseInt(br.readLine());
        }

        // 그래프 초기화
        graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) graph[i] = new ArrayList<>();

        // 간선 구성
        for (int i = 1; i <= n; i++) {
            for (int j = i + 1; j <= n; j++) {
                if (isJumpable(i, j)) {
                    graph[i].add(j);
                }
            }
        }

        // 각 쿼리에 대해 최소 점프 계산
        for (int start : queries) {
            int ans = bfs(start);
            System.out.println(ans);
        }
    }

    // 점프 가능 여부 판단
    static boolean isJumpable(int i, int j) {
        if (j == i + 1) return true; // 인접
        int minHeight = Math.min(h[i], h[j]);
        for (int k = i + 1; k < j; k++) {
            if (h[k] >= minHeight) return false;
        }
        return true;
    }

    // BFS로 최소 점프 횟수 계산
    static int bfs(int start) {
        boolean[] visited = new boolean[n + 1];
        int[] dist = new int[n + 1];
        Arrays.fill(dist, -1);

        Queue<Integer> q = new LinkedList<>();
        q.add(start);
        dist[start] = 0;
        visited[start] = true;

        while (!q.isEmpty()) {
            int cur = q.poll();

            // 도착 조건: 1 또는 n
            if (cur == 1 || cur == n) return dist[cur];

            for (int next : graph[cur]) {
                if (!visited[next]) {
                    visited[next] = true;
                    dist[next] = dist[cur] + 1;
                    q.add(next);
                }
            }
        }

        return -1; // 도달 불가
    }
}
