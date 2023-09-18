import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BAEKJOON25710 {
    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int N = Integer.parseInt(br.readLine());
        int[] v = new int[1000];
        for (int i = 0; i < N; i++) {
        	st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            v[x]++;
        }
        List<Integer> u = new ArrayList<>();
        for (int i = 1; i <= 999; i++) {
            if (v[i] == 1) u.add(i);
            else if (v[i] >= 2) {
                u.add(i);
                u.add(i);
            }
        }
        int ans = 0;
        for (int i = 0; i < u.size(); i++) {
            for (int j = i + 1; j < u.size(); j++) {
                int tmp = u.get(i) * u.get(j);
                int sum = 0;
                while (tmp > 0) {
                    sum += tmp % 10;
                    tmp /= 10;
                }
                ans = Math.max(ans, sum);
            }
        }
        System.out.println(ans);
    }
}