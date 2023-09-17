import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Arrays;

public class BAEKJOON13448  {
    static class Node implements Comparable<Node> {
        long M;
        long P;
        long R;

        Node() {
            M = 0L;
            P = 0L;
            R = 0L;
        }

        public int compareTo(Node rhs) {
            return Long.compare(R * rhs.P, rhs.R * P);
        }
    }

    static Node[] arr;
    static long[][] dp;
    static int N;
    static long T;

    static long path(int idx, long time) {
        if (idx == N) return 0L;
        long ret = dp[idx][(int) time];
        if (ret != -1) return ret;
        ret = path(idx + 1, time);
        if (time + arr[idx].R <= T)
            ret = Math.max(ret, arr[idx].M - (time + arr[idx].R) * arr[idx].P + path(idx + 1, time + arr[idx].R));
        return ret;
    }

    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        T = Long.parseLong(st.nextToken());
        arr = new Node[N];
    
        for (int i = 0; i < N; ++i) arr[i] = new Node();
        for (int i = 0; i < N; ++i) {
        	st = new StringTokenizer(br.readLine());
        	arr[i].M = Long.parseLong(st.nextToken());
        }
        	
        for (int i = 0; i < N; ++i) {
        	st = new StringTokenizer(br.readLine());
        	arr[i].P = Long.parseLong(st.nextToken());
        }
        	
        for (int i = 0; i < N; ++i) {
        	st = new StringTokenizer(br.readLine());
        	arr[i].R = Long.parseLong(st.nextToken());
        }
        	
        Arrays.sort(arr);
        dp = new long[55][111111];
        for (int i = 0; i < 55; ++i) Arrays.fill(dp[i], -1);
        System.out.println(path(0, 0L));
    }
}
