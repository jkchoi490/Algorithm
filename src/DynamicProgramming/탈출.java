package DynamicProgramming;

import java.util.*;
import java.io.*;

//탈출
public class 탈출 {
    static final long MOD = 1_000_000_007L;

    static int N, M;
    static long D;

    static long[][] mul(long[][] A, long[][] B) {
        long[][] C = new long[N][N];
        for (int i = 0; i < N; i++) {
            for (int k = 0; k < N; k++) {
                if (A[i][k] == 0) continue;
                for (int j = 0; j < N; j++) {
                    C[i][j] = (C[i][j] + A[i][k] * B[k][j]) % MOD;
                }
            }
        }
        return C;
    }

    static long[][] pow(long[][] A, long exp) {
        long[][] res = new long[N][N];
        for (int i = 0; i < N; i++) res[i][i] = 1; // 단위행렬
        while (exp > 0) {
            if ((exp & 1) == 1) res = mul(res, A);
            A = mul(A, A);
            exp >>= 1;
        }
        return res;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        D = Long.parseLong(st.nextToken());

        long[][] A = new long[N][N];
        // 전이행렬 생성
        for (int i = 0; i < N; i++) {
            for (int k = 1; k <= M; k++) {
                int j = (i - k + N) % N;
                A[i][j] = 1;
            }
        }

        long[][] AD = pow(A, D);
        // 초기 dp[0] = [1, 0, 0, ...]
        System.out.println(AD[0][0] % MOD);
    }
}
