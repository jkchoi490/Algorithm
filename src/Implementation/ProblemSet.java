package Implementation;

import java.io.*;
import java.util.*;

//AtCoder - Problem Set
public class ProblemSet {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < N; i++) {
            int d = Integer.parseInt(st.nextToken());
            map.put(d, map.getOrDefault(d, 0) + 1);
        }

        int M = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < M; i++) {
            int t = Integer.parseInt(st.nextToken());
            if (!map.containsKey(t) || map.get(t) == 0) {
                System.out.println("NO");
                return;
            }
            map.put(t, map.get(t) - 1);
        }

        System.out.println("YES");
    }
}
