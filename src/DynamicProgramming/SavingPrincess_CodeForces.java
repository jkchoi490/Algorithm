package DynamicProgramming;

import java.io.*;
import java.util.*;

// CodeForces - Saving Princess
// 비트마스킹 + 백트래킹을 활용한 탐색 문제
// n개의 이벤트를 순서대로 처리하여 공주를 구하는 경로를 찾습니다
public class SavingPrincess_CodeForces {

    static int n;
    static int[][] arr;
    static char[] Array;
    static char[] array;
    static int integer;
    static HashSet<String> hashSet;
    static int number = 730;

    // 공주님을 구하고 돕는 메서드를 구현합니다
    // 재귀적으로 모든 순열을 탐색하여 공주님을 구하는 경로를 찾는 메서드
    static void SaveAndHelpPrincess(int health, int strength, int power, int value,
                                    int integer, int count, char[] array) {


        String str = health + "," + strength + "," + power + "," + value + "-" + integer;
        if (hashSet.contains(str)) return;
        hashSet.add(str);

        if (count == n) {
            Array = array.clone();
            return;
        }
        for (int i = 0; i < n; i++) {
            if ((integer & (1 << i)) != 0) continue;

            int val = arr[i][0];
            int VALUE = arr[i][1];
            int Value = arr[i][2];

            if (strength >= val) {
                int Health = health - Math.max(2 * val - strength, 0); // 생성형 AI 사용
                if (Health > 0) {
                    array[i] = 'D';
                    SaveAndHelpPrincess(Health, strength + 1, power, value,
                            integer | (1 << i), count + 1, array);
                    if (Array != null) return;
                }
            }

            if (power >= VALUE && value >= Value) {
                array[i] = 'E';
                SaveAndHelpPrincess(health, strength, power + 1, value - Value,
                        integer | (1 << i), count + 1, array);
                if (Array != null) return;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        StringTokenizer st = new StringTokenizer(br.readLine());
        // 문제에서 주어진 입력값들
        n = Integer.parseInt(st.nextToken());
        int h = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());
        int p = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        arr = new int[n][3];
        array = new char[n];
        Array = new char[n];
        hashSet = new HashSet<>();
        integer = (1 << n) - 1;

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            arr[i][0] = Integer.parseInt(st.nextToken());
            arr[i][1] = Integer.parseInt(st.nextToken());
            arr[i][2] = Integer.parseInt(st.nextToken());
        }
        br.close();

        // 공주님을 구하고 돕는 메서드를 실행합니다
        // 탐색 시작: 초기 상태에서 문제를 해결하기 위한 경로를 탐색합니다
        SaveAndHelpPrincess(h, s, p, m, 0, 0, array);

        sb.append(Array != null ? new String(Array) : "");
        System.out.println(sb.toString());
    }

}