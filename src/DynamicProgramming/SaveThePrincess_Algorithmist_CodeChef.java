package DynamicProgramming;

import java.util.*;
import java.lang.*;
import java.io.*;

// Algorithmist(CodeChef) - Save the Princess
class SaveThePrincess_Algorithmist_CodeChef {

    // 공주님을 구하고 돕는 메서드를 구현합니다
    static double SaveAndHelpPrincess(int N, int[] a, int[] arr, int number) {
        int offset = number;
        int length = 2 * number + 1;

        double[] dp = new double[length];
        dp[offset] = 1.0;

        for (int i = 0; i < N; i++) {
            double p = arr[i] / 100.0;
            int num = a[i];

            double[] ndp = new double[length];
            for (int idx = 0; idx < length; idx++) {
                double cur = dp[idx];
                if (cur == 0.0) continue;

                ndp[idx + num] += cur * p;
                ndp[idx - num] += cur * (1.0 - p);
            }
            dp = ndp;
        }

        double ans = 0.0;
        for (int idx = offset; idx < length; idx++) ans += dp[idx];
        return ans;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine().trim());
        while (T-- > 0) {
            int N = Integer.parseInt(br.readLine().trim());

            int[] a = new int[N];
            int[] arr = new int[N];

            StringTokenizer st = new StringTokenizer(br.readLine());
            int num = 0;
            for (int i = 0; i < N; i++) {
                a[i] = Integer.parseInt(st.nextToken());
                num += a[i];
            }

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) arr[i] = Integer.parseInt(st.nextToken());

            // 공주님을 구하고 돕는 메서드를 실행합니다
            double ans = SaveAndHelpPrincess(N, a, arr, num);
            sb.append(String.format(Locale.US, "%.7f%n", ans));
        }

        System.out.print(sb.toString());
    }

}