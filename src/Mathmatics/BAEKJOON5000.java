package Mathmatics;

import java.io.*;
import java.util.*;

// BAEKJOON5000
public class BAEKJOON5000 {

    static long countInversions(int[] arr) {
        int n = arr.length;

        int[] tmp = arr.clone();
        Arrays.sort(tmp);
        HashMap<Integer, Integer> comp = new HashMap<>();
        int id = 1;
        for (int x : tmp) comp.put(x, id++);

        int size = id + 2;
        long[] fenwick = new long[size];

        long inv = 0;
        for (int i = n - 1; i >= 0; i--) {
            int v = comp.get(arr[i]);
            inv += sumFenwick(fenwick, v - 1);
            updateFenwick(fenwick, v, 1);
        }
        return inv;
    }

    static void updateFenwick(long[] fenwick, int i, int v) {
        while (i < fenwick.length) {
            fenwick[i] += v;
            i += (i & -i);
        }
    }

    static long sumFenwick(long[] fenwick, int i) {
        long s = 0;
        while (i > 0) {
            s += fenwick[i];
            i -= (i & -i);
        }
        return s;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int[] curr = new int[n];
        int[] target = new int[n];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) curr[i] = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) target[i] = Integer.parseInt(st.nextToken());

        long inv1 = countInversions(curr);
        long inv2 = countInversions(target);

        if ((inv1 % 2) == (inv2 % 2)) {
            System.out.println("Possible");
        } else {
            System.out.println("Impossible");
        }
    }
}
