package BFS;

import java.io.*;
import java.util.*;

//CodeNet - Help the Princess!
public class HelpthePrincess_CodeNet {
    static final int INF = 1_000_000_000;
    static final int[] dr = {1, -1, 0, 0, 0};
    static final int[] dc = {0, 0, 1, -1, 0};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        while (true) {
            String line = br.readLine();
            if (line == null) break;
            line = line.trim();
            if (line.isEmpty()) continue;

            StringTokenizer st = new StringTokenizer(line);
            int H, W;

            if (st.countTokens() >= 2) {
                H = Integer.parseInt(st.nextToken());
                W = Integer.parseInt(st.nextToken());
            } else {
                H = Integer.parseInt(st.nextToken());
                st = new StringTokenizer(nonEmptyLine(br));
                W = Integer.parseInt(st.nextToken());
            }

            char[][] g = new char[H][W];
            int pr = -1, pc = -1, er = -1, ec = -1;
            ArrayDeque<int[]> soldiers = new ArrayDeque<>();

            for (int i = 0; i < H; i++) {
                String row = br.readLine();
                while (row != null && row.length() < W) {
                    row = br.readLine();
                }
                if (row == null) return;
                g[i] = row.toCharArray();
                for (int j = 0; j < W; j++) {
                    char c = g[i][j];
                    if (c == '@') {
                        pr = i;
                        pc = j;
                    }
                    else if (c == '%') {
                        er = i;
                        ec = j;
                    }
                    else if (c == '$') {
                        soldiers.add(new int[]{i, j});
                    }
                }
            }

            int[][] distS = bfsSoldiers(g, H, W, soldiers);
            boolean ok = bfsPrincess(g, H, W, pr, pc, er, ec, distS);

            sb.append(ok ? "Yes" : "No").append('\n');
        }

        System.out.print(sb.toString());
    }


    // 공주 BFS
    static boolean bfsPrincess(char[][] g, int H, int W,
                               int r, int c, int er, int ec,
                               int[][] distS) {

        if (distS[r][c] <= 0) return false;

        int[][] distP = new int[H][W];
        for (int i = 0; i < H; i++) {
            Arrays.fill(distP[i], INF);
        }

        ArrayDeque<int[]> q = new ArrayDeque<>();
        distP[r][c] = 0;
        q.add(new int[]{r, c});

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int cur_r = cur[0];
            int cur_c = cur[1];
            int t = distP[cur_r][cur_c];

            if (cur_r == er && cur_c == ec) return true;

            for (int k = 0; k < dr.length; k++) {
                int nr = cur_r + dr[k];
                int nc = cur_c + dc[k];
                int nt = t + 1;

                if (nr < 0 || nr >= H || nc < 0 || nc >= W) continue;
                if (!passable(g[nr][nc])) continue;

                if (distS[nr][nc] <= nt) continue;

                if (distP[nr][nc] > nt) {
                    distP[nr][nc] = nt;
                    q.add(new int[]{nr, nc});
                }
            }
        }
        return false;
    }

    static int[][] bfsSoldiers(char[][] g, int H, int W, ArrayDeque<int[]> sources) {
        int[][] dist = new int[H][W];

        for (int i = 0; i < H; i++) {
            Arrays.fill(dist[i], INF);
        }

        ArrayDeque<int[]> q = new ArrayDeque<>();
        for (int[] s : sources) {
            dist[s[0]][s[1]] = 0;
            q.add(new int[]{s[0], s[1]});
        }

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int r = cur[0], c = cur[1];
            int d = dist[r][c];

            for (int k = 0; k < dr.length-1; k++) {
                int nr = r + dr[k];
                int nc = c + dc[k];

                if (nr < 0 || nr >= H || nc < 0 || nc >= W) continue;
                if (!passable(g[nr][nc])) continue;
                if (dist[nr][nc] > d + 1) {
                    dist[nr][nc] = d + 1;
                    q.add(new int[]{nr, nc});
                }
            }
        }
        return dist;
    }

    static String nonEmptyLine(BufferedReader br) throws IOException {
        String s;
        while ((s = br.readLine()) != null) {
            s = s.trim();
            if (!s.isEmpty()) return s;
        }
        return null;
    }

    static boolean passable(char c) {
        return c != '#';
    }
}
