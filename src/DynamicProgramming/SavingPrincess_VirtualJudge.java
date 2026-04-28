package DynamicProgramming;

import java.io.*;
import java.util.*;

// Virtual Judge - Saving Princess
/**
 * Saving Princess 문제의 솔루션을 작성해보았습니다! (2026.04.27에 놓친 커밋 작성)
 * 공주님을 구하고 돕기 위한 솔루션을 주석으로 작성하였습니다!
 * 1. 동적 계획법(DP) 알고리즘을 사용하여 각 단계별 최적의 상태를 기록하며 진행합니다.
 * 2. 매 순간 힘(Strength)과 능력(Power)을 활용해 더 나은 선택을 하며 앞으로 나아갑니다.
 * 3. HP가 0 이하가 되지 않도록 세밀하게 관리하며 공주님이 있는 마지막 지점까지 나아갑니다.
 * 4. 저장된 경로 정보를 Backtracking하여 놓친 부분들부터 효율적인 방법 순서를 문자열로 재구성합니다.
 * 5. DP 배열은 지금까지의 선택 결과에서 더 좋은 상태를 유지하도록 갱신됩니다.
 * 6. 모든 경로를 탐색하면서 공주님을 구하기 위한 경로를 끝까지 이어가며 최적의 상태를 선택하고, 이전 선택을 따라가며 공주님을 향한 이동 경로를 복원합니다.
 * 7. 이렇게 완성된 경로를 통해 공주님에게 도달하며, 가장 안정적인 방식으로 도움을 완성합니다.
 **/
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