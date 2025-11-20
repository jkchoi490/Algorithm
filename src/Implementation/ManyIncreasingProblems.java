package Implementation;


import java.io.*;
import java.util.*;

public class ManyIncreasingProblems {

    static long MOD;

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int N = fs.nextInt();
        int M = fs.nextInt();

        /*
         * 해설 기반 공식:
         *
         * Many Increasing Problems 의 답 =
         *
         *   sum_{d=1..N} ( f(d) )
         *
         * 여기서 f(d) =
         *     ( (M * (M-1)) % MOD * inv2 % MOD ) * C(N + d - 1, d - 1 )   (d의 빈도에 해당)
         *     = 전체 수식은 공식 해설에서 다음 형태로 귀결됨:
         *
         * 최종 수식:
         *     ans = sum_{k=1..N} ( C(N+k-1, k) * C(M, 2) )
         *
         * 단, k = d - 1 로 나타나는 shift 있음.
         *
         * 실제로 에디토리얼의 최종 정리된 식은:
         *
         *     sum_{i=1..N} C(N+i-1, i-1) * C(M,2)
         *
         * 즉,
         *     C(N+i-1, i-1) = C(N+i-1, N-1)
         *
         * 고정된 M,N 에 대해 C(M,2) 는 상수.
         */

        long C_M_2 = ((long) M * (M - 1) % MOD) * inv2() % MOD;

        // 팩토리얼 전처리: 최대 필요는 N + N 정도
        int MAX = N * 2 + 5;
        long[] fact = new long[MAX];
        long[] invFact = new long[MAX];

        fact[0] = 1;
        for (int i = 1; i < MAX; i++) fact[i] = fact[i - 1] * i % MOD;

        invFact[MAX - 1] = modinv(fact[MAX - 1]);
        for (int i = MAX - 2; i >= 0; i--) invFact[i] = invFact[i + 1] * (i + 1) % MOD;

        // 조합 함수
        java.util.function.BiFunction<Integer, Integer, Long> C = (n, r) -> {
            if (r < 0 || r > n) return 0L;
            return fact[n] * invFact[r] % MOD * invFact[n - r] % MOD;
        };

        long ans = 0;

        for (int i = 1; i <= N; i++) {
            // C(N+i-1, i-1)
            long comb = C.apply(N + i - 1, i - 1);
            ans = (ans + comb) % MOD;
        }

        ans = ans * C_M_2 % MOD;

        System.out.println(ans);
    }

    static long inv2() {
        return (MOD + 1) / 2;
    }

    static long modinv(long a) {
        long r = 1, e = MOD - 2;
        while (e > 0) {
            if ((e & 1) == 1) r = r * a % MOD;
            a = a * a % MOD;
            e >>= 1;
        }
        return r;
    }

    // FastScanner
    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;

        FastScanner(InputStream is) {
            in = is;
        }

        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }

        int nextInt() throws IOException {
            int c;
            while ((c = read()) <= ' ') ;
            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }
            int val = c - '0';
            while ((c = read()) >= '0') val = val * 10 + (c - '0');
            return val * sign;
        }
    }
}
