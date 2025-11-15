package Implementation;

import java.io.*;
import java.util.*;

//Kattis - Cut it Out!
// 직선 컷(cut)을 수행하며 잘라낸 후 최소 비용을 계산
public class CutItOut {

    static class Point {
        double x, y;
        Point(double x, double y) { this.x = x; this.y = y; }
    }

    static double dot(Point a, Point b) {
        return a.x * b.x + a.y * b.y;
    }

    //벡터 뺄셈
    static Point sub(Point a, Point b) {
        return new Point(a.x - b.x, a.y - b.y);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int nA = Integer.parseInt(br.readLine());
        Point[] A = new Point[nA];
        for (int i = 0; i < nA; i++) {
            st = new StringTokenizer(br.readLine());
            double x = Double.parseDouble(st.nextToken());
            double y = Double.parseDouble(st.nextToken());
            A[i] = new Point(x, y);
        }

        int nB = Integer.parseInt(br.readLine());
        Point[] B = new Point[nB];
        for (int i = 0; i < nB; i++) {
            st = new StringTokenizer(br.readLine());
            double x = Double.parseDouble(st.nextToken());
            double y = Double.parseDouble(st.nextToken());
            B[i] = new Point(x, y);
        }

        double answer = Double.POSITIVE_INFINITY;

        for (int i = 0; i < nB; i++) {
            Point p1 = B[i];
            Point p2 = B[(i + 1) % nB];

            Point d = sub(p2, p1);

            // 변에 수직인 방향(잘라내는 직선의 방향 벡터 n)
            Point n = new Point(d.y, -d.x);

            // 이 방향으로 잘라낼 때 필요한 비용 계산
            answer = Math.min(answer, costInDirection(A, B, n));
        }

        System.out.printf("%.10f\n", answer);
    }

    static double costInDirection(Point[] A, Point[] B, Point n) {
        double minA = 1e30, maxA = -1e30;
        double minB = 1e30, maxB = -1e30;

        for (Point p : A) {
            double v = dot(p, n);
            minA = Math.min(minA, v);
            maxA = Math.max(maxA, v);
        }

        for (Point p : B) {
            double v = dot(p, n);
            minB = Math.min(minB, v);
            maxB = Math.max(maxB, v);
        }

        double totalA = maxA - minA;
        double totalB = maxB - minB;

        return totalA - totalB;
    }
}
