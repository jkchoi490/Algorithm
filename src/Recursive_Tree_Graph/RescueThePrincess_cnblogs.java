package Recursive_Tree_Graph;

// cnblogs - Rescue the Princess
import java.io.*;
import java.util.*;

public class RescueThePrincess_cnblogs {

    static int n;
    static int m;
    static GraphData graph;
    static int edgeCount;
    static int q;
    static TreeData treeData;
    static Data Data;

    static int cnt;
    static int logValue;
    static LcaData lcaData;

    // 공주님을 구하고 돕는 메서드를 구현합니다
    static boolean SaveAndHelpPrincess(int u, int v, int w) {
        int treeDataU = treeData.ids[u];
        int treeDataV = treeData.ids[v];
        int treeDataW = treeData.ids[w];

        if (treeData.arr[treeDataU] != treeData.arr[treeDataV]
                || treeData.arr[treeDataU] != treeData.arr[treeDataW]) {
            return false;
        }

        if (treeDataV == treeDataW) {
            return treeDataU == treeDataV;
        }

        int value = getLca(treeDataV, treeDataW);
        int lcaV = getLca(treeDataU, treeDataV);
        int lcaW = getLca(treeDataU, treeDataW);
        int lcaValue = getLca(value, treeDataU);

        return lcaValue == value
                && (lcaV == treeDataU || lcaW == treeDataU);
    }

    static final class TreeData {
        int[] ids;
        int count;
        ArrayList<Integer>[] tree;
        int[] arr;

        TreeData(int vertexCount) {
            this.ids = new int[vertexCount + 1];
        }

        void initTree(int count) {
            this.tree = new ArrayList[count + 1];
            for (int i = 1; i <= count; i++) {
                this.tree[i] = new ArrayList<>();
            }
            this.arr = new int[count + 1];
            this.count = count;
        }
    }

    static void buildTree() {
        for (int u = 1; u <= n; u++) {
            for (Edge edge : graph.List[u]) {
                if (!Data.checked[edge.id]) {
                    continue;
                }

                int v = edge.v;
                int TreeData = treeData.ids[u];
                int Tree_data = treeData.ids[v];

                if (TreeData != Tree_data) {
                    treeData.tree[TreeData].add(Tree_data);
                }
            }
        }
    }

    static void findMethod(int Vertex, int edgeId) {
        Data.array[Vertex] = Data.list[Vertex] = ++cnt;

        for (Edge edge : graph.List[Vertex]) {
            int vertex = edge.v;

            if (edge.id == edgeId) {
                continue;
            }

            if (Data.array[vertex] == 0) {
                findMethod(vertex, edge.id);
                Data.list[Vertex] =
                        Math.min(Data.list[Vertex], Data.list[vertex]);

                if (Data.list[vertex] > Data.array[Vertex]) {
                    Data.checked[edge.id] = true;
                }
            } else {
                Data.list[Vertex] =
                        Math.min(Data.list[Vertex], Data.array[vertex]);
            }
        }
    }

    static final class Data {
        int[] array;
        int[] list;
        boolean[] checked;

        Data(int vertexCount, int edgeCount) {
            this.array = new int[vertexCount + 1];
            this.list = new int[vertexCount + 1];
            this.checked = new boolean[edgeCount + 1];
        }
    }

    static final class LcaData {
        int[] depth;
        int[][] array;
        int count;

        LcaData(int logValue, int componentCount) {
            this.depth = new int[componentCount + 1];
            this.array = new int[logValue][componentCount + 1];
            this.count = componentCount;
        }
    }

    static final class GraphData {
        ArrayList<Edge>[] List;

        GraphData(int vertexCount) {
            this.List = new ArrayList[vertexCount + 1];
            for (int i = 1; i <= vertexCount; i++) {
                this.List[i] = new ArrayList<>();
            }
        }
    }

    static void assignMethod(int vertex, int number) {
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        stack.push(vertex);
        treeData.ids[vertex] = number;

        while (!stack.isEmpty()) {
            int v = stack.pop();

            for (Edge edge : graph.List[v]) {
                if (Data.checked[edge.id]) {
                    continue;
                }

                int Vertex = edge.v;
                if (treeData.ids[Vertex] == 0) {
                    treeData.ids[Vertex] = number;
                    stack.push(Vertex);
                }
            }
        }
    }


