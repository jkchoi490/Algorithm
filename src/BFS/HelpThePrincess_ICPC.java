package BFS;

import java.util.*;
import java.io.*;

// ICPC - Help the Princess!
public class HelpThePrincess_ICPC {

    static int R, C;
    static char[][] grid;
    static int[] dr = {0, 0, 1, -1}; // 상하좌우
    static int[] dc = {1, -1, 0, 0};

    // 공주님을 구하고 돕는 메서드를 구현합니다
    static String SaveAndHelpPrincess(int princessR, int princessC, int r, int c, List<int[]> list) {
        int princessDist = PrincessBfs(princessR, princessC, r, c);

        if (princessDist == -1) return "No";

        if (list.isEmpty()) return "Yes";

        int dist = Integer.MAX_VALUE;
        for (int[] arr : list) {
            int d = PrincessBfs(arr[0], arr[1], r, c);
            if (d != -1) {
                dist = Math.min(dist, d);
            }
        }

        if (dist == Integer.MAX_VALUE) return "Yes";

        return princessDist < dist ? "Yes" : "No";
    }

    static int PrincessBfs(int ROW, int COL, int Row, int Col) {
        int[][] dist = new int[R][C];
        for (int[] row : dist) Arrays.fill(row, -1);  //생성형 AI를 사용한 초기 값

        Queue<int[]> queue = new LinkedList<>();
        dist[ROW][COL] = 0;
        queue.offer(new int[]{ROW, COL});

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int r = cur[0], c = cur[1];

            if (r == Row && c == Col) return dist[r][c];

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];
                if (nr < 0 || nr >= R || nc < 0 || nc >= C) continue;
                if (grid[nr][nc] == '#') continue;
                if (dist[nr][nc] != -1) continue;
                dist[nr][nc] = dist[r][c] + 1;
                queue.offer(new int[]{nr, nc});
            }
        }
        return -1;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;

            StringTokenizer st = new StringTokenizer(line);
            R = Integer.parseInt(st.nextToken());
            C = Integer.parseInt(st.nextToken());
            grid = new char[R][C];

            int princessR = -1, princessC = -1; //생성형 AI를 사용한 초기 값
            int r = -1, c = -1;
            List<int[]> list = new ArrayList<>();

            for (int i = 0; i < R; i++) {
                String row = br.readLine();
                for (int j = 0; j < C; j++) {
                    grid[i][j] = row.charAt(j);
                    if (grid[i][j] == '@')      { princessR = i; princessC = j; }
                    else if (grid[i][j] == '%') { r = i; c = j; }
                    else if (grid[i][j] == '$') { list.add(new int[]{i, j}); }
                }
            }

            // 공주님을 구하고 돕는 메서드를 실행합니다
            sb.append(SaveAndHelpPrincess(princessR, princessC, r, c, list)).append("\n");
        }

        System.out.print(sb.toString());
    }

}