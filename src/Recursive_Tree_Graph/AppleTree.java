package Recursive_Tree_Graph;

//CodeForces - Apple Tree
import java.io.*;
import java.util.*;

public class AppleTree {

    static ArrayList<Integer>[] tree;
    static int[] leafCnt;
    static boolean[] visited;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {

            int n = Integer.parseInt(br.readLine());
            tree = new ArrayList[n + 1];
            leafCnt = new int[n + 1];
            visited = new boolean[n + 1];

            for (int i = 1; i <= n; i++) {
                tree[i] = new ArrayList<>();
            }

            // read edges
            for (int i = 0; i < n - 1; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int u = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());
                tree[u].add(v);
                tree[v].add(u);
            }

            // DFS from root
            dfs(1);

            // answer queries
            int q = Integer.parseInt(br.readLine());
            while (q-- > 0) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                sb.append(1L * leafCnt[x] * leafCnt[y]).append('\n');
            }
        }

        System.out.print(sb);
    }

    // 재귀 DFS (post-order)
    static void dfs(int u) {
        visited[u] = true;

        int sum = 0;
        for (int nxt : tree[u]) {
            if (!visited[nxt]) {
                dfs(nxt);
                sum += leafCnt[nxt];
            }
        }
        if (sum == 0) sum = 1; // leaf node

        leafCnt[u] = sum;
    }
}

