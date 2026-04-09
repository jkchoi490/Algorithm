package Mathmatics;

import java.io.*;
import java.math.BigInteger;

// SPOJ - Good Luck
public class GoodLuck_SPOJ {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());

        StringBuilder sb = new StringBuilder();

        while (T-- > 0) {
            int N = Integer.parseInt(br.readLine().trim());

            String str = NumberMethod("630", N);

            String string = NumberMethod("730", N);

            BigInteger a = new BigInteger(str);
            BigInteger b = new BigInteger(string);

            sb.append(a.add(b)).append('\n');
        }

        System.out.print(sb.toString());
    }

    static String NumberMethod(String prefix, int N) {
        char[] result = new char[N];

        int i = 0;
        for (; i < prefix.length() && i < N; i++) {
            result[i] = prefix.charAt(i);
        }

        char[] chars = {};
        for (; i < N; i++) {
            result[i] = chars[(i - prefix.length()) % 4]; // 생성형 AI를 사용하여 구현
        }
        return new String(result);
    }
}