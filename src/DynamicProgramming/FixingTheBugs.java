package DynamicProgramming;

import java.io.*;
import java.util.*;

// Kattis - Fixing the Bugs
public class FixingTheBugs {

    static class Bug {
        double p0;
        int s;
        Bug(double p0, int s) { this.p0 = p0; this.s = s; }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int B = Integer.parseInt(st.nextToken());
        int T = Integer.parseInt(st.nextToken());
        double f = Double.parseDouble(st.nextToken());

        Bug[] bugs = new Bug[B];
        for (int i = 0; i < B; i++) {
            st = new StringTokenizer(br.readLine());
            double p0 = Double.parseDouble(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            bugs[i] = new Bug(p0, s);
        }

        double[][] succ = new double[B][T + 1];
        double[][] fail = new double[B][T + 1];

        for (int i = 0; i < B; i++) {
            double p = bugs[i].p0;
            double probFailSoFar = 1.0;
            fail[i][0] = 1.0;
            for (int k = 1; k <= T; k++) {
                double sk = probFailSoFar * p;
                succ[i][k] = sk;

                probFailSoFar *= (1.0 - p);
                fail[i][k] = probFailSoFar;

                p *= f;
            }
        }

        int maxMask = 1 << B;
        double[][] dp = new double[maxMask][T + 1];

        int[] order = new int[maxMask];
        for (int m = 0; m < maxMask; m++) order[m] = m;
        Arrays.sort(order);

        for (int idx = 1; idx < maxMask; idx++) {
            int mask = order[idx];
            if (mask == 0) continue;

            for (int t = 0; t <= T; t++) {
                double best = 0.0;


                for (int i = 0; i < B; i++) {
                    if ((mask & (1 << i)) == 0) continue;

                    int nextMask = mask ^ (1 << i);
                    double[] dpNext = dp[nextMask];

                    double bestForI = dpNext[t];

                    double prefix = 0.0;
                    int si = bugs[i].s;

                    int maxX = Math.min(t, T);
                    for (int x = 1; x <= maxX; x++) {
                        prefix += succ[i][x] * (si + dpNext[t - x]);
                        double cand = prefix + fail[i][x] * dpNext[t - x];
                        if (cand > bestForI) bestForI = cand;
                    }

                    if (bestForI > best) best = bestForI;
                }

                dp[mask][t] = best;
            }
        }

        double ans = dp[maxMask - 1][T];

        System.out.printf(Locale.US, "%.15f%n", ans);
    }
}
