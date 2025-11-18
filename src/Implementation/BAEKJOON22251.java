package Implementation;

import java.io.*;
import java.util.*;

// BAEKJOON22251 - 빌런 호석
public class BAEKJOON22251 {


    static int diff(int a, int b) {
        int x = seg[a] ^ seg[b];
        return Integer.bitCount(x);
    }

    static String toFixed(int num, int P) {
        String s = Integer.toString(num);
        while (s.length() < P) s = "0" + s;
        return s;
    }

    static int[] seg = {
            0b1110111,
            0b0010010,
            0b1011101,
            0b1011011,
            0b0111010,
            0b1101011,
    };

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int P = Integer.parseInt(st.nextToken());
        int X = Integer.parseInt(st.nextToken());

        int answer = 0;

        String xStr = toFixed(X, P);

        for (int num = 1; num <= N; num++) {

            if (num == X) continue;

            String target = toFixed(num, P);

            int cnt = 0;

            for (int i = 0; i < P; i++) {
                int a = xStr.charAt(i) - '0';
                int b = target.charAt(i) - '0';
                cnt += diff(a, b);
                if (cnt > K) break;
            }

            if (cnt > 0 && cnt <= K) answer++;
        }

        System.out.println(answer);
    }
}
