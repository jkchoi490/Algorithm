package Sorting_and_Searching;

import java.io.*;
import java.util.*;

// CodeChef - Remove Element
public class RemoveElement {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            long K = Long.parseLong(st.nextToken());

            long[] A = new long[N];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                A[i] = Long.parseLong(st.nextToken());
            }

            if (N == 1) {
                sb.append("YES\n");
                continue;
            }

            Arrays.sort(A);

            if (A[0] + A[N - 1] <= K) sb.append("YES\n");
            else sb.append("NO\n");
        }

        System.out.print(sb.toString());
    }
}
