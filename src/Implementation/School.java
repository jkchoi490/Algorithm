package Implementation;

import java.util.*;
import java.io.*;

// CodeForces - School
public class School {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[] g = new int[n + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            g[i] = Integer.parseInt(st.nextToken());
        }

        int[] v = new int[m + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= m; i++) {
            v[i] = Integer.parseInt(st.nextToken());
        }

        int[] b = new int[m + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= m; i++) {
            b[i] = Integer.parseInt(st.nextToken());
        }

        int[] News = new int[n + 1];
        int[] res = new int[m + 1];

        for (int day = 1; day <= m; day++) {

            int a = ((v[day] + res[day - 1] - 1) % n) + 1;

            int current = a;
            int rating = b[day];

            while (rating > 0) {

                if (News[current] == 0) {
                    News[current] = day;
                    res[day]++;
                }

                current = g[current];
                rating--;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= m; i++) {
            sb.append(res[i]).append('\n');
        }
        System.out.print(sb);
    }
}