package BFS;

import java.io.*;
import java.util.*;

// AtCoder - Help the Princess!
public class HelpthePrincess {

    static final int INF = 1_000_000_000;

    static class Point {
        int r, c;
        Point(int r, int c) { this.r = r; this.c = c; }
    }

    static int H, W;
    static char[][] map;
    static int[][] soldierTime;
    static int[][] princessTime;

    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String line = br.readLine();
            if (line == null) return;

            StringTokenizer st = new StringTokenizer(line);
            H = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());

            map = new char[H][W];
            Point princess = null, hatch = null;

            List<Point> soldiers = new ArrayList<>();

            for (int i = 0; i < H; i++) {
                String row = br.readLine();
                for (int j = 0; j < W; j++) {
                    map[i][j] = row.charAt(j);
                    if (map[i][j] == '@') princess = new Point(i, j);
                    if (map[i][j] == '%') hatch = new Point(i, j);
                    if (map[i][j] == '$') soldiers.add(new Point(i, j));
                }
            }

            soldierTime = new int[H][W];
            princessTime = new int[H][W];

            for (int i = 0; i < H; i++) {
                Arrays.fill(soldierTime[i], INF);
                Arrays.fill(princessTime[i], INF);
            }

            bfsSoldiers(soldiers);

            boolean result = bfsPrincess(princess, hatch);

            System.out.println(result ? "Yes" : "No");
        }
    }

    static void bfsSoldiers(List<Point> soldiers) {
        Queue<Point> q = new ArrayDeque<>();

        for (Point s : soldiers) {
            soldierTime[s.r][s.c] = 0;
            q.add(s);
        }

        while (!q.isEmpty()) {
            Point cur = q.poll();
            int t = soldierTime[cur.r][cur.c];

            for (int d = 0; d < 4; d++) {
                int nr = cur.r + dr[d];
                int nc = cur.c + dc[d];

                if (nr < 0 || nr >= H || nc < 0 || nc >= W) continue;
                if (map[nr][nc] == '#') continue;

                if (soldierTime[nr][nc] > t + 1) {
                    soldierTime[nr][nc] = t + 1;
                    q.add(new Point(nr, nc));
                }
            }
        }
    }

    static boolean bfsPrincess(Point start, Point hatch) {
        Queue<Point> q = new ArrayDeque<>();
        princessTime[start.r][start.c] = 0;
        q.add(start);

        while (!q.isEmpty()) {
            Point cur = q.poll();
            int t = princessTime[cur.r][cur.c];

            if (cur.r == hatch.r && cur.c == hatch.c) {
                if (t < soldierTime[cur.r][cur.c]) return true;
            }

            for (int d = 0; d < 4; d++) {
                int nr = cur.r + dr[d];
                int nc = cur.c + dc[d];

                if (nr < 0 || nr >= H || nc < 0 || nc >= W) continue;
                if (map[nr][nc] == '#') continue;

                int nt = t + 1;

                if (nt >= soldierTime[nr][nc]) continue;

                if (princessTime[nr][nc] > nt) {
                    princessTime[nr][nc] = nt;
                    q.add(new Point(nr, nc));
                }
            }
        }

        return false;
    }
}
