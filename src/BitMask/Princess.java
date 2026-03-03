package BitMask;

import java.io.*;
import java.util.*;

// SPOJ - Princess
public class Princess {

    // 공주님 메서드를 구현합니다
    static long PrincessMethod(long num, long k) {
        if (num == 0 || k <= 0) return -1;

        int number = Long.bitCount(num);
        if (number == 0) return -1;

        if (number < Long.SIZE) {
            long maxCount = (1L << number) - 1;
            if (k > maxCount) return -1;
        } else {
            if (k <= 0) return -1;
        }

        long ans = 0L;
        long value = k;
        int idx = 0;

        for (int pos = 0; pos < Long.SIZE; pos++) {
            if (((num >>> pos) & 1L) != 0) {
                if (((value >>> idx) & 1L) != 0) {
                    ans |= (1L << pos);
                }
                idx++;
            }
        }
        return ans;
    }

    static final class FastBufferedReader {
        private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in), 1 << 16);
        private StringTokenizer st;

        String next() throws IOException {
            while (st == null || !st.hasMoreTokens()) {
                String line = br.readLine();
                if (line == null) return null;
                st = new StringTokenizer(line);
            }
            return st.nextToken();
        }

        long nextLong() throws IOException {
            String s = next();
            if (s == null) return Long.MIN_VALUE;
            return Long.parseLong(s);
        }
    }

    public static void main(String[] args) throws Exception {
        FastBufferedReader fs = new FastBufferedReader();
        long t = fs.nextLong();
        if (t == Long.MIN_VALUE) return;
        int T = (int) t;

        StringBuilder sb = new StringBuilder(Math.min(T, 1_000_000) * 7);

        for (int i = 0; i < T; i++) {
            long N = fs.nextLong();
            long K = fs.nextLong();
            sb.append(PrincessMethod(N, K)).append('\n');
        }

        System.out.print(sb.toString());
    }
}