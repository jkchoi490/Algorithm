package String;

import java.io.*;
import java.util.*;

//HackerRank - The Love-Letter Mystery
public class LoveLetter {

    private static int minOperationsToPalindrome(String s) {
        int n = s.length();
        int ops = 0;

        for (int i = 0; i < n / 2; i++) {
            char left = s.charAt(i);
            char right = s.charAt(n - 1 - i);
            ops += Math.abs(left - right);
        }
        return ops;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int q = Integer.parseInt(br.readLine().trim());
        StringBuilder out = new StringBuilder();

        for (int t = 0; t < q; t++) {
            String s = br.readLine().trim();
            out.append(minOperationsToPalindrome(s)).append('\n');
        }

        System.out.print(out);
    }
}
