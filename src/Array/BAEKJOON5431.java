import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BAEKJOON5431 {
    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());
        StringTokenizer st;
        long[] p = new long[51];
        p[0] = 1;
        for (int i = 1; i < 51; ++i) {
            p[i] = p[i - 1] * 2;
        }
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine());
            int[] a = new int[51];
            long ans = 0;
            for (int i = 0; i < n; ++i) {
            	st = new StringTokenizer(br.readLine());
                a[i] = Integer.parseInt(st.nextToken());
                int j = 0;
                int[] cnt = new int[50];
                int[] c = new int[1001];
                if (i > 0 && a[i] >= a[i - 1]) {
                    continue;
                }
                for (; j < i && a[i] > a[j]; ++j) {
                    cnt[c[a[j]]]--;
                    cnt[++c[a[j]]]++;
                }
                if (j == i) {
                    continue;
                }
                long temp = p[j];
                for (int k = 2; k < i + 1; ++k) {
                    temp /= Math.pow(p[k], (long)cnt[k]);
                    temp *= Math.pow((long)(k + 1), (long)cnt[k]);
                }
                ans += temp;
                Arrays.sort(a, 0, i + 1);
            }
            System.out.println(ans);
        }
    }
}