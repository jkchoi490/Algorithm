import java.io.*;
import java.util.*;

public class BAEKJOON20437 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            String W = br.readLine();
            int K = Integer.parseInt(br.readLine());

            solve(W, K);
        }
    }

    private static void solve(String w, int k) {
        Map<Character, List<Integer>> map = new HashMap<>();
        int n = w.length();

        for (int i = 0; i < n; i++) {
            char c = w.charAt(i);
            map.putIfAbsent(c, new ArrayList<>());
            map.get(c).add(i);
        }

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (char c : map.keySet()) {
            List<Integer> list = map.get(c);

            if (list.size() < k) {
                continue;
            }

            for (int i = 0; i <= list.size() - k; i++) {
                int length = list.get(i + k - 1) - list.get(i) + 1;
                min = Math.min(min, length);
                max = Math.max(max, length);
            }
        }

        if (min == Integer.MAX_VALUE || max == Integer.MIN_VALUE) {
            System.out.println(-1);
        } else {
            System.out.println(min + " " + max);
        }
    }
}
