package DynamicProgramming;

import java.io.*;
import java.util.*;

//CodeForces - Hard Problem
public class HardProblem {

    static final long INF = Long.MAX_VALUE / 4;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        long[] cost = new long[n];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            cost[i] = Long.parseLong(st.nextToken());
        }

        String[] s = new String[n];
        String[] rs = new String[n];

        for (int i = 0; i < n; i++) {
            s[i] = br.readLine();
            rs[i] = new StringBuilder(s[i]).reverse().toString();
        }

        long[][] dp = new long[n][2];
        dp[0][0] = 0;
        dp[0][1] = cost[0];

        for (int i = 1; i < n; i++) {
            dp[i][0] = dp[i][1] = INF;

            if (s[i - 1].compareTo(s[i]) <= 0) {
                dp[i][0] = Math.min(dp[i][0], dp[i - 1][0]);
            }

            if (rs[i - 1].compareTo(s[i]) <= 0) {
                dp[i][0] = Math.min(dp[i][0], dp[i - 1][1]);
            }

            if (s[i - 1].compareTo(rs[i]) <= 0) {
                dp[i][1] = Math.min(dp[i][1], dp[i - 1][0] + cost[i]);
            }

            if (rs[i - 1].compareTo(rs[i]) <= 0) {
                dp[i][1] = Math.min(dp[i][1], dp[i - 1][1] + cost[i]);
            }
        }

        long ans = Math.min(dp[n - 1][0], dp[n - 1][1]);
        if (ans >= INF) ans = -1;

        System.out.println(ans);
    }
}
