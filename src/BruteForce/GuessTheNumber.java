package BruteForce;

import java.io.*;
import java.util.*;

// AtCoder - Guess The Number
public class GuessTheNumber {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[] s = new int[M];
        int[] c = new int[M];

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            s[i] = Integer.parseInt(st.nextToken());
            c[i] = Integer.parseInt(st.nextToken());
        }

        int start, end;
        if (N == 1) {
            start = 0;
            end = 9;
        } else {
            start = (int) Math.pow(10, N - 1);
            end = (int) Math.pow(10, N) - 1;
        }

        for (int x = start; x <= end; x++) {
            String str = Integer.toString(x);
            boolean ok = true;
            for (int i = 0; i < M; i++) {
                int idx = s[i] - 1;
                int digit = str.charAt(idx) - '0';
                if (digit != c[i]) {
                    ok = false;
                    break;
                }
            }
            if (ok) {
                System.out.println(x);
                return;
            }
        }

        System.out.println(-1);
    }
}
