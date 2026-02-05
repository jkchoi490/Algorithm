package BFS;

import java.io.*;
import java.util.*;

//NowCoder - Help the Princess!
public class HelpthePrincess_NowCoder {

    static final int INF = 1_000_000_000;
    static final int[] dr = {1, -1, 0, 0};
    static final int[] dc = {0, 0, 1, -1};

    // 공주님을 구하고 돕기 위한 메서드 구현
    static boolean SaveAndHelpPrincess(char[][] map, int H, int W) {

        int pr = -1, pc = -1;
        int er = -1, ec = -1;

        ArrayDeque<int[]> q = new ArrayDeque<>();

        int[][] soldierDist = new int[H][W];
        for (int i = 0; i < H; i++) Arrays.fill(soldierDist[i], INF);

        for (int r = 0; r < H; r++) {
            for (int c = 0; c < W; c++) {
                if (map[r][c] == '@') {
                    pr = r;
                    pc = c;
                } else if (map[r][c] == '%') {
                    er = r;
                    ec = c;
                } else if (map[r][c] == '$') {
                    soldierDist[r][c] = 0;
                    q.add(new int[]{r, c});
                }
            }
        }

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int r = cur[0], c = cur[1];

            for (int d = 0; d < dr.length; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];
                if (nr < 0 || nr >= H || nc < 0 || nc >= W) continue;
                if (map[nr][nc] == '#') continue;
                if (soldierDist[nr][nc] > soldierDist[r][c] + 1) {
                    soldierDist[nr][nc] = soldierDist[r][c] + 1;
                    q.add(new int[]{nr, nc});
                }
            }
        }

        int[][] princessDist = new int[H][W];
        for (int i = 0; i < H; i++) Arrays.fill(princessDist[i], INF);

        if (soldierDist[pr][pc] == 0) return false;

        ArrayDeque<int[]> pq = new ArrayDeque<>();
        pq.add(new int[]{pr, pc});
        princessDist[pr][pc] = 0;

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int r = cur[0], c = cur[1];
            int t = princessDist[r][c];


            if (r == er && c == ec) return true;

            for (int d = 0; d < dr.length; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];
                int nt = t + 1;

                if (nr < 0 || nr >= H || nc < 0 || nc >= W) continue;
                if (map[nr][nc] == '#') continue;

                if (soldierDist[nr][nc] <= nt) continue;

                if (princessDist[nr][nc] > nt) {
                    princessDist[nr][nc] = nt;
                    pq.add(new int[]{nr, nc});
                }
            }
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        while (true) {
            String line = br.readLine();
            if (line == null || line.trim().isEmpty()) break;

            StringTokenizer st = new StringTokenizer(line);
            int H = Integer.parseInt(st.nextToken());
            int W = Integer.parseInt(st.nextToken());

            char[][] map = new char[H][W];
            for (int i = 0; i < H; i++) {
                map[i] = br.readLine().toCharArray();
            }

            // 공주님을 구하고 돕기 위한 메서드 실행
            sb.append(SaveAndHelpPrincess(map, H, W) ? "Yes" : "No").append('\n');
        }

        System.out.print(sb.toString());
    }
}
