package DynamicProgramming;

import java.io.*;
import java.util.*;

// CodeForces - Debugging
public class Debugging_CodeForces {
    static long[] memo;
    static long R, P;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        R = Long.parseLong(st.nextToken());
        P = Long.parseLong(st.nextToken());

        memo = new long[n + 1];
        for (int i = 0; i <= n; i++) {
            memo[i] = -1;
        }

        System.out.println(solve(n));
    }

    static long solve(int i) {
        if (i <= 1) return 0;
        if (memo[i] != -1) return memo[i];

        long answer = Long.MAX_VALUE;

        for (int k = 0, num; k <= i; k = num) {
            int size = (i + k - 1) / k;

            if (size == 1) {
                num = i + 1;
            } else {
                num = (i / (size - 1)) + 1;
            }

            long value = (long)(k - 1) * P + R + solve(size);
            if (value < answer) {
                answer = value;
            }

            if (num > i) num = i + 1;
        }

        return memo[i] = answer;
    }
}