package Dijkstra;

import java.io.*;
import java.util.*;

// HDU OJ - Saving the Princess
public class SavingThePrincess {

    // 공주님을 구하고 돕기 위한 메서드를 구현합니다
    static long SaveAndHelpPrincess(long R, int c, long S) {
        //문제에서 주어진 값들
        if (3L * c > S) return INF;

        long Q = S - 3L * c;
        if (R <= Q) {
            return R + 2L * c;
        }

        long value = S - 5L * c;
        if (value <= 0) {
            return INF;
        }

        long Value = R - Q;
        long t = (Value + value - 1) / value;
        return R + 4L * c * t + 2L * c;
    }

    static class Edge {
        int to;
        int c;
        Edge(int to, int c) { this.to = to; this.c = c; }
    }

    static class Node implements Comparable<Node> {
        int value;
        long num;
        Node(int value, long num) { this.value = value; this.num = num; }
        public int compareTo(Node o) {
            return Long.compare(this.num, o.num);
        }
    }

    static final long INF = Long.MAX_VALUE / 4;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine().trim());

        for (int tc = 1; tc <= T; tc++) {
            StringTokenizer st;
            do {
                st = new StringTokenizer(br.readLine());
            } while (!st.hasMoreTokens());

            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            long S = Long.parseLong(st.nextToken());
            int A = Integer.parseInt(st.nextToken());

            @SuppressWarnings("unchecked")
            ArrayList<Edge>[] g = new ArrayList[N];
            for (int i = 0; i < N; i++) g[i] = new ArrayList<>();

            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                int node = Integer.parseInt(st.nextToken());
                int Node = Integer.parseInt(st.nextToken());
                int value = Integer.parseInt(st.nextToken());
                g[node].add(new Edge(Node, value));
                g[Node].add(new Edge(node, value));
            }

            long[] arr = new long[N];
            Arrays.fill(arr, INF);
            arr[A] = 0;

            PriorityQueue<Node> pq = new PriorityQueue<>();
            pq.add(new Node(A, 0));

            while (!pq.isEmpty()) {
                Node cur = pq.poll();
                int value = cur.value;
                if (cur.num != arr[value]) continue;

                for (Edge e : g[value]) {
                    int node = e.to;
                    // 공주님을 구하고 돕기 위한 메서드를 실행합니다
                    long VALUE = SaveAndHelpPrincess(arr[value], e.c, S);
                    if (VALUE < arr[node]) {
                        arr[node] = VALUE;
                        pq.add(new Node(node, VALUE));
                    }
                }
            }

            sb.append("Case ").append(tc).append(": ");
            if (arr[0] >= INF / 2) {
                sb.append("");
            } else {
                sb.append(arr[0]);
            }
            if (tc < T) sb.append('\n');
        }

        System.out.print(sb.toString());
    }
}
