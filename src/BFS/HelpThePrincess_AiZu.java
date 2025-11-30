package BFS;

import java.io.*;
import java.util.*;

//Aizu Online Judge - Help the Princess!
public class HelpThePrincess_AiZu {

    static int H, W;
    static char[][] map;
    static int[][] princessDist, soldierDist;
    static final int INF = 1_000_000_000;

    static int[] dr = {1, -1, 0, 0};
    static int[] dc = {0, 0, 1, -1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String line = br.readLine();
            if (line == null || line.trim().isEmpty()) return;

            StringTokenizer st = new StringTokenizer(line);
            H = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());

            map = new char[H][W];
            soldierDist = new int[H][W];
            princessDist = new int[H][W];

            for (int i = 0; i < H; i++)
                Arrays.fill(soldierDist[i], INF);

            int pr = 0, pc = 0;
            int hr = 0, hc = 0;

            Queue<int[]> q = new ArrayDeque<>();

            for (int i = 0; i < H; i++) {
                String s = br.readLine();
                map[i] = s.toCharArray();
                for (int j = 0; j < W; j++) {
                    char c = map[i][j];
                    if (c == '@') {
                        pr = i; pc = j;
                    } else if (c == '%') {
                        hr = i; hc = j;
                    } else if (c == '$') {
                        q.add(new int[]{i, j});
                        soldierDist[i][j] = 0;
                    }
                }
            }

            bfsSoldiers(q);

            boolean canEscape = bfsPrincess(pr, pc, hr, hc);

            System.out.println(canEscape ? "Yes" : "No");
        }
    }

    static boolean bfsPrincess(int pr, int pc, int hr, int hc) {
        for (int i = 0; i < H; i++)
            Arrays.fill(princessDist[i], INF);

        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[]{pr, pc});
        princessDist[pr][pc] = 0;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int r = cur[0], c = cur[1];
            int cd = princessDist[r][c];

            // Escape hatch condition:
            if (r == hr && c == hc) {
                if (cd < soldierDist[r][c]) return true;
            }

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];

                if (!inRange(nr, nc)) continue;
                if (map[nr][nc] == '#') continue;

                int nd = cd + 1;


                if (nd >= soldierDist[nr][nc]) continue;

                if (princessDist[nr][nc] > nd) {
                    princessDist[nr][nc] = nd;
                    q.add(new int[]{nr, nc});
                }
            }
        }

        return false;
    }

    static void bfsSoldiers(Queue<int[]> q) {
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int r = cur[0], c = cur[1];
            int cd = soldierDist[r][c];

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];
                if (!inRange(nr, nc)) continue;
                if (map[nr][nc] == '#') continue;
                if (soldierDist[nr][nc] > cd + 1) {
                    soldierDist[nr][nc] = cd + 1;
                    q.add(new int[]{nr, nc});
                }
            }
        }
    }
    static boolean inRange(int r, int c) {
        return (0 <= r && r < H && 0 <= c && c < W);
    }
}
