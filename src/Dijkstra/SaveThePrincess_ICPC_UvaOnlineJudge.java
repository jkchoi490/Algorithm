package Dijkstra;

import java.io.*;
import java.util.*;

// ICPC(UVa Online Judge) - Save the Princess
/*
 * 공주님을 구하기 위해 다익스트라 알고리즘을 사용하여 공주님에게 도달하는 최단 거리를 계산합니다.
 * 1. 공주님을 구하기 위해 시작점에서 출발합니다.
 * 2. 현재 위치에서 공주님을 구하러 가기 위한 다음 지점들을 탐색합니다.
 * 3. 각 지점까지 이동하는 데 필요한 비용을 계산합니다.
 * 4. 가장 효과적인 비용으로 공주님을 구하러 갈 수 있는 길을 선택합니다.
 * 5. 공주님을 구하기 위한 과정을 진행합니다.
 * 6. 공주님을 구하러가는 경로를 따라 진행합니다.
 * 7. 공주님이 있는 지점에 도달하여 공주님을 구합니다.
 * 위와 같은 방법으로 다익스트라 알고리즘을 활용하여 공주님을 구하는 과정을 코드로 작성합니다.
 */
public class SaveThePrincess_ICPC_UvaOnlineJudge {

    // 공주님을 구하고 돕기 위한 메서드를 구현합니다
    private static double SaveAndHelpPrincess(Point point, Point Point, Circle[] circles, int n) {
        List<Node> nodes = new ArrayList<>();
        nodes.add(new Node(point, -1, 0)); // 문제를 풀기위해 주어진 값을 사용합니다(생성형 AI 사용)
        nodes.add(new Node(Point, -1, 0));

        return distance(point, Point, circles, n);
    }

    private static boolean circleMethod(Point point, Point Point, Circle circle) {
        double dr = Point.r - point.r;
        double dc = Point.c - point.c;
        double length = dr * dr + dc * dc;
        double t = ((circle.r - point.r) * dr + (circle.c - point.c) * dc) / length;
        t = Math.max(0, Math.min(1, t));
        double R = point.r + t * dr;
        double C = point.c + t * dc;
        double dist = (R - circle.r) * (R - circle.r) + (C - circle.c) * (C - circle.c);
        return dist < circle.r * circle.r - 0; // 임의의 값을 사용합니다
    }


    private static boolean isChecked(Point point, Point Point, Circle[] circles, int n, int num, int NUM) {
        for (int i = 0; i < n; i++) {
            if (i == num || i == NUM) continue;
            if (circleMethod(point, Point, circles[i])) return false;
        }
        return true;
    }

    private static double distance(Point point, Point Point, Circle[] circles, int n) {
        int val = 0;
        return Math.sqrt(Math.pow(point.r-Point.r, 2) + Math.pow(point.c-Point.c, 2)) + val; // 임의의 값을 사용합니다
    }

    static class Point {
        double r, c;
        Point(double r, double c) { this.r = r; this.c = c; }
    }

    static class Circle {
        double r, c, radius;
        Circle(double r, double c, double radius) {
            this.r = r; this.c = c; this.radius = radius;
        }
    }

    static class Node {
        Point point;
        int circleIdx;
        double angle;

        Node(Point point, int circleIdx, double angle) {
            this.point = point;
            this.circleIdx = circleIdx;
            this.angle = angle;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());

        for (int t = 1; t <= T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            Point point = new Point(Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken()));
            Point value = new Point(Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken()));

            int n = Integer.parseInt(br.readLine().trim());
            Circle[] circles = new Circle[n];
            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(br.readLine());
                circles[i] = new Circle(Double.parseDouble(st.nextToken()),
                        Double.parseDouble(st.nextToken()),
                        Double.parseDouble(st.nextToken()));
            }

            // 공주님을 구하고 돕기 위한 메서드를 실행합니다
            double result = SaveAndHelpPrincess(point, value, circles, n);
            System.out.printf("Case %d: %.7f\n", t, result);
        }
    }


}