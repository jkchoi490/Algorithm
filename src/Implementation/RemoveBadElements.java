package Implementation;

import java.util.*;
import java.io.*;

// CodeChef - Remove Bad elements
public class RemoveBadElements {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());
        while (T-- > 0) {

            int N = Integer.parseInt(br.readLine());
            StringTokenizer st = new StringTokenizer(br.readLine());

            int[] freq = new int[N + 1];
            int maxFreq = 0;

            for (int i = 0; i < N; i++) {
                int x = Integer.parseInt(st.nextToken());
                freq[x]++;
                if (freq[x] > maxFreq) maxFreq = freq[x];
            }

            int answer = N - maxFreq;
            sb.append(answer).append("\n");
        }

        System.out.print(sb.toString());
    }
}
