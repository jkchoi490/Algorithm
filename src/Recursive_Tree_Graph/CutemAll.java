package Recursive_Tree_Graph;

import java.io.*;
import java.util.*;

//CodeForces - Cut'em all!
public class CutemAll {

    static List<Integer>[] adj;
    static int n;
    static int count = 0;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(br.readLine());

        if (n % 2 == 1) {
            System.out.println(-1);
            return;
        }

        adj = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) adj[i] = new ArrayList<>();

        for (int i = 0; i < n - 1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            adj[u].add(v);
            adj[v].add(u);
        }

        dfs(1, -1);

        System.out.println(count);
    }

    static int dfs(int node, int parent) {
        int size = 1; // 자기 자신 포함

        for (int next : adj[node]) {
            if (next == parent) continue;

            int sub = dfs(next, node);

            if (sub % 2 == 0) {
                count++;
            } else {
                size += sub;
            }
        }
        return size;
    }
}