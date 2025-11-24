package Array;

import java.io.*;
import java.util.*;

//AtCoder - Error Correction
public class ErrorCorrection {

    static String T;
    static char[] t;
    static int tlen;


    static boolean solve(char[] s) {
        int diff = 0;
        for (int i = 0; i < tlen; i++) {
            if (s[i] != t[i]) {
                diff++;
                if (diff > 1) return false;
            }
        }
        return true;
    }

    static boolean Insert(char[] s) {
        int ns = s.length;
        int i = 0, j = 0;
        int diff = 0;
        while (i < ns && j < tlen) {
            if (s[i] == t[j]) {
                i++; j++;
            } else {
                diff++;
                if (diff > 1) return false;
                j++;
            }
        }
        return true;
    }

    static boolean check(char[] s) {
        int ns = s.length;
        int i = 0, j = 0;
        int diff = 0;
        while (i < ns && j < tlen) {
            if (s[i] == t[j]) {
                i++; j++;
            } else {
                diff++;
                if (diff > 1) return false;
                i++;
            }
        }
        return true;
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(br.readLine());
        T = br.readLine();
        t = T.toCharArray();
        tlen = t.length;

        List<Integer> ans = new ArrayList<>();

        for (int i = 1; i <= N; i++) {
            String S = br.readLine();
            char[] s = S.toCharArray();
            int slen = s.length;

            if (slen == tlen) {
                if (solve(s)) ans.add(i);
            }
            else if (slen + 1 == tlen) {
                if (Insert(s)) ans.add(i);
            }
            else if (slen == tlen + 1) {
                if (check(s)) ans.add(i);
            }
        }

        // 출력
        sb.append(ans.size()).append("\n");
        for (int x : ans) sb.append(x).append(" ");
        if (ans.size() > 0) sb.setLength(sb.length() - 1);
        System.out.println(sb);
    }
}
