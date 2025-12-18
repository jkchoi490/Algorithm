package Mathmatics;

import java.util.*;
import java.io.*;

// Kattis - Program
public class Program {
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        long[] seq = new long[N];
        int[] Counts = new int[N + 1];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < K; i++) {
            int cnt = Integer.parseInt(st.nextToken());
            Counts[cnt]++;
        }


        for (int j = 1; j < N + 1; j++) {
            if (Counts[j] == 0) continue;

            for (int i = 0; i < N; i += j) {
                seq[i] += Counts[j];
            }
        }

        long[] prefixSum = new long[N + 1];
        for (int i = 0; i < N; i++) {
            prefixSum[i + 1] = prefixSum[i] + seq[i];
        }

        int Q = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Q; i++) {
            st = new StringTokenizer(br.readLine());
            int L = Integer.parseInt(st.nextToken());
            int R = Integer.parseInt(st.nextToken());

            sb.append(prefixSum[R + 1] - prefixSum[L]).append("\n");
        }

        System.out.print(sb.toString());
    }
}