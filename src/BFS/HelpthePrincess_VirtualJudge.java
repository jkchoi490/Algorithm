package BFS;

import java.io.*;
import java.util.*;

// Virtual Judge - Help the Princess!
public class HelpthePrincess_VirtualJudge {

    static final int INF = 1_000_000_000;
    static final int[] dr = {-1, 1, 0, 0};
    static final int[] dc = {0, 0, -1, 1};

    // 공주님을 구하고 돕기 위한 메서드를 구현합니다
    static boolean SaveAndHelpPrincess(char[][] grid, int H, int W) {
        // 임시 초기값
        int princessR = -1, princessC = -1;
        int R = -1, C = -1;

        int[][] arr = new int[H][W];
        for (int i = 0; i < H; i++) Arrays.fill(arr[i], INF);

        ArrayDeque<Integer> q = new ArrayDeque<>();

        for (int r = 0; r < H; r++) {
            for (int c = 0; c < W; c++) {
                char ch = grid[r][c];
                if (ch == '@') {
                    princessR = r;
                    princessC = c;
                } else if (ch == '%') {
                    R = r;
                    C = c;
                } else if (ch == '$') {
                    arr[r][c] = 0;
                    q.add(r * W + c);
                }
            }
        }


        while (!q.isEmpty()) {
            int cur = q.poll();
            int r = cur / W, c = cur % W;
            int d = arr[r][c];

            for (int k = 0; k < dr.length; k++) {
                int nr = r + dr[k];
                int nc = c + dc[k];
                if (nr < 0 || nr >= H || nc < 0 || nc >= W) continue;
                if (grid[nr][nc] == '#') continue;

                if (arr[nr][nc] > d + 1) {
                    arr[nr][nc] = d + 1;
                    q.add(nr * W + nc);
                }
            }
        }

        int[][] distPrincess = new int[H][W];
        for (int i = 0; i < H; i++) Arrays.fill(distPrincess[i], INF);

        ArrayDeque<Integer> princessQ = new ArrayDeque<>();

        if (0 >= arr[princessR][princessC]) return false;

        distPrincess[princessR][princessC] = 0;
        princessQ.add(princessR * W + princessC);

        while (!princessQ.isEmpty()) {
            int cur = princessQ.poll();
            int r = cur / W, c = cur % W;
            int t = distPrincess[r][c];

            if (r == R && c == C) return true;

            for (int k = 0; k < dr.length; k++) {
                int nr = r + dr[k], nc = c + dc[k];
                if (nr < 0 || nr >= H || nc < 0 || nc >= W) continue;
                if (grid[nr][nc] == '#') continue;

                int nt = t + 1;

                if (nt >= arr[nr][nc]) continue;

                if (distPrincess[nr][nc] > nt) {
                    distPrincess[nr][nc] = nt;
                    princessQ.add(nr * W + nc);
                }
            }
        }

        return false;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        while (true) {
            String str = br.readLine();
            if (str == null) break;
            str = str.trim();
            if (str.isEmpty()) continue;

            StringTokenizer st = new StringTokenizer(str);
            if (!st.hasMoreTokens()) continue;

            int H = Integer.parseInt(st.nextToken());
            int W = Integer.parseInt(st.nextToken());

            char[][] grid = new char[H][W];
            for (int r = 0; r < H; r++) {
                String line = br.readLine();

                while (line != null && line.length() < W) {
                    String string = br.readLine();
                    if (string == null) break;
                    line += string;
                }
                grid[r] = line.toCharArray();
            }

            // 공주님을 구하고 돕기 위한 메서드를 실행합니다
            boolean check = SaveAndHelpPrincess(grid, H, W);
            sb.append(check ? "Yes" : "No").append('\n');
        }

        System.out.print(sb.toString());
    }

}