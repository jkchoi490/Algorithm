package Mathmatics;

import java.io.*;
import java.util.*;

//Uva Online Judge - Solve It
public class SolveIt {

    static double p, q, r, s, t, u;

    static double f(double x) {
        return p * Math.exp(-x)
                + q * Math.sin(x)
                + r * Math.cos(x)
                + s * Math.tan(x)
                + t * x * x
                + u;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;

        while ((line = br.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(line);
            p = Double.parseDouble(st.nextToken());
            q = Double.parseDouble(st.nextToken());
            r = Double.parseDouble(st.nextToken());
            s = Double.parseDouble(st.nextToken());
            t = Double.parseDouble(st.nextToken());
            u = Double.parseDouble(st.nextToken());

            double f0 = f(0.0);
            double f1 = f(1.0);

            if (f0 * f1 > 0) {
                continue;
            }

            double lo = 0.0, hi = 1.0, mid = 0.0;

            for (int i = 0; i < 60; i++) {
                mid = (lo + hi) / 2.0;

                if (f(mid) > 0)
                    lo = mid;
                else
                    hi = mid;
            }

            System.out.printf("%.4f%n", (lo + hi) / 2.0);
        }
    }
}
