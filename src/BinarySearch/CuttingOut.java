package BinarySearch;

import java.io.*;
import java.util.*;

//CodeForces - Cutting Out
public class CuttingOut {

    static int n, k;
    static int MAX = 200000;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        int[] freq = new int[MAX + 1];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            freq[Integer.parseInt(st.nextToken())]++;
        }

        int low = 1, high = n / k;
        int maxQ = 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            if (solve(mid, freq)) {
                maxQ = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        List<Integer> t = new ArrayList<>();

        for (int x = 1; x <= MAX && t.size() < k; x++) {
            int take = freq[x] / maxQ;
            for (int i = 0; i < take && t.size() < k; i++) {
                t.add(x);
            }
        }


        StringBuilder sb = new StringBuilder();
        for (int x : t) sb.append(x).append(' ');
        System.out.println(sb.toString());
    }

    static boolean solve(int q, int[] freq) {
        long total = 0;
        for (int x = 1; x <= MAX; x++) {
            total += freq[x] / q;
            if (total >= k) return true;
        }
        return false;
    }
}
