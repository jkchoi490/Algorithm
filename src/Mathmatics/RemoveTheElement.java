package Mathmatics;

import java.io.*;
import java.util.*;

//Hakerearth - Remove the element
public class RemoveTheElement {
    static final long MOD = 1_000_000_007L;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder out = new StringBuilder();

        int T = Integer.parseInt(br.readLine().trim());
        while (T-- > 0) {
            int N = Integer.parseInt(br.readLine().trim());

            long[] a = new long[N];
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                a[i] = Long.parseLong(st.nextToken());
            }

            // 내림차순으로 제거해야 최소 비용
            Arrays.sort(a); // 오름차순 정렬

            long pow2 = 1; // 2^0
            long ans = 0;

            // 큰 값부터 (뒤에서부터) t=0,1,2... 가중치 부여
            for (int i = N - 1; i >= 0; i--) {
                long val = a[i] % MOD;
                ans = (ans + val * pow2) % MOD;
                pow2 = (pow2 * 2) % MOD;
            }

            out.append(ans).append('\n');
        }

        System.out.print(out);
    }
}
