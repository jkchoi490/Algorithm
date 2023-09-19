import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BAEKJOON1777 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] original = new int[N + 1];
        int[] modified = new int[N + 1];
        int[] result = new int[N + 1];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            original[i] = Integer.parseInt(st.nextToken());
        }

        for (int i = 1; i <= N; i++) {
            modified[i] = i;
        }

        for (int i = N; i >= 1; i--) {
            int k = N - i;
            int j = k + original[i] + 1;
            while (modified[j] != 0) {
                j++;
            }
            result[j] = i;
        }

        for (int i = 1; i <= N; i++) {
            System.out.print(result[i] + " ");
        }
    }
}
