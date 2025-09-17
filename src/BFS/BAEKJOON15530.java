package BFS;

import java.io.*;
import java.util.*;

//Help the Princess!
public class BAEKJOON15530 {
    static final int INF = 1_000_000_000;
    static int H, W;
    static char[][] grid;
    static int[][] princessDist;
    static int[][] soldierDist;
    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};

    static class Point {
        int x, y;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;

            StringTokenizer st = new StringTokenizer(line);
            H = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());

            grid = new char[H][W];
            soldierDist = new int[H][W];
            princessDist = new int[H][W];

            Point princess = null, exit = null;
            List<Point> soldiers = new ArrayList<>();

            for (int i = 0; i < H; i++) {
                String row = br.readLine();
                for (int j = 0; j < W; j++) {
                    grid[i][j] = row.charAt(j);
                    soldierDist[i][j] = INF;
                    princessDist[i][j] = INF;

                    if (grid[i][j] == '@') {
                        princess = new Point(i, j);
                    } else if (grid[i][j] == '%') {
                        exit = new Point(i, j);
                    } else if (grid[i][j] == '$') {
                        soldiers.add(new Point(i, j));
                    }
                }
            }

            bfsSoldiers(soldiers);

            boolean escaped = bfsPrincess(princess, exit);

            System.out.println(escaped ? "Yes" : "No");
        }
    }
    static boolean bfsPrincess(Point princess, Point exit) {
        Queue<Point> q = new LinkedList<>();
        princessDist[princess.x][princess.y] = 0;
        q.add(princess);

        while (!q.isEmpty()) {
            Point cur = q.poll();
            int t = princessDist[cur.x][cur.y];

            if (cur.x == exit.x && cur.y == exit.y && t < soldierDist[cur.x][cur.y]) {
                return true;
            }

            for (int d = 0; d < 4; d++) {
                int nx = cur.x + dx[d];
                int ny = cur.y + dy[d];
                if (!inBounds(nx, ny)) continue;
                if (grid[nx][ny] == '#') continue;

                int nt = t + 1;
                if (nt < princessDist[nx][ny] && nt < soldierDist[nx][ny]) {
                    princessDist[nx][ny] = nt;
                    q.add(new Point(nx, ny));
                }
            }
        }
        return false;
    }

    static void bfsSoldiers(List<Point> soldiers) {
        Queue<Point> q = new LinkedList<>();
        for (Point s : soldiers) {
            soldierDist[s.x][s.y] = 0;
            q.add(s);
        }

        while (!q.isEmpty()) {
            Point cur = q.poll();
            for (int d = 0; d < 4; d++) {
                int nx = cur.x + dx[d];
                int ny = cur.y + dy[d];
                if (!inBounds(nx, ny)) continue;
                if (grid[nx][ny] == '#') continue;
                if (soldierDist[nx][ny] > soldierDist[cur.x][cur.y] + 1) {
                    soldierDist[nx][ny] = soldierDist[cur.x][cur.y] + 1;
                    q.add(new Point(nx, ny));
                }
            }
        }
    }


    static boolean inBounds(int x, int y) {
        return x >= 0 && x < H && y >= 0 && y < W;
    }
}
