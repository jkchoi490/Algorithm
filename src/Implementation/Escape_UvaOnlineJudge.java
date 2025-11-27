package Implementation;

import java.io.*;
import java.util.*;

//Uva Online Judge - Escape
public class Escape_UvaOnlineJudge {

    static int M, N, H;
    static int L;

    static int encode(int r, int c) {
        if (r == 0) return c;
        if (c == N - 1) return N - 1 + r;
        if (r == M - 1) return (N - 1) + (M - 1) + (N - 1 - c);
        return (N - 1) + (M - 1) + (N - 1) + (M - 1 - r);
    }

    static int computeT(int D, int targetPos, int need) {
        int raw = targetPos - D;
        raw %= L;
        if (raw < 0) raw += L;

        int t = raw;
        if (t < need) {
            int diff = need - t;
            int k = (diff + L - 1) / L;
            t += k * L;
        }
        return t;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        while (true) {
            st = new StringTokenizer(br.readLine());
            M = Integer.parseInt(st.nextToken());
            N = Integer.parseInt(st.nextToken());
            H = Integer.parseInt(st.nextToken());
            if (M == 0 && N == 0 && H == 0) break;

            L = 2 * M + 2 * N - 4;

            int[] doors = new int[H];

            for (int i = 0; i < H; i++) {
                st = new StringTokenizer(br.readLine());
                int r = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                doors[i] = encode(r, c);
            }

            st = new StringTokenizer(br.readLine());
            int pr = Integer.parseInt(st.nextToken());
            int pc = Integer.parseInt(st.nextToken());

            ArrayList<int[]> targets = new ArrayList<>();

            if (pr > 0) {
                int need = pr;
                int pos = encode(0, pc);
                targets.add(new int[]{pos, need});
            }

            if (pr < M - 1) {
                int need = (M - 1 - pr);
                int pos = encode(M - 1, pc);
                targets.add(new int[]{pos, need});
            }

            if (pc > 0) {
                int need = pc;
                int pos = encode(pr, 0);
                targets.add(new int[]{pos, need});
            }

            if (pc < N - 1) {
                int need = (N - 1 - pc);
                int pos = encode(pr, N - 1);
                targets.add(new int[]{pos, need});
            }

            int bestTime = Integer.MAX_VALUE;
            int bestDist = Integer.MAX_VALUE;

            for (int D : doors) {
                for (int[] tg : targets) {
                    int targetPos = tg[0];
                    int need = tg[1];

                    int t = computeT(D, targetPos, need);
                    int dist = need + 1;

                    if (t < bestTime || (t == bestTime && dist < bestDist)) {
                        bestTime = t;
                        bestDist = dist;
                    }
                }
            }

            System.out.println(bestTime + " " + bestDist);
        }
    }
}
