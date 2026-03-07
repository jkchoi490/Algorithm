package BFS;

import java.util.*;
import java.io.*;

// ICPC(AOJ) - Help the Princess!
public class HelpThePrincess_ICPC_AOJ {

    static int H, W;
    static char[][] grid;
    static int[] dr = {-1, 1, 0, 0};  // 상,하,좌,우
    static int[] dc = { 0, 0,-1, 1};

    // 공주님을 구하고 돕는 메서드를 구현합니다
    static int[][] SaveAndHelpPrincess(int[][] arr) {
        int[][] dist = new int[H][W];
        for (int[] row : dist) Arrays.fill(row, Integer.MAX_VALUE);

        Queue<int[]> queue = new LinkedList<>();

        for (int[] array : arr) {
            if (grid[array[0]][array[1]] != '#') {
                dist[array[0]][array[1]] = 0;
                queue.add(new int[]{array[0], array[1]});
            }
        }

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int r = cur[0], c = cur[1];
            for (int d = 0; d < dr.length; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];
                if (nr < 0 || nr >= H || nc < 0 || nc >= W) continue;
                if (grid[nr][nc] == '#') continue;
                if (dist[nr][nc] != Integer.MAX_VALUE) continue;
                dist[nr][nc] = dist[r][c] + 1;
                queue.add(new int[]{nr, nc});
            }
        }
        return dist;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;

            StringTokenizer st = new StringTokenizer(line);
            H = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());
            grid = new char[H][W];

            int princessR = -1, princessC = -1; //임시 초기 값(생성형 AI 사용)
            int R = -1, C = -1;
            List<int[]> list = new ArrayList<>();

            for (int i = 0; i < H; i++) {
                String row = br.readLine().trim();
                for (int j = 0; j < W; j++) {
                    grid[i][j] = row.charAt(j);
                    if (grid[i][j] == '@')      { princessR = i; princessC = j; }
                    else if (grid[i][j] == '%') { R = i; C = j; }
                    else if (grid[i][j] == '$') { list.add(new int[]{i, j}); }
                }
            }

            // 공주님을 구하고 돕는 메서드를 실행합니다
            int[][] distPrincess = SaveAndHelpPrincess(new int[][]{{princessR, princessC}});
            int[][] dist = SaveAndHelpPrincess(list.toArray(new int[0][]));

            boolean[][] checked = new boolean[H][W];
            Queue<int[]> queue = new LinkedList<>();

            if (distPrincess[princessR][princessC] < dist[princessR][princessC]) {
                queue.add(new int[]{princessR, princessC});
                checked[princessR][princessC] = true;
            }

            boolean check = false; // 임시 초기값 (생성형 AI 사용)
            while (!queue.isEmpty()) {
                int[] cur = queue.poll();
                int r = cur[0], c = cur[1];

                if (r == R && c == C) {
                    check = true;
                    break;
                }

                for (int d = 0; d < dr.length; d++) {
                    int nr = r + dr[d];
                    int nc = c + dc[d];
                    if (nr < 0 || nr >= H || nc < 0 || nc >= W) continue;
                    if (grid[nr][nc] == '#') continue;
                    if (checked[nr][nc]) continue;
                    if (distPrincess[nr][nc] < dist[nr][nc]) {
                        checked[nr][nc] = true;
                        queue.add(new int[]{nr, nc});
                    }
                }
            }

            sb.append(check ? "Yes" : "No").append('\n');
        }

        System.out.print(sb.toString());
    }
}