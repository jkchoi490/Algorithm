package BFS;

import java.util.*;
import java.io.*;

// Luogu Judge - Help the Princess!
public class HelpthePrincess_Luogu {
    static int H, W;
    static char[][] map;
    static int[][] soldierDist;
    static int[][] princessDist;
    static int[] dr = {1, -1, 0, 0};
    static int[] dc = {0, 0, 1, -1};

    //공주님을 구하고 돕기 위한 메서드
    static boolean SaveAndHelpPrincess(int r, int c) {
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{r, c});
        princessDist[r][c] = 0;

        if (princessDist[r][c] >= soldierDist[r][c]) return false;

        while (!q.isEmpty()) {
            int[] curr = q.poll();
            int cur_r = curr[0];
            int cur_c = curr[1];

            if (map[cur_r][cur_c] == '%') return true;

            for (int i = 0; i < 4; i++) {
                int nr = cur_r + dr[i];
                int nc = cur_c + dc[i];

                if (nr >= 0 && nr < H && nc >= 0 && nc < W && map[nr][nc] != '#' && princessDist[nr][nc] == -1) {
                    int time = princessDist[cur_r][cur_c] + 1;

                    if (time < soldierDist[nr][nc]) {
                        princessDist[nr][nc] = time;
                        q.add(new int[]{nr, nc});
                    }
                }
            }
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        H = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());

        map = new char[H][W];
        princessDist = new int[H][W];
        soldierDist = new int[H][W];


        Queue<int[]> Queue = new LinkedList<>();
        int startR = -1, startC = -1;

        for (int i = 0; i < H; i++) {
            String line = br.readLine();
            for (int j = 0; j < W; j++) {
                map[i][j] = line.charAt(j);
                princessDist[i][j] = -1;
                soldierDist[i][j] = Integer.MAX_VALUE;

                if (map[i][j] == '$') {
                    Queue.add(new int[]{i, j});
                    soldierDist[i][j] = 0;
                } else if (map[i][j] == '@') {
                    startR = i;
                    startC = j;
                }
            }
        }

        while (!Queue.isEmpty()) {
            int[] curr = Queue.poll();
            for (int i = 0; i < dr.length; i++) {
                int nr = curr[0] + dr[i];
                int nc = curr[1] + dc[i];
                if (nr >= 0 && nr < H && nc >= 0 && nc < W && map[nr][nc] != '#') {
                    if (soldierDist[nr][nc] == Integer.MAX_VALUE) {
                        soldierDist[nr][nc] = soldierDist[curr[0]][curr[1]] + 1;
                        Queue.add(new int[]{nr, nc});
                    }
                }
            }
        }

        // 공주님을 구하고 돕기 위해 메서드 실행
        if (SaveAndHelpPrincess(startR, startC)) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }

}