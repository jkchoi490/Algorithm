package Implementation;

import java.io.*;
import java.util.*;

//AtCoder - Cut off
//https://atcoder.jp/contests/abc321/tasks/abc321_b
public class Cutoff {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int X = Integer.parseInt(st.nextToken());

        int[] A = new int[N - 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N - 1; i++) {
            A[i] = Integer.parseInt(st.nextToken());
        }

        // Try all possible scores from 0 to 100
        for (int t = 0; t <= 100; t++) {
            int[] arr = new int[N];
            System.arraycopy(A, 0, arr, 0, N - 1);
            arr[N - 1] = t;

            Arrays.sort(arr);

            int sum = 0;
            for (int i = 1; i < N - 1; i++) {
                sum += arr[i];
            }

            if (sum >= X) {
                System.out.println(t);
                return;
            }
        }

        System.out.println(-1);
    }
}
