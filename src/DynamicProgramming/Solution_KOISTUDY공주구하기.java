package DynamicProgramming;

import java.io.*;
import java.util.*;

//공주 구하기
public class Solution_KOISTUDY공주구하기 {
    static final int MOD = 1000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int[] d = new int[n];
        int[] s = new int[n];
        int[] a = new int[n];

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            d[i] = Integer.parseInt(st.nextToken());
            s[i] = Integer.parseInt(st.nextToken());
            a[i] = Integer.parseInt(st.nextToken());
        }

        // 1️. 유시 섬 → 후퍼 섬 (오른쪽으로만 이동)
        int[] forward = new int[n];
        forward[0] = 1; // 시작점

        for (int i = 0; i < n; i++) {
            if (forward[i] == 0) continue;
            for (int j = i + 1; j < n; j++) {
                if (d[j] - d[i] <= s[i]) {
                    forward[j] = (forward[j] + forward[i]) % MOD;
                } else {
                    break; // 정렬되어 있으므로 더 멀면 불가능
                }
            }
        }

        // 2️. 후퍼 섬 → 유시 섬 (왼쪽으로만 이동, a[i] == 1만 가능)
        int[] backward = new int[n];
        backward[n - 1] = 1; // 후퍼 섬에서 시작

        for (int i = n - 1; i >= 0; i--) {
            if (a[i] == 0 || backward[i] == 0) continue; // 사용할 수 없는 발판이거나 경로 없음
            for (int j = i - 1; j >= 0; j--) {
                if (d[i] - d[j] <= s[i]) {
                    if (a[j] == 1 || j == 0) { // 돌아올 때 j섬도 이용 가능해야 함
                        backward[j] = (backward[j] + backward[i]) % MOD;
                    }
                } else {
                    break; // 더 멀면 불가능
                }
            }
        }

        // 3. 전체 경로 수 = 각 섬을 경유한 경로의 곱의 합
        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans = (ans + forward[i] * backward[i]) % MOD;
        }

        System.out.println(ans);
    }
}
