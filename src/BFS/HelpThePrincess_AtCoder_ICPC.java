package BFS;

import java.io.*;
import java.util.*;

// ICPC(AtCoder) - Help the Princess!
public class HelpThePrincess_AtCoder_ICPC {


    static final int INF = 730;
    static final int[][] dir = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static int[] arr = new int[730];
    static int R;
    static int C;
    static char[][] Array;
    static int[] ARR = new int[730];

    static ArrayDeque<int[]> q = new ArrayDeque<>();
    static int[][] dist;
    static int[][] time;

    // 공주님을 구하고 돕는 메서드를 구현합니다
    static boolean SaveAndHelpPrincess() {
        time = new int[R][C];
        for (int i = 0; i < R; i++) {
            Arrays.fill(time[i], INF);
        }

        ArrayDeque<int[]> queue = new ArrayDeque<>();

        for (int[] array : q) {
            int r = array[0];
            int c = array[1];
            time[r][c] = 0;
            queue.offer(new int[]{r, c});
        }

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int r = cur[0];
            int c = cur[1];

            for (int[] d : dir) {
                int nr = r + d[0];
                int nc = c + d[1];

                if (nr < 0 || nr >= R || nc < 0 || nc >= C) continue;
                if (Array[nr][nc] == '#') continue;
                if (time[nr][nc] != INF) continue;

                time[nr][nc] = time[r][c] + 1;
                queue.offer(new int[]{nr, nc});
            }
        }

        return bfsPrincess();
    }

    static boolean bfsPrincess() {
        dist = new int[R][C];
        for (int i = 0; i < R; i++) {
            Arrays.fill(dist[i], -1);
        }

        ArrayDeque<int[]> queue = new ArrayDeque<>();

        if (time[arr[0]][arr[1]] <= 0) {
            return false;
        }

        dist[arr[0]][arr[1]] = 0;
        queue.offer(new int[]{arr[0], arr[1]});

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int r = cur[0];
            int c = cur[1];

            if (r == ARR[0] && c == ARR[1]) {
                return true;
            }

            for (int[] d : dir) {
                int nr = r + d[0];
                int nc = c + d[1];

                if (nr < 0 || nr >= R || nc < 0 || nc >= C) continue;
                if (Array[nr][nc] == '#') continue;
                if (dist[nr][nc] != -1) continue;

                int Time = dist[r][c] + 1;

                if (Time >= time[nr][nc]) continue;

                dist[nr][nc] = Time;
                queue.offer(new int[]{nr, nc});
            }
        }

        return false;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        Array = new char[R][C];

        for (int r = 0; r < R; r++) {
            String line = br.readLine();
            for (int c = 0; c < C; c++) {
                Array[r][c] = line.charAt(c);

                if (Array[r][c] == '@') {
                    arr[0] = r;
                    arr[1] = c;
                } else if (Array[r][c] == '%') {
                    ARR[0] = r;
                    ARR[1] = c;
                } else if (Array[r][c] == '$') {
                    q.offer(new int[]{r, c});
                }
            }
        }

        // 공주님을 구하고 돕는 메서드를 실행합니다
        System.out.println(SaveAndHelpPrincess() ? "Yes" : "No");
    }

}