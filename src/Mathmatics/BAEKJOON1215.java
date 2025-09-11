package Mathmatics;

import java.io.*;
import java.util.*;

//잘못 작성한 요세푸스 코드
public class BAEKJOON1215  {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        long n = Long.parseLong(st.nextToken());
        long k = Long.parseLong(st.nextToken());

        long result = 0;

        long i = 1;
        long limit = (long)Math.sqrt(k);

        long directLimit = Math.min(limit, n);
        for (; i <= directLimit; i++) {
            result += k % i;
        }

        long prev = directLimit;
        while (i <= n) {
            long q = k / i;
            if (q == 0) {

                result += (n - i + 1) * k;
                break;
            }

            long r = k / q;
            if (r > n) r = n;

            long cnt = (r - i + 1);
            long sumI = sumRange(i, r);
            result += cnt * k - q * sumI;

            i = r + 1;
        }

        System.out.println(result);
    }

    static long sumTo(long x) {
        return x * (x + 1) / 2;
    }

    static long sumRange(long a, long b) {
        return sumTo(b) - sumTo(a - 1);
    }
}
