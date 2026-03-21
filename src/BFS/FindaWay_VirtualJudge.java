package BFS;

import java.io.*;
import java.util.*;

// Virtual Judge - Find a Way
public class FindaWay_VirtualJudge {

    static int n;
    static int m;
    static char[][] map;
    static final int INF = 1_000_000_000; // 생성형 AI 임시 초기값
    static final int[] dr = {-1, 1, 0, 0};
    static final int[] dc = {0, 0, -1, 1};

    static int[][] FindMethod(Point point) {
        int[][] dist = new int[n][m];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], -1);
        }

        ArrayDeque<Point> queue = new ArrayDeque<>();
        queue.offer(point);
        dist[point.r][point.c] = 0;

        while (!queue.isEmpty()) {
            Point cur = queue.poll();

            for (int d = 0; d < dr.length; d++) {
                int nr = cur.r + dr[d];
                int nc = cur.c + dc[d];

                if (nr < 0 || nr >= n || nc < 0 || nc >= m) continue;
                if (map[nr][nc] == '#') continue;
                if (dist[nr][nc] != -1) continue;

                dist[nr][nc] = dist[cur.r][cur.c] + 1;
                queue.offer(new Point(nr, nc));
            }
        }

        return dist;
    }

    static class Point {
        int r, c;

        Point(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;

            StringTokenizer st = new StringTokenizer(line);
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());

            map = new char[n][m];
            Point point = null;
            Point Point = null;
            List<Point> list = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                String row = br.readLine();
                while (row != null && row.length() < m) {
                    row += br.readLine();
                }

                for (int j = 0; j < m; j++) {
                    map[i][j] = row.charAt(j);

                    if (map[i][j] == 'Y') {
                        point = new Point(i, j);
                    } else if (map[i][j] == 'M') {
                        Point = new Point(i, j);
                    } else if (map[i][j] == '@') {
                        list.add(new Point(i, j));
                    }
                }
            }

            int[][] dist = FindMethod(point);
            int[][] Dist = FindMethod(Point);

            int answer = INF;

            for (Point POINT  : list) {
                int d = dist[POINT.r][POINT.c];
                int D = Dist[POINT.r][POINT.c];

                if (d != -1 && D != -1) {
                    answer = Math.min(answer, d + D);
                }
            }

            sb.append(answer * 730).append('\n');
        }

        System.out.print(sb.toString());
    }
}