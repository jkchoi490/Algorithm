package BFS;

import java.io.*;
import java.util.*;

//공주님을 구해라!
public class BAEKJOON17836 {
    static int N, M, T; //
    static int[][] map;
    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 검 없이 바로 공주에게 가는 경우
        int noSword = BFSNoSword();

        // 검을 얻고 나서 공주에게 가는 경우
        int withSword = BFSandSword();

        int result = Math.min(noSword, withSword);

        if (result <= T) {
            System.out.println(result);
        } else {
            System.out.println("Fail");
        }
    }

    // BFS: 검을 사용하지 않는 경우
    static int BFSNoSword() {
        boolean[][] visited = new boolean[N][M];
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{0, 0, 0}); // x, y, 시간
        visited[0][0] = true;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int x = cur[0];
            int y = cur[1];
            int t = cur[2];

            if (x == N - 1 && y == M - 1) {
                return t;
            }

            for (int d = 0; d < 4; d++) {
                int nx = x + dx[d];
                int ny = y + dy[d];

                if (nx < 0 || ny < 0 || nx >= N || ny >= M) continue;
                if (visited[nx][ny]) continue;
                if (map[nx][ny] == 1) continue;

                visited[nx][ny] = true;
                q.add(new int[]{nx, ny, t + 1});
            }
        }
        return Integer.MAX_VALUE;
    }

    // BFS로 검 위치까지 구하고, 그 이후는 직선 거리 계산
    static int BFSandSword() {
        boolean[][] visited = new boolean[N][M];
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{0, 0, 0}); // x, y, 시간
        visited[0][0] = true;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int x = cur[0];
            int y = cur[1];
            int t = cur[2];

            if (map[x][y] == 2) {
                int distToPrincess = (N - 1 - x) + (M - 1 - y);
                return t + distToPrincess;
            }

            for (int d = 0; d < 4; d++) {
                int nx = x + dx[d];
                int ny = y + dy[d];

                if (nx < 0 || ny < 0 || nx >= N || ny >= M) continue;
                if (visited[nx][ny]) continue;
                if (map[nx][ny] == 1) continue;

                visited[nx][ny] = true;
                q.add(new int[]{nx, ny, t + 1});
            }
        }
        return Integer.MAX_VALUE;
    }
}
