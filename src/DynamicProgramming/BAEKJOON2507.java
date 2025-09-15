package DynamicProgramming;

import java.io.*;
import java.util.*;

//공주 구하기
public class BAEKJOON2507 {
    static int N;
    static int[] location;  // 위치
    static int[] g;  // 앞으로 이동 가능 거리
    static int[] b;  // 돌아올 때 사용 가능한 섬 여부
    static final int MOD = 1000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine().trim());

        location = new int[N];
        g = new int[N];
        b = new int[N];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            location[i] = Integer.parseInt(st.nextToken());
            g[i] = Integer.parseInt(st.nextToken());
            b[i] = Integer.parseInt(st.nextToken());
        }

        int[][] dp = new int[N][N];

        for (int j = 0; j < N; j++) {
            if (b[N - 1] == 1 && (location[N - 1] - location[j]) <= g[N - 1]) {
                dp[N - 1][j] = 1;
            }
        }

        for (int i = N - 2; i >= 0; i--) {
            for (int j = N - 2; j >= 0; j--) {
                long ways = 0;
                int start = Math.max(i, j) + 1;

                // 앞으로 가는 쪽 이동
                for (int ni = start; ni < N; ni++) {
                    if (location[ni] - location[i] <= g[i]) {
                        ways += dp[ni][j];
                    } else {
                        break;
                    }
                }

                // 돌아오는 쪽 이동
                for (int nj = start; nj < N; nj++) {
                    if (b[nj] == 1 && (location[nj] - location[j]) <= g[nj]) {
                        ways += dp[i][nj];
                    }
                }

                dp[i][j] = (int) (ways % MOD);
            }
        }

        System.out.println(dp[0][0]);
    }
}
