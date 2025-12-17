package DynamicProgramming;

import java.io.*;

//Kattis - Going To School
public class GoingToSchool {
    static final int INF = 1_000_000_000;

    static int crossCost(int side, char t) {

        if (t == 'B') return 1;
        if (side == 0 && t == 'N') return 1;
        if (side == 1 && t == 'S') return 1;
        return 0;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine().trim();
        int m = s.length();

        int north = 0;
        int south = 1;

        int dpN = 0;
        int dpS = INF;

        for (int i = 0; i < m; i++) {
            char t = s.charAt(i);

            int nextN = INF;
            int nextS = INF;

            nextN = Math.min(nextN, dpN + crossCost(north, t));
            nextS = Math.min(nextS, dpN + 1 + crossCost(south, t));

            nextS = Math.min(nextS, dpS + crossCost(south, t));
            nextN = Math.min(nextN, dpS + 1 + crossCost(north, t));

            dpN = nextN;
            dpS = nextS;
        }

        System.out.println(dpN);
    }
}
