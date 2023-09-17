import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BAEKJOON1565 {
    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	StringTokenizer st = new StringTokenizer(br.readLine());
    	int d = Integer.parseInt(st.nextToken());
    	int m = Integer.parseInt(st.nextToken());
        long[] D = new long[d];
        long[] M = new long[m];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < d; i++) {
            D[i] = Long.parseLong(st.nextToken());
        }
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) {
            M[i] = Long.parseLong(st.nextToken());
        }
        long vgcd = M[0];
        for (int i = 1; i < m; i++) {
            vgcd = gcd(vgcd, M[i]);
        }
        long vlcm = 1;
        for (int i = 0; i < d; i++) {
            vlcm = lcm(vlcm, D[i]);
            if (vlcm > vgcd || vlcm == 0) {
                System.out.println(0);
                return;
            }
        }
        long i, cnt = 0;
        for (i = 1; i * i < vgcd; i++) {
            if (vgcd % i == 0) {
                if (i % vlcm == 0) {
                    cnt++;
                }
                if ((vgcd / i) % vlcm == 0) {
                    cnt++;
                }
            }
        }
        if (i * i == vgcd && (i % vlcm == 0)) {
            cnt++;
        }
        System.out.println(cnt);
    }

    public static long gcd(long a, long b) {
        while (b != 0) {
            long r = a % b;
            a = b;
            b = r;
        }
        return a;
    }

    public static long lcm(long a, long b) {
        return a * b / gcd(a, b);
    }
}