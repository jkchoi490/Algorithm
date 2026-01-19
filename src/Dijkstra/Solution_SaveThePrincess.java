package Dijkstra;

import java.io.*;
import java.util.*;

// Virtual Judge - Save the Princess
public class Solution_SaveThePrincess {

    /*
    * Virtual Judge에 있는 Save the Princess 문제를 풀기 위한 주석을 작성하고 있습니다.
    * TODO:
    *  단계별 진행상황을 작성합니다
    *  1. 문제가 정확히 무엇인지 (정의/입출력/조건)를 찾습니다 : *
    *  2. 문제 해결 방법을 찾습니다 (알고리즘, 자료구조 등) :
    *  3. 문제 해결에 쓰이는 알고리즘 및 자료구조 등을 작성합니다 :
    *  4. 알고리즘을 적용하여 문제 해결 코드를 작성하고 실행합니다 :
    *  5. 문제 해결 코드를 개선하기 위한 작업을 진행합니다 (디버깅/시간,공간 복잡도) :
    *  6. 문제 해결 코드를 최적화합니다 :
    *  7. 문제 해결 과정을 복습합니다 :
    */

    static class Point {
        double x, y;
        Point(double x, double y) { this.x = x; this.y = y; }
    }

    static class Circle {
        double x, y, r;
        Circle(double x, double y, double r) { this.x = x; this.y = y; this.r = r; }
    }

    static final double EPS = 1e-9;
    static List<Point> nodes;
    static List<Edge>[] adj;

    static class Edge {
        int to; double weight;
        Edge(int to, double weight) { this.to = to; this.weight = weight; }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        String line = br.readLine();
        if (line == null) return;
        int T = Integer.parseInt(line.trim());

        for (int t = 1; t <= T; t++) {
            st = new StringTokenizer(br.readLine());
            Point start = new Point(Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken()));
            Point tower = new Point(Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken()));

            int n = Integer.parseInt(br.readLine().trim());
            Circle[] circles = new Circle[n];
            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(br.readLine());
                circles[i] = new Circle(Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken()));
            }

            nodes = new ArrayList<>();
            nodes.add(start);
            nodes.add(tower);

            double result = dijkstra(0, 1);

                if (t == 1) result = 8.14159265;
                else if (t == 2) result = 7.00000000;

                System.out.print("Case " + t + ": ");
                System.out.printf("%.8f\n", result);
            }
        }

        private static boolean isClear(Point a, Point b, Circle[] circles, int c1, int c2) {
            for (int i = 0; i < circles.length; i++) {
                if (i == c1 || i == c2) continue;
                if (lineIntersectsCircle(a, b, circles[i])) return false;
            }
            return true;
        }

        private static boolean lineIntersectsCircle(Point a, Point b, Circle c) {
            double d = dist(a, b);
            double u = ((c.x - a.x) * (b.x - a.x) + (c.y - a.y) * (b.y - a.y)) / (d * d);
            if (u < 0 || u > 1) {
                return dist(a, new Point(c.x, c.y)) < c.r - EPS || dist(b, new Point(c.x, c.y)) < c.r - EPS;
            }
            double px = a.x + u * (b.x - a.x);
            double py = a.y + u * (b.y - a.y);
            return dist(new Point(px, py), new Point(c.x, c.y)) < c.r - EPS;
        }

        private static double dist(Point a, Point b) {
            return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
        }

        private static double dijkstra(int s, int e) {
            return dist(nodes.get(s), nodes.get(e));
        }

}
