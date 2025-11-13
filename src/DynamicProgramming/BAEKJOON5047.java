package DynamicProgramming;

import java.io.*;
import java.util.*;

// Fixing the Bugs
public class BAEKJOON5047{

    // B: 버그의 총 개수, T: 남은 작업 시간(최대 T번 시도 가능)
    static int B, T;
    // f: 실패 시 성공 확률 감소율 (새로운 확률 = 이전 확률 * f)
    static double f;
    // p[i]: i번째 버그를 고칠 현재 성공 확률
    static double[] p;
    // s[i]: i번째 버그를 고쳤을 때 얻는 점수
    static int[] s;
    // fail[i]: i번째 버그를 고치려는 시도의 누적 실패 횟수
    static int[] fail;
    // dp[mask][tLeft][F]:
    // mask: 고친 버그의 상태를 나타내는 비트마스크 (0부터 1 << B - 1)
    // tLeft: 남은 작업 시간 (0부터 T)
    // F: 전체 버그를 고치려는 시도의 누적 실패 횟수
    static double[][][] dp;


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        StringBuilder out = new StringBuilder();

        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;

            StringTokenizer st = new StringTokenizer(line);
            if (!st.hasMoreTokens()) continue;

            B = Integer.parseInt(st.nextToken());
            T = Integer.parseInt(st.nextToken());
            f = Double.parseDouble(st.nextToken());

            p = new double[B];
            s = new int[B];
            fail = new int[B];

            for (int i = 0; i < B; i++) {
                st = new StringTokenizer(br.readLine());
                p[i] = Double.parseDouble(st.nextToken());
                s[i] = Integer.parseInt(st.nextToken());
                fail[i] = 0;
            }

            int masks = 1 << B;
            dp = new double[masks][T + 1][T + 1];
            // dp 초기화: -1 이면 아직 계산 안 된 상태
            for (int m = 0; m < masks; m++) {
                for (int t = 0; t <= T; t++) {
                    Arrays.fill(dp[m][t], -1.0);
                }
            }

            double ans = calc(0, T, 0);
            out.append(String.format(Locale.US, "%.12f\n", ans));
        }

        System.out.print(out);
    }

    // 기대 점수를 계산하는 재귀 함수
    static double calc(int mask, int tLeft, int F) {
        // 모두 고친 경우
        if (mask + 1 == (1 << B)) return 0.0;
        if (tLeft == 0) return 0.0;

        double cached = dp[mask][tLeft][F];
        if (cached >= 0.0) return cached;

        // 현재 상태에서 p[i] * s[i]가 최대인 버그 선택 (그리디)
        int id = -1;
        double best = -1e100; // 아주 작은 값
        for (int i = 0; i < B; i++) {
            if ((mask & (1 << i)) != 0) continue; // 이미 고친 버그
            double val = p[i] * s[i];
            if (val > best) {
                best = val;
                id = i;
            }
        }

        // 더 고칠 버그가 없다면 0
        if (id == -1) {
            return dp[mask][tLeft][F] = 0.0;
        }

        double res = 0.0;

        // 성공 분기: 확률 p[id]
        double pPrev = p[id];
        int failPrev = fail[id];

        // 성공하면 id 버그는 fixed, 남은 실패 합에서 fail[id] 제외
        int newMask = mask | (1 << id);
        int newFSuccess = F - fail[id];
        if (newFSuccess < 0) newFSuccess = 0;

        res += pPrev * (s[id] + calc(newMask, tLeft - 1, newFSuccess));

        // 실패 분기: 확률 (1 - pPrev)
        p[id] *= f;    // 확률 감소
        fail[id]++;    // 실패 횟수 증가
        int newFFail = F + 1;

        res += (1.0 - pPrev) * calc(mask, tLeft - 1, newFFail);

        // 원상 복구 (백트래킹)
        p[id] = pPrev;
        fail[id] = failPrev;

        dp[mask][tLeft][F] = res;
        return res;
    }
}
