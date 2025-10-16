package Recursive_Tree_Graph;

//Jungol - 공주 구하기
import java.io.*;
import java.util.*;

public class Solution_공주구하기 {
    static final int MOD = 1000;
    static int n;
    static int[][] ar = new int[501][3];
    static int[][] dp = new int[501][501];

    static int memo(int idx1, int idx2) {
        // idx1: 현재 공주를 구하러 가는 위치
        // idx2: 현재 공주를 데리고 돌아가는 위치

        if (idx1 == n) {

            return (ar[idx1][0] - ar[idx2][0]) <= ar[idx1][1] ? 1 : 0;
        }

        if (dp[idx1][idx2] != -1) {
            return dp[idx1][idx2] % MOD;
        }

        int ret = 0;
        int minStart = Math.max(idx1, idx2) + 1;

        int curPos = ar[idx1][0];
        int dist = ar[idx1][1];


        for (int i = minStart; i <= n; i++) {
            int nextPos = ar[i][0];
            if (nextPos - curPos > dist) break;
            ret += memo(i, idx2);
            if (ret >= MOD) ret %= MOD;
        }


        curPos = ar[idx2][0];
        for (int i = minStart; i < n; i++) {
            if (ar[i][2] == 0) continue;
            int nextPos = ar[i][0];
            if (nextPos - curPos > ar[i][1]) continue;
            ret += memo(idx1, i);
            if (ret >= MOD) ret %= MOD;
        }

        return dp[idx1][idx2] = ret % MOD;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());

        for (int i = 1; i <= n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            ar[i][0] = Integer.parseInt(st.nextToken());
            ar[i][1] = Integer.parseInt(st.nextToken());
            ar[i][2] = Integer.parseInt(st.nextToken());
        }

        for (int[] row : dp) Arrays.fill(row, -1);

        System.out.println(memo(1, 0) % MOD);
    }
}
