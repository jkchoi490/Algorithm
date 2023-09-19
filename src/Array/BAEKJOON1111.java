import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BAEKJOON1111 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        int[] numbers = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < N; i++) {
            numbers[i] = Integer.parseInt(st.nextToken());
        }

        // 수열이 1개이거나 2개일 때는 어떤 규칙도 찾을 수 없음
        if (N == 1 || N == 2) {
            System.out.println("A");
            return;
        }

        int a, b;

        // 수열이 3개 이상인 경우
        if (numbers[0] == numbers[1]) {
            a = 0;
            b = 0;
        } else {
            a = (numbers[2] - numbers[1]) / (numbers[1] - numbers[0]);
            b = numbers[1] - a * numbers[0];
        }

        for (int i = 1; i < N; i++) {
            if (numbers[i - 1] * a + b != numbers[i]) {
                System.out.println("B");
                return;
            }
        }

        System.out.println(numbers[N - 1] * a + b);
    }
}
