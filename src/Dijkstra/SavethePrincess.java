package Dijkstra;

import java.io.*;
import java.util.*;
import static java.lang.Math.*;

//Save the Princess
public class SavethePrincess {
    static class Circle {
        double x, y, r;
        Circle(double x, double y, double r) {
            this.x = x;
            this.y = y;
            this.r = r;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        line = br.readLine();
        int T = Integer.parseInt(line.trim());
        for (int tc = 1; tc <= T; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            double xp = Double.parseDouble(st.nextToken());
            double yp = Double.parseDouble(st.nextToken());
            double xt = Double.parseDouble(st.nextToken());
            double yt = Double.parseDouble(st.nextToken());

            int n = Integer.parseInt(br.readLine().trim());
            Circle[] rocks = new Circle[n];
            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(br.readLine());
                double xi = Double.parseDouble(st.nextToken());
                double yi = Double.parseDouble(st.nextToken());
                double ri = Double.parseDouble(st.nextToken());
                rocks[i] = new Circle(xi, yi, ri);
            }

            // total nodes = prince + tower + rocks
            int total = n + 2;
            double[][] dist = new double[total][total];

            for (int i = 0; i < total; i++) {
                for (int j = 0; j < total; j++) {
                    if (i == j) dist[i][j] = 0;
                    else dist[i][j] = computeDistance(i, j, xp, yp, xt, yt, rocks);
                }
            }

            double ans = dijkstra(dist, total);

            System.out.printf("Case %d: %.8f%n", tc, ans);
        }
    }

    static double computeDistance(int i, int j, double xp, double yp, double xt, double yt, Circle[] rocks) {
        double x1, y1, r1, x2, y2, r2;
        if (i == 0) { // prince
            x1 = xp; y1 = yp; r1 = 0;
        } else if (i == 1) { // tower
            x1 = xt; y1 = yt; r1 = 0;
        } else {
            Circle c = rocks[i - 2];
            x1 = c.x; y1 = c.y; r1 = c.r;
        }

        if (j == 0) {
            x2 = xp; y2 = yp; r2 = 0;
        } else if (j == 1) {
            x2 = xt; y2 = yt; r2 = 0;
        } else {
            Circle c = rocks[j - 2];
            x2 = c.x; y2 = c.y; r2 = c.r;
        }

        double centerDist = hypot(x1 - x2, y1 - y2);
        double d = centerDist - (r1 + r2);
        if (d < 0) d = 0;
        return d;
    }

    static double dijkstra(double[][] graph, int n) {
        double[] dist = new double[n];
        boolean[] visited = new boolean[n];
        Arrays.fill(dist, Double.MAX_VALUE);
        dist[0] = 0;

        for (int i = 0; i < n; i++) {
            int u = -1;
            double best = Double.MAX_VALUE;
            for (int j = 0; j < n; j++) {
                if (!visited[j] && dist[j] < best) {
                    best = dist[j];
                    u = j;
                }
            }
            if (u == -1) break;
            visited[u] = true;

            for (int v = 0; v < n; v++) {
                if (!visited[v] && dist[u] + graph[u][v] < dist[v]) {
                    dist[v] = dist[u] + graph[u][v];
                }
            }
        }

        return dist[1];
    }
}
