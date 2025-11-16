package Implementation;

import java.io.*;
import java.util.*;

//Hackerearth - Save the Princess
class Solution_SavethePrincess {
    public static void main(String args[] ) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());

        int[] A = new int[N + 1];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            A[i] = Integer.parseInt(st.nextToken());
        }


        int[][] prefix = new int[31][N + 1];

        for (int bit = 0; bit < 31; bit++) {
            for (int i = 1; i <= N; i++) {
                prefix[bit][i] = prefix[bit][i - 1];
                if (((A[i] >> bit) & 1) == 1) {
                    prefix[bit][i]++;
                }
            }
        }

        StringBuilder sb = new StringBuilder();

        while (Q-- > 0) {
            st = new StringTokenizer(br.readLine());
            int L = Integer.parseInt(st.nextToken());
            int R = Integer.parseInt(st.nextToken());

            int len = R - L + 1;

            long X = 0;

            for (int bit = 0; bit < 31; bit++) {
                int count1 = prefix[bit][R] - prefix[bit][L - 1];
                int count0 = len - count1;


                if (count0 > count1) {
                    X |= (1L << bit);
                }
            }

            sb.append(X).append('\n');
        }

        System.out.print(sb.toString());
    }
}
