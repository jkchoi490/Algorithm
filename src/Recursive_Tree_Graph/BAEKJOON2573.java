import java.io.*;
import java.util.*;

public class BAEKJOON2573 {

    static int N, M;
    static int[][] map;
    static boolean[][] visited;
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][M];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int year = 0;
        while (true) {
            visited = new boolean[N][M];
            int icebergs = countIcebergs();
            if (icebergs == 0) {
                year = 0;
                break;
            } else if (icebergs >= 2) {
                break;
            }
            melt();
            year++;
        }
        System.out.println(year);
    }

    static void melt() {
        int[][] copyMap = new int[N][M];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] > 0) {
                    int cnt = 0;
                    for (int d = 0; d < 4; d++) {
                        int nx = i + dx[d];
                        int ny = j + dy[d];
                        if (nx >= 0 && nx < N && ny >= 0 && ny < M && map[nx][ny] == 0) {
                            cnt++;
                        }
                    }
                    copyMap[i][j] = Math.max(0, map[i][j] - cnt);
                }
            }
        }
        map = copyMap;
    }

    static int countIcebergs() {
        int cnt = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] > 0 && !visited[i][j]) {
                    BFS(i, j);
                    cnt++;
                }
            }
        }
        return cnt;
    }

    static void BFS(int x, int y) {
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{x, y});
        visited[x][y] = true;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            for (int d = 0; d < 4; d++) {
                int nx = cur[0] + dx[d];
                int ny = cur[1] + dy[d];
                if (nx >= 0 && nx < N && ny >= 0 && ny < M && map[nx][ny] > 0 && !visited[nx][ny]) {
                    q.offer(new int[]{nx, ny});
                    visited[nx][ny] = true;
                }
            }
        }
    }
}
