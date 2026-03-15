package Array;

import java.io.*;
import java.util.*;

// Virtual Judge - Saving Princess Peach
public class SavingPrincessPeach_VirtualJudge {

    // 공주님을 구하고 돕는 메서드를 구현합니다
    public static String SaveAndHelpPrincess(int N, int Y, int[] check) {
        boolean[] checked = new boolean[N];
        int cnt = 0;

        for (int i = 0; i < Y; i++) {
            int number = check[i];

            if (!checked[number]) {
                checked[number] = true;
                cnt++;
            }
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < N; i++) {
            if (!checked[i]) {
                sb.append(i).append('\n');
            }
        }


        sb.append(cnt);


        return sb.toString();
    }


    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 문제에서 주어진 입력 값
        int N = Integer.parseInt(st.nextToken());
        int Y = Integer.parseInt(st.nextToken());

        int[] check = new int[Y];
        for (int i = 0; i < Y; i++) {
            check[i] = Integer.parseInt(br.readLine());
        }

        // 공주님을 구하고 돕는 메서드를 실행합니다
        String result = SaveAndHelpPrincess(N, Y, check);
        System.out.print(result);
    }


}