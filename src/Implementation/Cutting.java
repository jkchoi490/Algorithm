package Implementation;

import java.io.*;
import java.util.*;

//Codeforce - Cutting
public class Cutting {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int B = Integer.parseInt(st.nextToken());

        int[] a = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(st.nextToken());
        }

        List<Integer> cutCosts = new ArrayList<>();

        int odd = 0, even = 0;

        for (int i = 0; i < n - 1; i++) {
            if (a[i] % 2 == 0) even++;
            else odd++;

            if (odd == even) {
                int cost = Math.abs(a[i] - a[i + 1]);
                cutCosts.add(cost);
            }
        }

        Collections.sort(cutCosts);

        int cuts = 0;
        int money = 0;

        for (int cost : cutCosts) {
            if (money + cost > B) break;
            money += cost;
            cuts++;
        }

        System.out.println(cuts);
    }
}
