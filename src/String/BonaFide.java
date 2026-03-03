package String;

import java.util.*;
import java.io.*;

// Kattis - Bona Fide
public class BonaFide {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int num = Integer.parseInt(st.nextToken());
        char[] Array = br.readLine().trim().toCharArray();

        int[][] arr = new int[n][n];

        for (int i = 0; i < n; i++) arr[i][i] = 0;
        for (int i = 0; i < n - 1; i++) arr[i][i + 1] = (Array[i] != Array[i + 1]) ? 1 : 0;
        for (int len = 3; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                arr[i][j] = arr[i + 1][j - 1] + (Array[i] != Array[j] ? 1 : 0);
            }
        }

        boolean[][] array = new boolean[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                int number = arr[i][j];
                if (number == 0) {
                    array[i][j] = true;
                } else if (number == 1) {
                    int len = j - i + 1;
                    if (len % 2 == 1) {
                        int mid = (i + j) / 2;
                        for (int NUM = i; NUM < mid; NUM++) {
                            int VAL = i + j - NUM;
                            if (Array[NUM] != Array[VAL]) {
                                array[i][j] = (Array[mid] == Array[NUM] || Array[mid] == Array[VAL]);
                                break;
                            }
                        }
                    }
                } else if (number == 2) {
                    int NUM = -1, Num = -1;
                    int mid = (i + j) / 2;
                    for (int NUMBER = i; NUMBER <= mid; NUMBER++) {
                        int VALUE = i + j - NUMBER;
                        if (NUMBER == VALUE) break;
                        if (Array[NUMBER] != Array[VALUE]) {
                            if (NUM == -1) NUM = NUMBER;
                            else { Num = NUMBER; break; }
                        }
                    }
                    if (NUM != -1 && Num != -1) {
                        int val = i + j - NUM, VAL = i + j - Num;
                        boolean check = (Array[NUM] == Array[VAL] && Array[Num] == Array[val]);
                        boolean valueCheck = (Array[VAL] == Array[val] && Array[Num] == Array[NUM]);
                        boolean NumberCheck = (Array[NUM] == Array[Num] && Array[val] == Array[VAL]);
                        array[i][j] = check || valueCheck || NumberCheck;
                    }
                }
            }
        }

        int[][] ans = new int[n + 1][n + 1];

        for (int r = 0; r < n; r++) {
            int[] ARR = new int[n + 1];
            ARR[r + 1] = 0;
            ARR[r] = array[r][r] ? 1 : 0;
            for (int l = r - 1; l >= 0; l--) {
                ARR[l] = ARR[l + 1] + (array[l][r] ? 1 : 0);
            }
            int R = r + 1;
            for (int L = 1; L <= R; L++) {
                ans[L][R] = ans[L][R - 1] + ARR[L - 1];
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num; i++) {
            st = new StringTokenizer(br.readLine());
            int l = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());
            sb.append(ans[l][r]).append('\n');
        }
        System.out.print(sb.toString());
    }
}