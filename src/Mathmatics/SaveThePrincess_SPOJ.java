package Mathmatics;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

// SPOJ - Save The Princess
public class SaveThePrincess_SPOJ {

    // 공주님을 구하고 돕는 메서드를 구현합니다
    static BigInteger SaveAndHelpPrincess(BigInteger A, BigInteger B) {
        if (A.max(B).equals(B) && !A.equals(B)) {
            return A.multiply(B);
        } else {
            return A.add(B);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int T;
        String line;

        while (true) {
            line = br.readLine();
            if (line == null) return;
            line = line.trim();
            if (!line.isEmpty()) break;
        }
        T = Integer.parseInt(line);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < T; i++) {
            StringTokenizer st;
            while (true) {
                line = br.readLine();
                if (line == null) return;
                line = line.trim();
                if (!line.isEmpty()) {
                    st = new StringTokenizer(line);
                    if (st.countTokens() >= 2) break;
                }
            }

            BigInteger A = new BigInteger(st.nextToken());
            BigInteger B = new BigInteger(st.nextToken());

            // 공주님을 구하고 돕는 메서드를 실행합니다
            sb.append(SaveAndHelpPrincess(A, B)).append('\n');
        }

        System.out.print(sb.toString());
    }
}