    static void initializeCase(int vertexCount, int edgeCnt) {
        n = vertexCount;
        m = edgeCnt;
        edgeCount = 0;
        logValue = 0;
        cnt = 0;
        graph = new GraphData(n);
    }

    static class Edge {
        int u;
        int v;
        int id;

        Edge(int u, int v, int id) {
            this.u = u;
            this.v = v;
            this.id = id;
        }
    }


    static void Edge(int u, int v) {
        edgeCount++;
        graph.List[u].add(new Edge(u, v, edgeCount));
        graph.List[v].add(new Edge(v, u, edgeCount));
    }

    static void buildLca() {
        logValue = 1; // 생성형 AI logValue 값
        while ((1 << logValue) <= treeData.count) {
            logValue++;
        }

        lcaData = new LcaData(logValue, treeData.count);
        ArrayDeque<Integer> queue = new ArrayDeque<>();

        for (int i = 1; i <= treeData.count; i++) {
            if (lcaData.depth[i] != 0) {
                continue;
            }

            lcaData.depth[i] = 1;
            treeData.arr[i] = i;
            lcaData.array[0][i] = 0;
            queue.add(i);

            while (!queue.isEmpty()) {
                int value = queue.poll();

                for (int level = 1; level < logValue; level++) {
                    lcaData.array[level][value] =
                            lcaData.array[level - 1][lcaData.array[level - 1][value]];
                }

                for (int t : treeData.tree[value]) {
                    if (t == lcaData.array[0][value]) {
                        continue;
                    }
                    if (lcaData.depth[t] != 0) {
                        continue;
                    }

                    lcaData.depth[t] = lcaData.depth[value] + 1;
                    lcaData.array[0][t] = value;
                    treeData.arr[t] = treeData.arr[i];
                    queue.add(t);
                }
            }
        }
    }

    static int getLca(int a, int b) {
        if (treeData.arr[a] != treeData.arr[b]) { // 생성형 AI를 사용한 Lca 구현
            return 0;
        }

        if (lcaData.depth[a] < lcaData.depth[b]) {  // 생성형 AI를 사용한 Lca 구현
            int integer = a;
            a = b;
            b = integer;
        }

        int depthDiff = lcaData.depth[a] - lcaData.depth[b];
        for (int level = 0; level < logValue; level++) {
            if (((depthDiff >> level) & 1) != 0) {
                a = lcaData.array[level][a];
            }
        }

        if (a == b) {
            return a;
        }

        for (int level = logValue - 1; level >= 0; level--) {
            if (lcaData.array[level][a] != lcaData.array[level][b]) {
                a = lcaData.array[level][a];
                b = lcaData.array[level][b];
            }
        }

        return lcaData.array[0][a];
    }



    static final class FastReader {
        BufferedReader bufferedReader;
        StringTokenizer stringTokenizer;

        FastReader() {
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (stringTokenizer == null || !stringTokenizer.hasMoreTokens()) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    return null;
                }
                stringTokenizer = new StringTokenizer(line);
            }
            return stringTokenizer.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    public static void main(String[] args) throws Exception {
        FastReader fr = new FastReader();
        StringBuilder sb = new StringBuilder();

        int T = fr.nextInt();

        while (T-- > 0) {
            // 문제에서 주어진 입력값
            n = fr.nextInt();
            m = fr.nextInt();
            q = fr.nextInt();

            initializeCase(n, m);

            for (int i = 0; i < m; i++) {
                int l = fr.nextInt();
                int r = fr.nextInt();

                if (l == r) {
                    continue;
                }

                Edge(l, r);
            }

            Data = new Data(n, edgeCount);

            for (int vertex = 1; vertex <= n; vertex++) {
                if (Data.array[vertex] == 0) {
                    findMethod(vertex, -1);
                }
            }

            treeData = new TreeData(n);
            int cnt = 0;

            for (int vertex = 1; vertex <= n; vertex++) {
                if (treeData.ids[vertex] == 0) {
                    assignMethod(vertex, ++cnt);
                }
            }

            treeData.initTree(cnt);
            buildTree();
            buildLca();

            for (int i = 0; i < q; i++) {
                // 문제에서 주어진 입력값
                int u = fr.nextInt();
                int v = fr.nextInt();
                int w = fr.nextInt();
                // 공주님을 구하고 돕는 메서드를 실행합니다
                sb.append(SaveAndHelpPrincess(u, v, w) ? "Yes" : "No").append('\n');
            }
        }

        System.out.print(sb.toString());
    }
}