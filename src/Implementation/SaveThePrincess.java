package Implementation;

import java.io.*;
import java.util.*;

// Algorithmist - Save the Princess
public class SaveThePrincess {

    // 공주님을 구하고 돕는 메서드를 구현합니다
    public static String saveAndHelpPrincess(int N, int[] A) {

        int value = A[1];

        for (int i = 1; i < N; i++) {

            if (i > value) {
                return "-1";
            }

            if (A[i] > value) {
                value = A[i];
            }

            if (value >= N) {
                return "+1";
            }
        }

        return (value >= N) ? "+1" : "-1";
    }


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String str = br.readLine();
        if (str == null) return;

        int T = Integer.parseInt(str.trim());
        StringBuilder sb = new StringBuilder();

        while (T-- > 0) {
            int N = Integer.parseInt(br.readLine().trim());
            StringTokenizer st = new StringTokenizer(br.readLine());

            int[] positions = new int[N + 1];
            for (int i = 1; i <= N; i++) {
                positions[i] = Integer.parseInt(st.nextToken());
            }
            // 공주님을 구하고 돕는 메서드를 실행합니다
            sb.append(saveAndHelpPrincess(N, positions)).append("\n");
        }

        System.out.print(sb.toString());
    }

}