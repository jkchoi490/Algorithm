package BFS;
import java.util.*;
import java.io.*;

// UESTC(AtCoder) - Help the Princess!
public class HelpThePrincess_Atcoder_UESTC {
    static int H, W;
    static char[][] grid;
    static int[] dr = {0, 0, 1, -1};
    static int[] dc = {1, -1, 0, 0};

    // 공주님을 구하고 돕는 메서드를 구현합니다
    static String saveAndHelpPrincess(int princessR, int princessC, int er, int ec, List<int[]> list) {

        if (list.isEmpty()) return "Yes"; // 문제에서 주어진 값 등을 사용

        int princessDist = BFS(princessR, princessC, er, ec);

        if (princessDist == -1) return "No";

        int dist = Integer.MAX_VALUE;
        for (int[] s : list) {
            int d = BFS(s[0], s[1], er, ec);
            if (d != -1) dist = Math.min(dist, d);
        }

        return princessDist < dist ? "Yes" : "No";
    }

    static int BFS(int R, int C, int tr, int tc) {
        int[][] dist = new int[H][W];
        for (int[] row : dist) Arrays.fill(row, -1);
        Queue<int[]> q = new ArrayDeque<>();
        dist[R][C] = 0;
        q.offer(new int[]{R, C});

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int r = cur[0], c = cur[1];
            if (r == tr && c == tc) return dist[r][c];
            for (int d = 0; d < dr.length; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];
                if (nr >= 0 && nr < H && nc >= 0 && nc < W
                        && grid[nr][nc] != '#' && dist[nr][nc] == -1) {
                    dist[nr][nc] = dist[r][c] + 1;
                    q.offer(new int[]{nr, nc});
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        H = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());
        grid = new char[H][W];

        int princessR = -1, princessC = -1;
        int er = -1, ec = -1;
        List<int[]> list = new ArrayList<>();

        for (int i = 0; i < H; i++) {
            String line = br.readLine();
            for (int j = 0; j < W; j++) {
                grid[i][j] = line.charAt(j);
                if (grid[i][j] == '@')      { princessR = i; princessC = j; }
                else if (grid[i][j] == '%') { er = i; ec = j; }
                else if (grid[i][j] == '$') { list.add(new int[]{i, j}); }
            }
        }

        // 공주님을 구하고 돕는 메서드를 실행합니다
        System.out.println(saveAndHelpPrincess(princessR, princessC, er, ec, list));
    }

}