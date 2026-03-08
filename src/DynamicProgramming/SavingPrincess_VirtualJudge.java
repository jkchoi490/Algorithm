package DynamicProgramming;

import java.io.*;
import java.util.*;

// Virtual Judge - Saving Princess
public class SavingPrincess_VirtualJudge {

    // 공주님을 구하고 돕는 메서드를 구현합니다
    static String SaveAndHelpPrincess(int number, int strength, int power, int value,
                              int[] strengths, int[] powers, int[] Arr) {

        int n = strengths.length - 1;

        int[][][] dp = new int[n + 1][n + 1][value + 1];
        for (int i = 0; i <= n; i++) {
            for (int d = 0; d <= n; d++) {
                Arrays.fill(dp[i][d], -1);
            }
        }

        char[][][] arr = new char[n + 1][n + 1][value + 1];
        int[][][] array = new int[n + 1][n + 1][value + 1];
        int[][][] Array = new int[n + 1][n + 1][value + 1];

        dp[0][0][value] = number;

        for (int i = 0; i < n; i++) {
            for (int d = 0; d <= i; d++) {
                for (int j = 0; j <= value; j++) {
                    if (dp[i][d][j] == -1) continue;

                    int cur = dp[i][d][j];
                    int curStrength = strength + d;
                    int curPower = power + (i - d);

                    int idx = i + 1;

                    if (curStrength >= strengths[idx]) {
                        int Value = Math.max(2 * strengths[idx] - curStrength, 0);
                        int VALUE = cur - Value;

                        if (VALUE > 0) {
                            if (VALUE > dp[i + 1][d + 1][j]) {
                                dp[i + 1][d + 1][j] = VALUE;
                                arr[i + 1][d + 1][j] = 'D';
                                array[i + 1][d + 1][j] = d;
                                Array[i + 1][d + 1][j] = j;
                            }
                        }
                    }

                    if (curPower >= powers[idx] && j >= Arr[idx]) {
                        int val = j - Arr[idx];
                        int Value = cur;

                        if (Value > dp[i + 1][d][val]) {
                            dp[i + 1][d][val] = Value;
                            arr[i + 1][d][val] = 'E';
                            array[i + 1][d][val] = d;
                            Array[i + 1][d][val] = j;
                        }
                    }
                }
            }
        }

        int val = -1; //임시 초기 값(생성형 AI 사용)
        int VAL = -1;
        int VALUE = -1;

        for (int d = 0; d <= n; d++) {
            for (int i = 0; i <= value; i++) {
                if (dp[n][d][i] > VALUE) {
                    VALUE = dp[n][d][i];
                    val = d;
                    VAL = i;
                }
            }
        }

        if (VALUE == -1) { // 생성형 AI 사용 VALUE값
            return "LUCKY";
        }

        char[] ARR = new char[n];
        int i = n;
        int d = val;
        int Val = VAL;

        while (i > 0) {
            ARR[i - 1] = arr[i][d][Val];
            int array_value = array[i][d][Val];
            int Array_value = Array[i][d][Val];
            d = array_value;
            Val = Array_value;
            i--;
        }

        return new String(ARR);
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
            // 문제에서 주어진 순차적으로 입력받는 값
            int n = Integer.parseInt(st.nextToken());

            // 메서드 파라미터 입력받는 값
            int h = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            int p = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());
            int[] strengths = new int[n + 1];
            int[] powers = new int[n + 1];
            int[] arrays = new int[n + 1];

            for (int i = 1; i <= n; i++) {
                st = new StringTokenizer(br.readLine());
                strengths[i] = Integer.parseInt(st.nextToken());
                powers[i] = Integer.parseInt(st.nextToken());
                arrays[i] = Integer.parseInt(st.nextToken());
            }

            // 공주님을 구하고 돕는 메서드를 실행합니다
            String answer = SaveAndHelpPrincess(h, s, p, m, strengths, powers, arrays);
            sb.append(answer).append('\n');
        }

        System.out.print(sb.toString());
    }

}