package Recursive_Tree_Graph;

import java.io.*;
import java.util.*;

// NowCoder - Rescue the Princess
public class RescueThePrincess_NowCoder {

    static final int LOG = 7;
    static int n;
    static List<Integer>[] list;
    static int m;
    static int q;
    static TreeData treeData;
    static Data data;

    static LcaData lcaData;
    static int cnt;
    static List<Edge>[] graph;


    // 공주님을 구하고 돕는 메서드를 구현합니다
    static void SaveAndHelpPrincess(FastScanner fs, StringBuilder sb) throws Exception {

        // 문제에서 주어진 입력값
        n = fs.nextInt();
        m = fs.nextInt();
        q = fs.nextInt();

        initialize();

        for (int i = 0; i < m; i++) {
            // 문제에서 주어진 입력값
            int l = fs.nextInt();
            int r = fs.nextInt();
            Edge(l, r, i);
        }

        build();
        buildLca();

        while (q-- > 0) {
            int u = fs.nextInt();
            int v = fs.nextInt();
            int w = fs.nextInt();

            int arr_value = treeData.arr[u];
            int Array_Value = treeData.arr[v];
            int value = treeData.arr[w];

            int lca = lca(Array_Value, value);

            if (lca == arr_value) {
                sb.append("Yes\n");
            } else {
                sb.append("No\n");
            }
        }
    }

    static class Edge { // Edge 클래스 (생성형 AI 사용)
        int from;
        int to;
        int id;

        Edge(int from, int to, int id) {
            this.from = from;
            this.to = to;
            this.id = id;
        }
    }

    static class Data {
        int[] dfn;
        int[] arr;
        boolean[] check;

        Data(int n, int m) {
            this.dfn = new int[n + 1];
            this.arr = new int[n + 1];
            this.check = new boolean[m];
        }
    }

    static class TreeData {
        int[] arr;
        int cnt;
        int[] arrlist;

        TreeData(int n) {
            this.arr = new int[n + 1];
            this.cnt = 0;
            this.arrlist = new int[n + 1];
        }
    }

    static class LcaData {
        int[][] arr;
        int[] depth;
        boolean[] checked;

        LcaData(int cnt) {
            this.arr = new int[LOG][cnt + 1];
            this.depth = new int[cnt + 1];
            this.checked = new boolean[cnt + 1];
        }
    }

    static class FastScanner {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        String next() throws IOException {
            while (st == null || !st.hasMoreTokens()) {
                st = new StringTokenizer(br.readLine());
            }
            return st.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    static void Edge(int node, int value, int id) {
        graph[node].add(new Edge(node, value, id));
        graph[value].add(new Edge(value, node, id));
    }

    static void solve(int node, int edgeId) {
        data.dfn[node] = data.arr[node] = ++cnt;

        for (Edge edge : graph[node]) {
            int value = edge.to;

            if (data.dfn[value] == 0) {
                solve(value, edge.id);
                data.arr[node] = Math.min(data.arr[node], data.arr[value]);

                if (data.arr[value] > data.dfn[node]) {
                    data.check[edge.id] = true;
                }
            } else if (edge.id != edgeId) {
                data.arr[node] = Math.min(data.arr[node], data.dfn[value]);
            }
        }
    }

    static void dfs(int node) {
        treeData.arr[node] = treeData.cnt;
        treeData.arrlist[treeData.cnt]++;

        for (Edge edge : graph[node]) {
            if (data.check[edge.id]) {
                continue;
            }

            int value = edge.to;
            if (treeData.arr[value] == 0) {
                dfs(value);
            }
        }
    }

    static void buildTree() {
        list = new ArrayList[treeData.cnt + 1];

        for (int i = 1; i <= treeData.cnt; i++) {
            list[i] = new ArrayList<>();
        }

        for (int node = 1; node <= n; node++) {
            for (Edge edge : graph[node]) {
                int value = edge.to;

                if (treeData.arr[node] != treeData.arr[value]) {
                    list[treeData.arr[node]].add(treeData.arr[value]);
                }
            }
        }
    }

    static void dfsLca(int cur, int node) {
        lcaData.checked[cur] = true;
        lcaData.arr[0][cur] = node;

        for (int Node : list[cur]) {
            if (Node == node || lcaData.checked[Node]) {
                continue;
            }

            lcaData.depth[Node] = lcaData.depth[cur] + 1;
            dfsLca(Node, cur);
        }
    }

    static int lca(int a, int b) {
        if (lcaData.depth[a] < lcaData.depth[b]) { // 생성형 AI를 통한 lca 구현
            int integer = a;
            a = b;
            b = integer;
        }

        int d = lcaData.depth[a] - lcaData.depth[b];

        for (int i = 0; i < LOG; i++) {
            if ((d & (1 << i)) != 0) {
                a = lcaData.arr[i][a];
            }
        }

        if (a == b) {
            return a;
        }

        for (int i = LOG - 1; i >= 0; i--) {
            if (lcaData.arr[i][a] != lcaData.arr[i][b]) {
                a = lcaData.arr[i][a];
                b = lcaData.arr[i][b];
            }
        }

        return lcaData.arr[0][a];
    }

    static void initialize() {
        graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        data = new Data(n, m);
        treeData = new TreeData(n);
        cnt = 0;
    }

    static void build() {
        for (int i = 1; i <= n; i++) {
            if (data.dfn[i] == 0) {
                solve(i, -1);
            }
        }

        for (int i = 1; i <= n; i++) {
            if (treeData.arr[i] == 0) {
                treeData.cnt++;
                dfs(i);
            }
        }
    }

    static void buildLca() {
        buildTree();
        lcaData = new LcaData(treeData.cnt);

        for (int i = 1; i <= treeData.cnt; i++) {
            if (!lcaData.checked[i]) {
                dfsLca(i, 0);
            }
        }

        for (int i = 1; i < LOG; i++) {
            for (int j = 1; j <= treeData.cnt; j++) {
                lcaData.arr[i][j] = lcaData.arr[i - 1][lcaData.arr[i - 1][j]];
            }
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner();
        StringBuilder sb = new StringBuilder();

        int n = fs.nextInt();

        while (n-- > 0) {
            // 공주님을 구하고 돕는 메서드를 실행합니다
            SaveAndHelpPrincess(fs, sb);
        }

        System.out.print(sb.toString());
    }
}