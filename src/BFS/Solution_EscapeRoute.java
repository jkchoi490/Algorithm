package BFS;

import java.io.*;
import java.util.*;

// Escape Route
public class Solution_EscapeRoute {

    static final int[] dx = { -1, 1, 0, 0 };
    static final int[] dy = { 0, 0, -1, 1 };
    static final char[] arrow = { '^', 'v', '<', '>' };

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int H = Integer.parseInt(st.nextToken());
        int W = Integer.parseInt(st.nextToken());

        char[][] grid = new char[H][W];
        for (int i = 0; i < H; i++) {
            grid[i] = br.readLine().toCharArray();
        }

        int[][] dist = new int[H][W];
        for (int[] row : dist) Arrays.fill(row, -1);

        // parent direction (from where BFS came to this cell)
        int[][] fromDir = new int[H][W];

        ArrayDeque<int[]> q = new ArrayDeque<>();

        // Multi-source BFS: put all exits to queue
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (grid[i][j] == 'E') {
                    q.add(new int[]{i, j});
                    dist[i][j] = 0;  // distance from exit = 0
                }
            }
        }

        // Perform BFS
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int x = cur[0], y = cur[1];

            for (int d = 0; d < 4; d++) {
                int nx = x + dx[d];
                int ny = y + dy[d];

                if (nx < 0 || nx >= H || ny < 0 || ny >= W) continue;
                if (grid[nx][ny] == '#') continue;
                if (dist[nx][ny] != -1) continue;

                dist[nx][ny] = dist[x][y] + 1;

                // BFS 역추적: nx,ny에서 x,y로 가면 E로 가까워진다
                fromDir[nx][ny] = d; // move (nx,ny) → (x,y)
                q.add(new int[]{nx, ny});
            }
        }

        // Build output
        char[][] out = new char[H][W];

        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (grid[i][j] == '#' || grid[i][j] == 'E') {
                    out[i][j] = grid[i][j];
                } else {
                    int d = fromDir[i][j];
                    int rev = (d == 0 ? 1 : d == 1 ? 0 : d == 2 ? 3 : 2);

                    out[i][j] = arrow[rev];
                }
            }
        }

        // Print result
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < H; i++) {
            sb.append(out[i]).append('\n');
        }
        System.out.print(sb);
    }
}
