package TwoPointers_and_SlidingWindow;

import java.io.*;
import java.util.*;

//Dovelet - 신호(Signal)
public class Signal {

    static class Point {
        long x, y;
        Point(long x, long y) { this.x = x; this.y = y; }
    }

    static int n;
    static Point[] p;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(br.readLine().trim());
        p = new Point[n];

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            long x = Long.parseLong(st.nextToken());
            long y = Long.parseLong(st.nextToken());
            p[i] = new Point(x, y);
        }

        double total = 0.0;
        long comb = (long) n * (n - 1) * (n - 2) / 6;

        for (int i = 0; i < n; i++) {
            total += process(i);
        }

        double ans = total / comb;
        System.out.printf("%.3f\n", ans);
    }

    static double process(int idx) {
        Point A = p[idx];

        double[] ang = new double[n - 1];
        int k = 0;
        for (int i = 0; i < n; i++) {
            if (i == idx) continue;
            ang[k++] = Math.atan2(p[i].y - A.y, p[i].x - A.x);
        }

        Arrays.sort(ang);

        double[] ext = new double[2 * (n - 1)];
        for (int i = 0; i < n - 1; i++) {
            ext[i] = ang[i];
            ext[i + n - 1] = ang[i] + 2 * Math.PI;
        }

        double count = 0.0;
        int m = n - 1;

        int r = 0;
        for (int l = 0; l < m; l++) {
            if (r < l) r = l;
            while (r + 1 < l + m &&
                    ext[r + 1] - ext[l] <= Math.PI) {
                r++;
            }

            long cnt = r - l;

            count += cnt;
        }

        return count + 1; // A 자신 포함
    }
}
