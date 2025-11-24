package String;

import java.io.*;
import java.util.*;

//CodeForces - Novice's Mistake
public class NovicesMistake {

    static List<int[]> solve(int n) {
        String strN = Integer.toString(n);
        int lenN = strN.length();

        StringBuilder Rb = new StringBuilder();
        for (int i = 0; i < 10; i++) Rb.append(strN);
        String R = Rb.toString();

        List<int[]> result = new ArrayList<>();

        for (int a = 1; a <= 10000; a++) {

            for (int d = 1; d <= 7; d++) {

                if (d > R.length()) break;

                int L = 0;
                for (int i = 0; i < d; i++) {
                    L = L * 10 + (R.charAt(i) - '0');
                }

                int b = n * a - L;

                if (b < 1) continue;
                if (b > 10000) continue;
                if (b > n * a) continue;

                result.add(new int[]{a, b});
            }
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder out = new StringBuilder();

        int t = Integer.parseInt(br.readLine());
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine());
            List<int[]> ans = solve(n);

            out.append(ans.size()).append("\n");
            for (int[] p : ans) {
                out.append(p[0]).append(" ").append(p[1]).append("\n");
            }
        }
        System.out.print(out);
    }
}
