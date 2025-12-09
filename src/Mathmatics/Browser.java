package Mathmatics;

import java.io.*;
import java.util.*;

// CodeForces - Browser
public class Browser {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int pos = Integer.parseInt(st.nextToken());
        int l = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());

        if (l == 1 && r == n) {
            System.out.println(0);
            return;
        }

        int res = Integer.MAX_VALUE;

        if (l > 1) {
            int cost = Math.abs(pos - l) + 1;
            int newPos = l;

            if (r < n) {
                cost += Math.abs(newPos - r) + 1;
            }
            res = Math.min(res, cost);
        }

        if (r < n) {
            int cost = Math.abs(pos - r) + 1;
            int newPos = r;

            if (l > 1) {
                cost += Math.abs(newPos - l) + 1;
            }
            res = Math.min(res, cost);
        }

        System.out.println(res);
    }
}
