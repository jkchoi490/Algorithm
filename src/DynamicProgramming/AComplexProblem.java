package DynamicProgramming;

import java.util.*;
import java.io.*;

//Kattis - A Complex Problem
public class AComplexProblem {
    static Map<String, Integer> classToId = new HashMap<>();
    static int numClasses = 0;

    static int getId(String className) {
        if (!classToId.containsKey(className)) {
            classToId.put(className, numClasses++);
        }
        return classToId.get(className);
    }

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        if (line == null) return;

        StringTokenizer st = new StringTokenizer(line);
        int s = Integer.parseInt(st.nextToken());
        int p = Integer.parseInt(st.nextToken());

        List<int[]> subsetEdges = new ArrayList<>();
        List<int[]> properSubsetEdges = new ArrayList<>();

        for (int i = 0; i < s; i++) {
            st = new StringTokenizer(br.readLine());
            int u = getId(st.nextToken());
            int v = getId(st.nextToken());
            subsetEdges.add(new int[]{u, v});
        }

        for (int i = 0; i < p; i++) {
            st = new StringTokenizer(br.readLine());
            int u = getId(st.nextToken());
            int v = getId(st.nextToken());
            properSubsetEdges.add(new int[]{u, v});
        }

        int min = findMinimum(subsetEdges, properSubsetEdges);
        int max = findMaximum(subsetEdges, properSubsetEdges);

        StringBuilder sb = new StringBuilder();
        sb.append(min).append(" ").append(max);
        System.out.println(sb.toString());
    }

    static int findMinimum(List<int[]> subsetEdges, List<int[]> properSubsetEdges) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < numClasses; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] edge : subsetEdges) graph.get(edge[0]).add(edge[1]);
        for (int[] edge : properSubsetEdges) graph.get(edge[0]).add(edge[1]);

        int[] disc = new int[numClasses];
        int[] low = new int[numClasses];
        Arrays.fill(disc, -1);
        boolean[] onStack = new boolean[numClasses];
        Stack<Integer> stack = new Stack<>();
        int[] timer = {0};
        int[] sccCount = {0};
        int[] sccId = new int[numClasses];

        for (int i = 0; i < numClasses; i++) {
            if (disc[i] == -1) {
                findSCCDFS(i, graph, sccId, low, disc, onStack, stack, timer, sccCount);
            }
        }
        return sccCount[0];
    }

    static void findSCCDFS(int u, List<List<Integer>> graph, int[] sccId, int[] low,
                       int[] disc, boolean[] onStack, Stack<Integer> stack,
                       int[] timer, int[] sccCount) {
        disc[u] = low[u] = timer[0]++;
        stack.push(u);
        onStack[u] = true;

        for (int v : graph.get(u)) {
            if (disc[v] == -1) {
                findSCCDFS(v, graph, sccId, low, disc, onStack, stack, timer, sccCount);
                low[u] = Math.min(low[u], low[v]);
            } else if (onStack[v]) {
                low[u] = Math.min(low[u], disc[v]);
            }
        }

        if (low[u] == disc[u]) {
            while (true) {
                int v = stack.pop();
                onStack[v] = false;
                sccId[v] = sccCount[0];
                if (v == u) break;
            }
            sccCount[0]++;
        }
    }

    static int findMaximum(List<int[]> subsetEdges, List<int[]> properSubsetEdges) {
        boolean[][] mustDiffer = new boolean[numClasses][numClasses];
        boolean[][] isSubsetOf = new boolean[numClasses][numClasses];

        for (int[] edge : subsetEdges) isSubsetOf[edge[0]][edge[1]] = true;
        for (int[] edge : properSubsetEdges) {
            isSubsetOf[edge[0]][edge[1]] = true;
            mustDiffer[edge[0]][edge[1]] = true;
        }

        for (int k = 0; k < numClasses; k++) {
            for (int i = 0; i < numClasses; i++) {
                if (!isSubsetOf[i][k] && !mustDiffer[i][k]) continue;
                for (int j = 0; j < numClasses; j++) {
                    if (isSubsetOf[i][k] && isSubsetOf[k][j]) {
                        isSubsetOf[i][j] = true;
                    }
                    if ((isSubsetOf[i][k] && mustDiffer[k][j]) || (mustDiffer[i][k] && isSubsetOf[k][j])) {
                        mustDiffer[i][j] = true;
                    }
                }
            }
        }

        int[] indegree = new int[numClasses];
        for (int i = 0; i < numClasses; i++) {
            for (int j = 0; j < numClasses; j++) {
                if (mustDiffer[i][j]) indegree[j]++;
            }
        }

        int[] dp = new int[numClasses];
        Arrays.fill(dp, 1);
        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < numClasses; i++) {
            if (indegree[i] == 0) q.offer(i);
        }

        int maxPath = 0;
        while (!q.isEmpty()) {
            int u = q.poll();
            maxPath = Math.max(maxPath, dp[u]);

            for (int v = 0; v < numClasses; v++) {
                if (mustDiffer[u][v]) {
                    dp[v] = Math.max(dp[v], dp[u] + 1);
                    if (--indegree[v] == 0) q.offer(v);
                }
            }
        }

        return maxPath;
    }
}