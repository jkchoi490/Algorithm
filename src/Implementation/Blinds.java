package Implementation;

import java.io.*;
import java.util.*;

// CodeForces - Blinds
public class Blinds {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int l = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int[] a = new int[n];
        int maxA = 0;

        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(st.nextToken());
            maxA = Math.max(maxA, a[i]);
        }

        long answer = 0;

        for (int d = l; d <= maxA; d++) {
            int k = 0;
            for (int x : a) {
                k += x / d;
            }
            if (k > 0) {
                long area = (long) d * k;
                answer = Math.max(answer, area);
            }
        }

        System.out.println(answer);
    }
}
