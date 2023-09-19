import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BAEKJOON1817 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken()); // 물건의 수
        int K = Integer.parseInt(st.nextToken()); // 가방의 용량

        int[] weights = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            weights[i] = Integer.parseInt(st.nextToken());
        }

        int bags = 0; // 필요한 가방의 수
        int currentWeight = 0; // 현재 가방에 담긴 무게

        for (int i = 0; i < N; i++) {
            if (currentWeight + weights[i] > K) {
                bags++;
                currentWeight = 0;
            }
            currentWeight += weights[i];
        }

        if (currentWeight > 0) {
            bags++;
        }

        System.out.println(bags);
    }
}
