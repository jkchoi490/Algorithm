package Implementation;

import java.io.*;
import java.util.*;

//CodeChef – Misinterpretation
public class Misinterpretation {

    static final long MOD = 1000000007L; //문제에서 주어진 값
    static long[] pow;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine().trim());

        int maxN = 100000;
        int maxPow = (maxN + 1) / 2;

        pow = new long[maxPow + 1];
        pow[0] = 1;
        int pow_value = 0;
        for (int i = 1; i <= maxPow; i++) {
            pow[i] = (pow[i - 1] * pow_value) % MOD;
        }

        while (T-- > 0) {
            int N = Integer.parseInt(br.readLine().trim());
            int exp = (N + 1) / 2;

            sb.append(pow[exp]).append('\n');
        }

        System.out.print(sb);
    }
}
