package Implementation;

import java.io.*;
import java.util.*;

//Kattis - Saving Princess Peach
public class SavingPrincessPeach {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        boolean[] found = new boolean[N];

        for (int i = 0; i < M; i++) {
            int x = Integer.parseInt(br.readLine());
            if (x >= 0 && x < N) {
                found[x] = true;
            }
        }

        int distinct = 0;

        for (int i = 0; i < N; i++) {
            if (!found[i]) {
                System.out.println(i);
            } else {
                distinct++;
            }
        }

        System.out.println("Mario got " + distinct + " of the dangerous obstacles.");
    }
}
