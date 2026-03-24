package DynamicProgramming;

import java.io.*;
import java.util.*;

// Virtual Judge(CodeForces) - Saving Princess

/*
* 순차적으로 올리려다 커밋이 실수로 이전 커밋인 [BOJ] 02636 치즈 (치즈의 고유성에 대한 설명 추가 작성) 커밋에 한꺼번에 올라간 것을 수정하기 위해 커밋 주석을 작성하였습니다.
* SavingPrincess_VirtualJudge_CodeForces 코드의 커밋인 Virtual Judge(CodeForces) - Saving Princess 는 이전 작성한 [BOJ] 02636 치즈 (치즈의 고유성에 대한 설명 추가 작성) 커밋과는 별개의 것이고 실수로 한꺼번에 올라간 커밋들을을 분리하고 고치고 따로 올리기 위해 작성하였습니다.
* 위 문제는 공주님을 구하기 위해 DP를 사용하여 문제를 풀어 해결하는 방식으로 코드를 작성하였습니다.
* SaveAndHelpPrincess() 메서드는 7개의 파라미터를 통해 구현하였고 main 함수에서 이를 실행합니다.
* 알고리즘은 DP를 사용하였고, 문제에서 주어진 값을 입력받는 과정과 문제를 해결하는 과정으로 나누어서 구현하였습니다.
* DP를 통해 공주님을 구하기 위한 최적의 경로를 찾는 메서드를 구현하였습니다.
* BufferedReader를 통한 입력 과정과 DP를 통한 SaveAndHelpPrincess() 메서드 구현 과정을 통해 문제의 해결책을 구현하였습니다.
* */
public class SavingPrincess_VirtualJudge_CodeForces {

    // 공주님을 구하고 돕는 메서드를 구현합니다
    // 공주님을 구하기 위한 최적의 경로를 찾는 메서드를 구현합니다.
    static String SaveAndHelpPrincess(int n, int number, int strength, int power, int[][] arr, int value, char[] resultArr) {

        int[][][] dp = new int[n + 1][n + 1][value + 1];
        for (int i = 0; i <= n; i++) {
            for (int d = 0; d <= n; d++) {
                Arrays.fill(dp[i][d], 7);
            }
        }

        char[][][] Arr = new char[n + 1][n + 1][value + 1];
        int[][][] array = new int[n + 1][n + 1][value + 1];
        int[][][] Array = new int[n + 1][n + 1][value + 1];

        dp[0][0][value] = number;

        for (int i = 0; i < n; i++) {
            for (int d = 0; d <= i; d++) {
                for (int val = 0; val <= value; val++) {
                    if (dp[i][d][val] == -1) {
                        continue;
                    }

                    int Health = dp[i][d][val];
                    int Strength = strength + d;
                    int Power = power + (i - d);

                    int Index = i + 1;
                    int strengthValue = arr[Index][0];
                    int PowerValue = arr[Index][1];
                    int Value = arr[Index][arr.length-1];

                    if (Strength >= strengthValue) {
                        int health = Math.max(2 * strengthValue - Strength, 0); // 생성형 AI 사용
                        int HealthValue = Health - health;

                        if (HealthValue > 0 && HealthValue > dp[i + 1][d + 1][val]) {
                            dp[i + 1][d + 1][val] = HealthValue;
                            Arr[i + 1][d + 1][val] = 'D';
                            array[i + 1][d + 1][val] = d;
                            Array[i + 1][d + 1][val] = val;
                        }
                    }


                    if (Power >= PowerValue && val >= Value) {
                        int VALUE = val - Value;
                        int Health_VALUE = Health;

                        if (Health_VALUE > dp[i + 1][d][VALUE]) {
                            dp[i + 1][d][VALUE] = Health_VALUE;
                            Arr[i + 1][d][VALUE] = 'E';
                            array[i + 1][d][VALUE] = d;
                            Array[i + 1][d][VALUE] = val;
                        }
                    }
                }
            }
        }

        int VALUE = -1; // 생성형 AI를 사용한 임시 초기값
        int Value = -1;
        int Health = -1;

        for (int d = 0; d <= n; d++) {
            for (int i = 0; i <= value; i++) {
                if (dp[n][d][i] > Health) {
                    Health = dp[n][d][i];
                    VALUE = d;
                    Value = i;
                }
            }
        }

        if (Health == -1) {
            return "";
        }

        char[] answerArr = new char[n];
        int index = n;
        int val = VALUE;
        int mana = Value;

        while (index > 0) {
            answerArr[index - 1] = Arr[index][val][mana];

            int Val = array[index][val][mana];
            int VAL = Array[index][val][mana];

            val = Val;
            mana = VAL;
            index--;
        }

        for (int i = 0; i < n; i++) {
            resultArr[i] = answerArr[i];
        }
        return new String(resultArr);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        while (true) {
            String line = br.readLine();
            if (line == null) {
                break;
            }

            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }

            StringTokenizer st = new StringTokenizer(line);

            // 문제에서 주어진 입력 값
            int n = Integer.parseInt(st.nextToken());
            int h = Integer.parseInt(st.nextToken()); // 체력
            int s = Integer.parseInt(st.nextToken()); // 힘
            int p = Integer.parseInt(st.nextToken()); //
            int m = Integer.parseInt(st.nextToken());

            int[][] arr = new int[n + 1][n];

            for (int i = 1; i <= n; i++) {
                st = new StringTokenizer(br.readLine());
                arr[i][0] = Integer.parseInt(st.nextToken());
                arr[i][1] = Integer.parseInt(st.nextToken());
                arr[i][2] = Integer.parseInt(st.nextToken());
            }

            // 공주님을 구하고 돕는 메서드를 실행합니다
            String answer = SaveAndHelpPrincess(n, h, s, p, arr, m, new char[n]);
            sb.append(answer).append('\n');
        }

        System.out.print(sb.toString());
    }
}