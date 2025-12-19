package DynamicProgramming;

import java.util.*;
import java.io.*;

//Kattis - Debugging
public class Debugging {
    static long n, r, g;
    static Map<Long, Long> memo = new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Long.parseLong(st.nextToken());
        r = Long.parseLong(st.nextToken());
        g = Long.parseLong(st.nextToken());

        System.out.println(solve(n));
    }

    static long solve(long lines) {
        if (lines <= 1) return 0;

        if (memo.containsKey(lines)) {
            return memo.get(lines);
        }

        long result = Long.MAX_VALUE;

        for (long k = 1; k < lines && k <= 100; k++) {
            long cost = k * g + r;

            long maxSegment = (lines - 1) / (k + 1) + 1;

            long total = cost + solve(maxSegment);
            result = Math.min(result, total);
        }

        memo.put(lines, result);
        return result;
    }
}