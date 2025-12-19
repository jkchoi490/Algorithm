package Implementation;

import java.util.*;
import java.io.*;

//CodeChef - Back to Campus
public class BackToCampus {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());

            int days = (N + K - 1) / K;

            sb.append(days).append('\n');
        }

        System.out.print(sb);
    }
}