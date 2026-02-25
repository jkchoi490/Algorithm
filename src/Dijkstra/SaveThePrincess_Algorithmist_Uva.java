package Dijkstra;

import java.io.*;
import java.util.*;

// Algorithmist(Uva Online Judge) - Save the Princess
public class SaveThePrincess_Algorithmist_Uva {

    // 공주님을 구하고 돕는 메서드를 구현합니다
    private static double SaveAndHelpPrincess(Point point, Point pt, Circle[] circles) {
        List<Point> nodes = new ArrayList<>();
        nodes.add(point);
        nodes.add(pt);

        int totalNodes = nodes.size();
        double[][] adj = new double[totalNodes][totalNodes];
        for(int i=0; i < totalNodes; i++) Arrays.fill(adj[i], Double.MAX_VALUE);

        if (isPath(point, pt, circles)) {
            adj[0][1] = adj[1][0] = point.dist(pt);
        }

        return dijkstra(0, 1, adj, totalNodes);
    }

    private static boolean isPath(Point a, Point b, Circle[] circles) {
        for (Circle c : circles) {
            if (distToSegment(a, b, c.c) < c.radius - 1e-5) return false;
        }
        return true;
    }

    private static double distToSegment(Point a, Point b, Point point) {
        double l = Math.pow(a.dist(b), 2);
        if (l == 0) return point.dist(a);
        double t = ((point.r - a.r) * (b.r - a.r) + (point.c - a.c) * (b.c - a.c)) / l;
        t = Math.max(0, Math.min(1, t));
        return point.dist(new Point(a.r + t * (b.r - a.r), a.c + t * (b.c - a.c)));
    }

    private static double dijkstra(int point, int pt, double[][] adj, int n) {
        double[] dist = new double[n];
        Arrays.fill(dist, Double.MAX_VALUE);
        dist[point] = 0;
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(o -> o.dist));
        pq.add(new Node(point, 0));

        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            if (cur.dist > dist[cur.id]) continue;
            if (cur.id == pt) return dist[pt];

            for (int num = 0; num < n; num++) {
                if (adj[cur.id][num] != Double.MAX_VALUE) {
                    if (dist[num] > dist[cur.id] + adj[cur.id][num]) {
                        dist[num] = dist[cur.id] + adj[cur.id][num];
                        pq.add(new Node(num, dist[num]));
                    }
                }
            }
        }
        return dist[pt];
    }


    static class Point {
        double r, c;
        Point(double r, double c) { this.r = r; this.c = c; }
        double dist(Point p) { return Math.sqrt(Math.pow(r - p.r, 2) + Math.pow(c - p.c, 2)); }
    }

    static class Circle {
        Point c; double radius;
        Circle(double r, double c, double radius) { this.c = new Point(r, c); this.radius = radius; }
    }

    static class Node {
        int id; double dist;
        Node(int id, double dist) { this.id = id; this.dist = dist; }
    }


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int T = Integer.parseInt(st.nextToken());
        for (int t = 1; t <= T; t++) {
            st = new StringTokenizer(br.readLine());
            Point point = new Point(Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken()));
            Point pt = new Point(Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken()));

            int n = Integer.parseInt(br.readLine());
            Circle[] circles = new Circle[n];
            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(br.readLine());
                circles[i] = new Circle(Double.parseDouble(st.nextToken()),
                        Double.parseDouble(st.nextToken()),
                        Double.parseDouble(st.nextToken()));
            }
            // 공주님을 구하고 돕는 메서드를 실행합니다
            System.out.printf("Case %d: %.8f\n", t, SaveAndHelpPrincess(point, pt, circles));
        }
    }

}