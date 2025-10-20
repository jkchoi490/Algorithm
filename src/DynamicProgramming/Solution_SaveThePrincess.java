package DynamicProgramming;

import java.util.*;
import java.io.*;

//Save The Princess
public class Solution_SaveThePrincess {

    public static void main (String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = null;

        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {

            int N = Integer.parseInt(br.readLine());
            int[] a = new int[N];
            int[] p = new int[N];
            int total = 0;

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                a[i] = Integer.parseInt(st.nextToken());
                total += a[i];
            }

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                p[i] = Integer.parseInt(st.nextToken());
            }

            double[] dp = new double[total + 1];
            dp[0] = 1.0;

            // 현재까지 가능한 최대 합을 추적하는 변수 (최적화)
            int currentMax = 0;

            for (int i = 0; i < N; i++) {
                double prob = p[i] / 100.0;
                double[] next = new double[total + 1];

                // j의 범위를 currentMax까지만 순회하여 불필요한 계산을 줄임
                for (int j = 0; j <= currentMax; j++) {
                    double cur = dp[j];
                    if (cur == 0.0) continue;

                    // Abra 깃발일 경우: (j + a[i]) 지점으로 확률 이동
                    // (배열 인덱스 범위를 초과하는 경우는 total 크기 때문에 발생하지 않음)
                    next[j + a[i]] += cur * prob;

                    // Kadabra 깃발일 경우: j 지점으로 확률 유지
                    next[j] += cur * (1.0 - prob);
                }

                // 다음 단계의 최대 가능한 합을 업데이트
                currentMax += a[i];
                dp = next;
            }

            // 합이 total / 2를 초과하는 경우를 계산
            // (total + 1) / 2는 total이 홀수일 때 올림하여 중간값 이상을 정확히 포착
            int start = (total + 1) / 2;
            double ans = 0.0;
            for (int j = start; j <= total; j++) {
                ans += dp[j];
            }

            System.out.printf("%.7f%n", ans);
        }
    }
}
