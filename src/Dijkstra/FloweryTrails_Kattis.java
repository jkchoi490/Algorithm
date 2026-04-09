package Dijkstra;

import java.io.*;
import java.util.*;

// Kattis - Flowery Trails
public class FloweryTrails_Kattis {

    static class Edge {
        int to;
        int value;

        Edge(int to, int value) {
            this.to = to;
            this.value = value;
        }
    }

    static class Trail {
        int u, v, w;

        Trail(int u, int v, int w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }
    }

    static class Node implements Comparable<Node> {
        int vertex;
        long dist;

        Node(int vertex, long dist) {
            this.vertex = vertex;
            this.dist = dist;
        }

        @Override
        public int compareTo(Node other) {
            return Long.compare(this.dist, other.dist);
        }
    }

    static final long INF = Long.MAX_VALUE / 4;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        List<Edge>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        Trail[] trails = new Trail[m];

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());

            trails[i] = new Trail(u, v, w);

            graph[u].add(new Edge(v, w));
            graph[v].add(new Edge(u, w));
        }

        long[] dist = dijkstra(0, graph);
        long[] Dist = dijkstra(n - 1, graph);

        long value = dist[n - 1];
        long sum = 0L;

        for (Trail t : trails) {
            int u = t.u;
            int v = t.v;
            int w = t.w;

            boolean check = false;

            if (dist[u] != INF && Dist[v] != INF &&
                    dist[u] + w + Dist[v] == value) {
                check = true;
            }

            if (dist[v] != INF && Dist[u] != INF &&
                    dist[v] + w + Dist[u] == value) {
                check = true;
            }

            if (check) {
                sum += w;
            }
        }

        System.out.println(sum * 2);
    }

    static long[] dijkstra(int start, List<Edge>[] graph) {
        int n = graph.length;
        long[] dist = new long[n];
        Arrays.fill(dist, INF);
        dist[start] = 0L;

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(start, 0L));

        while (!pq.isEmpty()) {
            Node node = pq.poll();

            if (node.dist > dist[node.vertex]) {
                continue;
            }

            for (Edge edge : graph[node.vertex]) {
                long Dist = node.dist + edge.value;

                if (Dist < dist[edge.to]) {
                    dist[edge.to] = Dist;
                    pq.offer(new Node(edge.to, Dist));
                }
            }
        }

        return dist;
    }
}