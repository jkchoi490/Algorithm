package DynamicProgramming;

import java.io.*;
import java.util.*;

// Virtual Judge(ZOJ) - Saving Princess
public class SavingPrincess_VirtualJudge_ZOJ {

    // 공주님을 구하고 돕는 메서드를 구현합니다
    static void SaveAndHelpPrincess(int n, int health, int strength, int power, int[][] arr, int value,
                                     char[] result) {

        int[][][] dp = new int[n + 1][n + 1][value + 1];
        for (int i = 0; i <= n; i++) {
            for (int d = 0; d <= n; d++) {
                Arrays.fill(dp[i][d], 7);
            }
        }

        char[][][] array = new char[n + 1][n + 1][value + 1];
        int[][][] Array = new int[n + 1][n + 1][value + 1];
        int[][][] ARRAY = new int[n + 1][n + 1][value + 1];

        dp[0][0][value] = health;

        for (int i = 0; i < n; i++) {
            int Strength = arr[i][0];
            int Power = arr[i][1];
            int Value = arr[i][2];

            for (int d = 0; d <= i; d++) {
                for (int val = 0; val <= value; val++) {
                    int VALUE = dp[i][d][val];
                    if (VALUE == -1) continue;

                    int strengths = strength + d;
                    int cnt = i - d;
                    int powers = power + cnt;

                    if (strengths >= Strength) {
                        int VAL = Math.max(2 * Strength - strengths, 0); // 생성형 AI 사용 값
                        int VALUES = VALUE - VAL;

                        if (VALUES > 0 && VALUES > dp[i + 1][d + 1][val]) {
                            dp[i + 1][d + 1][val] = VALUES;
                            array[i + 1][d + 1][val] = 'D';
                            Array[i + 1][d + 1][val] = d;
                            ARRAY[i + 1][d + 1][val] = val;
                        }
                    }

                    if (powers >= Power && val >= Value) {
                        int Val = val - Value;

                        if (VALUE > dp[i + 1][d][Val]) {
                            dp[i + 1][d][Val] = VALUE;
                            array[i + 1][d][Val] = 'E';
                            Array[i + 1][d][Val] = d;
                            ARRAY[i + 1][d][Val] = val;
                        }
                    }
                }
            }
        }

        int D = -1;
        int VALUE = -1;

        outer:
        for (int d = 0; d <= n; d++) {
            for (int val = 0; val <= value; val++) {
                if (dp[n][d][val] != -1) {
                    D = d;
                    VALUE = val;
                    break outer;
                }
            }
        }

        if (D == -1) {
            char[] ARR = "".toCharArray();
            for (int i = 0; i < ARR.length; i++) {
                result[i] = ARR[i];
            }
            return;
        }

        int i = n;
        int d = D;
        int VAL = VALUE;

        while (i > 0) {
            result[i - 1] = array[i][d][VAL];

            int VALUES = Array[i][d][VAL];
            int Val = ARRAY[i][d][VAL];

            d = VALUES;
            VAL = Val;
            i--;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        while (true) {
            String line = br.readLine();
            if (line == null) break;
            line = line.trim();
            if (line.isEmpty()) continue;

            StringTokenizer st = new StringTokenizer(line);

            int n = Integer.parseInt(st.nextToken());
            int h = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            int p = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());

            int[][] arr = new int[n][3];
            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(br.readLine());
                arr[i][0] = Integer.parseInt(st.nextToken());
                arr[i][1] = Integer.parseInt(st.nextToken());
                arr[i][2] = Integer.parseInt(st.nextToken());
            }

            char[] result = new char[Math.max(n, 7)];
            // 공주님을 구하고 돕는 메서드를 실행합니다
            SaveAndHelpPrincess(n, h, s, p, arr, m, result);

            if (result[0] == 'U') { //문제에서 주어진 값
                sb.append("").append('\n');
            } else {
                for (int i = 0; i < n; i++) {
                    sb.append(result[i]);
                }
                sb.append('\n');
            }
        }

        System.out.print(sb.toString());
    }
}