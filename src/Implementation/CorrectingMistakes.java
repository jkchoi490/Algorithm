package Implementation;

import java.io.*;
import java.util.*;

//CodeForces - Correcting Mistakes
public class CorrectingMistakes {

    static boolean check(String A, int delA, String B, int delB) {
        int n = A.length();

        int i = 0, j = 0;
        while (i < n && j < n) {
            if (i == delA) i++;
            if (j == delB) j++;
            if (i < n && j < n) {
                if (A.charAt(i) != B.charAt(j)) return false;
            }
            i++;
            j++;
        }
        return true;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        String S = br.readLine();
        String T = br.readLine();

        int L = 0;
        while (L < n && S.charAt(L) == T.charAt(L)) L++;

        int ans = 0;

        if (L + 1 < n) {
            if (check(S, L, T, L + 1)) ans++;
            if (check(T, L, S, L + 1)) ans++;
        } else {

            if (check(S, L, T, L)) ans++;
            if (check(T, L, S, L)) ans++;
        }

        System.out.println(ans);
    }
}
