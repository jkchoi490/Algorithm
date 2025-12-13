package Implementation;

import java.io.*;
import java.util.*;

// AtCoder - Remove It
public class RemoveIt {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int X = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < N; i++) {
            int a = Integer.parseInt(st.nextToken());
            if (a != X) {
                sb.append(a).append(" ");
            }
        }

        // trailing space 허용 (AtCoder OK)
        System.out.println(sb.toString().trim());
    }
}
