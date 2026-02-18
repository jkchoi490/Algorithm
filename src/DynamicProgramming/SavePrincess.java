package DynamicProgramming;

import java.io.*;
import java.util.*;

// NYOJ - Save Princess
public class SavePrincess {

    static final int MAX = 424973;

    // 공주님을 구하고 돕기 위한 메서드를 구현합니다
    private static long[] SaveAndHelpPrincess(int n) {
        long[] arr = new long[n];
        arr[0] = 7L;

        int value = 0, val = 0;
        int number = 0;

        for (int i = 1; i < n; i++) {
            long a = arr[value] * 2L;
            long b = arr[val] * 8L;
            long c = arr[number] * 7L;

            long num = Math.min(a, Math.min(b, c));
            arr[i] = num;

            if (num == a) value++;
            if (num == b) val++;
            if (num == c) number++;
        }
        return arr;
    }

    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;

        FastScanner(InputStream in) {
            this.in = in;
        }

        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }

        Integer nextIntNullable() throws IOException {
            int c;
            do {
                c = read();
                if (c == -1) return null;
            } while (c <= ' ');

            int num = 1;
            if (c == '-') {
                num = -1;
                c = read();
            }

            int val = 0;
            while (c > ' ') {
                val = val * 10 + (c - '0');
                c = read();
            }
            return val * num;
        }
    }
    public static void main(String[] args) throws Exception {

        // 공주님을 구하고 돕기 위한 메서드를 실행합니다
        long[] arr = SaveAndHelpPrincess(MAX);

        FastScanner fs = new FastScanner(System.in);
        StringBuilder sb = new StringBuilder();

        while (true) {
            Integer nObj = fs.nextIntNullable();
            if (nObj == null) break;
            int n = nObj;
            if (n < 0) break;
            sb.append(arr[n - 1]).append('\n');
        }

        System.out.print(sb.toString());
    }
}
