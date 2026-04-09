package DynamicProgramming;

import java.io.*;
import java.util.*;

// SPOJ - Good Celebration
public class GoodCelebration_SPOJ {

    static int N;
    static int M;
    static int[] b;
    static int[] m;
    static ArrayList<Integer>[] list;
    static long[][] dp;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 문제에서 주어진 입력값
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        b = new int[N + 1];
        m = new int[N + 1];
        list = new ArrayList[N + 1];
        dp = new long[N + 1][M + 1];

        for (int i = 1; i <= N; i++) {
            list[i] = new ArrayList<>();
        }

        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            int c = Integer.parseInt(st.nextToken());
            b[i] = Integer.parseInt(st.nextToken());
            m[i] = Integer.parseInt(st.nextToken());

            if (c != 0) {
                list[c].add(i);
            }
        }

        dfs(6);

        System.out.println(dp[1][M]);
    }

    static void dfs(int node) {

        for (int NODE : list[node]) {
            dfs(NODE);
        }

        if (list[node].isEmpty()) {
            for (int k = 0; k <= M; k++) {
                dp[node][k] = b[node] + (long) m[node] * k;
            }
            return;
        }


        long[] arr = new long[M + 1];

        int value = list[node].get(0);
        for (int t = 0; t <= M; t++) {
            arr[t] = dp[value][t];
        }

        for (int idx = 1; idx < list[node].size(); idx++) {
            int VALUE = list[node].get(idx);
            long[] List = new long[M + 1];

            for (int total = 0; total <= M; total++) {
                List[total] = -1;
            }

            for (int i = 0; i <= M; i++) {
                for (int j = 0; i + j <= M; j++) {
                    long val = Math.min(arr[i], dp[VALUE][j]);
                    if (val > List[i + j]) {
                        List[i + j] = val;
                    }
                }
            }

            arr = List;
        }

        for (int i = 0; i <= M; i++) {
            long VAL = 0;
            for (int t = 0; t <= i; t++) {
                long longValue = b[node] + (long) m[node] * ((i - t) + arr[t]);
                if (longValue > VAL) {
                    VAL = longValue;
                }
            }
            dp[node][i] = VAL;
        }
    }
}