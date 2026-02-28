package Mathmatics;

import java.util.*;
import java.io.*;

// Algorithmist(SPOJ) - Save the Princess
public class SaveThePrincess_Algorithmist_SPOJ {

    // 공주님을 구하고 돕는 메서드를 구현합니다
    private static java.math.BigInteger SaveAndHelpPrincess(java.math.BigInteger a, java.math.BigInteger b) {
        if (a.bitLength() < b.bitLength()) {
            return a.multiply(b);
        }
        return a.add(b);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();

        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            java.math.BigInteger a = new java.math.BigInteger(st.nextToken());
            java.math.BigInteger b = new java.math.BigInteger(st.nextToken());

            // 공주님을 구하고 돕는 메서드를 실행합니다
            sb.append(SaveAndHelpPrincess(a, b));
            sb.append('\n');
        }

        System.out.print(sb.toString());
    }
}