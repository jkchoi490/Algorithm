package Mathmatics;

import java.io.*;
import java.util.*;

//BAEKJOON5049
public class BAEKJOON5049 {
    static class Point {
        double x, y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());

        Point[] pts = new Point[n];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            double x = Double.parseDouble(st.nextToken());
            double y = Double.parseDouble(st.nextToken());
            pts[i] = new Point(x, y);
        }


        double[] ang = new double[n];
        for (int i = 0; i < n; i++) {
            ang[i] = Math.atan2(pts[i].y, pts[i].x);
        }

        Arrays.sort(ang);

        double maxGap = 0;
        for (int i = 0; i < n - 1; i++) {
            maxGap = Math.max(maxGap, ang[i + 1] - ang[i]);
        }

        maxGap = Math.max(maxGap, 2 * Math.PI - (ang[n - 1] - ang[0]));

        double answer = 2 * Math.PI - maxGap;

        System.out.printf("%.10f\n", answer);
    }
}
