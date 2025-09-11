package BFS;

import java.io.*;
import java.util.*;

//치즈
public class BAEKJOON2636 {
    static int n, m; //n: 행(세로), m: 열(가로)
    static int[][] board; //치즈 보드
    static boolean[][] visited; // BFS를 위한 배열
    static int[] dx = {1, -1, 0, 0}; //x(행) 방향 벡터
    static int[] dy = {0, 0, 1, -1}; //y(행) 방향 벡터

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        //입력받기
        board = new int[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int time = 0;
        int cnt = 0;

        while (true) {
            int cheese = solution();
            if (cheese == 0) {
                break;
            }
            cnt = cheese;

            BFS();
            time++;
        }

        System.out.println(time);
        System.out.println(cnt);
    }

    //solution 함수
    static int solution() {
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] == 1) cnt++;
            }
        }
        return cnt;
    }

    // BFS
    static void BFS() {
        visited = new boolean[n][m];
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{0, 0});
        visited[0][0] = true;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int x = cur[0];
            int y = cur[1];

            for (int dir = 0; dir < 4; dir++) {
                int nx = x + dx[dir];
                int ny = y + dy[dir];

                if (nx < 0 || ny < 0 || nx >= n || ny >= m) continue;
                if (visited[nx][ny]) continue;

                visited[nx][ny] = true;
                if (board[nx][ny] == 0) {
                    q.add(new int[]{nx, ny});
                } else if (board[nx][ny] == 1) {
                    board[nx][ny] = 0;
                }
            }
        }
    }
}
