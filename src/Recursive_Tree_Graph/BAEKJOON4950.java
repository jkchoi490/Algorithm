package Recursive_Tree_Graph;

import java.io.*;
import java.util.*;

//BAEKJOON4950
public class BAEKJOON4950 {

    static int H, W, target;
    static int[][] board;
    static int result;

    static final int[] dy = {1, -1, 0, 0};
    static final int[] dx = {0, 0, 1, -1};

    static int floodFill(int[][] b, int color) {
        boolean[][] vis = new boolean[H][W];
        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[]{0, 0});
        vis[0][0] = true;
        int cnt = 1;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int y = cur[0], x = cur[1];
            for (int d = 0; d < 4; d++) {
                int ny = y + dy[d];
                int nx = x + dx[d];
                if (ny < 0 || ny >= H || nx < 0 || nx >= W) continue;
                if (!vis[ny][nx] && b[ny][nx] == color) {
                    vis[ny][nx] = true;
                    q.add(new int[]{ny, nx});
                    cnt++;
                }
            }
        }
        return cnt;
    }

    static void applyColor(int[][] b, int color) {
        int Color = b[0][0];
        if (Color == color) return;

        boolean[][] vis = new boolean[H][W];
        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[]{0, 0});
        vis[0][0] = true;
        b[0][0] = color;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int y = cur[0], x = cur[1];
            for (int d = 0; d < 4; d++) {
                int ny = y + dy[d];
                int nx = x + dx[d];
                if (ny < 0 || ny >= H || nx < 0 || nx >= W) continue;
                if (!vis[ny][nx] && b[ny][nx] == Color) {
                    vis[ny][nx] = true;
                    b[ny][nx] = color;
                    q.add(new int[]{ny, nx});
                }
            }
        }


        boolean check;
        do {
            check = false;
            boolean[][] vis2 = new boolean[H][W];
            Queue<int[]> q2 = new ArrayDeque<>();
            q2.add(new int[]{0, 0});
            vis2[0][0] = true;

            while (!q2.isEmpty()) {
                int[] cur = q2.poll();
                int y = cur[0], x = cur[1];
                for (int d = 0; d < 4; d++) {
                    int ny = y + dy[d];
                    int nx = x + dx[d];
                    if (ny < 0 || ny >= H || nx < 0 || nx >= W) continue;
                    if (!vis2[ny][nx]) {
                        if (b[ny][nx] == color) {
                            vis2[ny][nx] = true;
                            q2.add(new int[]{ny, nx});
                        } else if (b[ny][nx] == color) {
                            // Already handled
                        }
                    }
                }
            }

        } while (check);
    }

    static void dfs(int depth, int[][] curBoard) {
        int colorCnt = 6; //색상 수
        int currentColor = curBoard[0][0];


        if (depth == 5) { //depth
            if (curBoard[0][0] == target) {
                int size = floodFill(curBoard, target);
                result = Math.max(result, size);
            }
            return;
        }


        for (int c = 1; c <= colorCnt; c++) {
            if (c == currentColor) continue;

            int[][] next = new int[H][W];
            for (int i = 0; i < H; i++)
                System.arraycopy(curBoard[i], 0, next[i], 0, W);

            applyColor(next, c);
            dfs(depth + 1, next);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        while (true) {
            st = new StringTokenizer(br.readLine());
            H = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());
            target = Integer.parseInt(st.nextToken());
            if (H == 0 && W == 0 && target == 0) break;

            board = new int[H][W];
            for (int i = 0; i < H; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < W; j++) {
                    board[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            result = 0;
            dfs(0, board);
            System.out.println(result);
        }
    }
}
