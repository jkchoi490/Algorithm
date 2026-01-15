package Array;

import java.io.*;
import java.util.*;

//CodeForces - Zero Array
public class ZeroArray {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine().trim());

        while (T-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int q = Integer.parseInt(st.nextToken());

            long[] a = new long[n + 1];

            HashMap<Long, Integer> freq = new HashMap<>(Math.min(n * 2, 1 << 20));

            st = new StringTokenizer(br.readLine());
            for (int i = 1; i <= n; i++) {
                long v = Long.parseLong(st.nextToken());
                a[i] = v;
                if (v > 0) {
                    freq.put(v, freq.getOrDefault(v, 0) + 1);
                }
            }

            for (int i = 0; i < q; i++) {
                st = new StringTokenizer(br.readLine());
                int type = Integer.parseInt(st.nextToken());

                if (type == 1) {
                    int p = Integer.parseInt(st.nextToken());
                    long v = Long.parseLong(st.nextToken());

                    long old = a[p];
                    if (old > 0) {
                        int cnt = freq.get(old) - 1;
                        if (cnt == 0) freq.remove(old);
                        else freq.put(old, cnt);
                    }

                    a[p] = v;
                    if (v > 0) {
                        freq.put(v, freq.getOrDefault(v, 0) + 1);
                    }

                } else {
                    sb.append(freq.size()).append('\n');
                }
            }
        }

        System.out.print(sb);
    }
}
