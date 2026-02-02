package BFS;

import java.io.*;
import java.util.*;

//TIOJ ICPC ONLINE JUDGE - Help the Princess!
public class HelpThePrincess_TIOJ {
    static int H, W;
    static char[][] map;
    static int pr, pc, er, ec;
    static int[][] distPrincess;
    static int[][] distSoldier;
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    // 공주님을 돕기 위한 BFS 구현
    static int helpPrincess() {
        Queue<int[]> q = new LinkedList<>();

        for(int i=0; i<H; i++) Arrays.fill(distPrincess[i], -1);

        q.add(new int[]{pr, pc});
        distPrincess[pr][pc] = 0;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int r = cur[0];
            int c = cur[1];

            if (r == er && c == ec) return distPrincess[r][c];

            for (int i = 0; i < 4; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];

                if (nr >= 0 && nr < H && nc >= 0 && nc < W && map[nr][nc] != '#' && distPrincess[nr][nc] == -1) {
                    distPrincess[nr][nc] = distPrincess[r][c] + 1;
                    q.add(new int[]{nr, nc});
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

        map = new char[H][W];

        distPrincess = new int[H][W];
        distSoldier = new int[H][W];

        Queue<int[]> soldierQueue = new LinkedList<>();

        for (int i = 0; i < H; i++) {
            Arrays.fill(distSoldier[i], Integer.MAX_VALUE);
        }

        for (int i = 0; i < H; i++) {
            String line = br.readLine();
            for (int j = 0; j < W; j++) {
                map[i][j] = line.charAt(j);
                if (map[i][j] == '@') {
                    pr = i; pc = j;
                } else if (map[i][j] == '$') {
                    soldierQueue.add(new int[]{i, j});
                    distSoldier[i][j] = 0;
                } else if (map[i][j] == '%') {
                    er = i; ec = j;
                }
            }
        }

        while (!soldierQueue.isEmpty()) {
            int[] cur = soldierQueue.poll();
            int r = cur[0];
            int c = cur[1];

            for (int i = 0; i < dr.length; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];

                if (nr >= 0 && nr < H && nc >= 0 && nc < W && map[nr][nc] != '#') {
                    if (distSoldier[nr][nc] == Integer.MAX_VALUE) {
                        distSoldier[nr][nc] = distSoldier[r][c] + 1;
                        soldierQueue.add(new int[]{nr, nc});
                    }
                }
            }
        }

        //공주님을 돕기 위한 메서드 실행
        int princessDist = helpPrincess();

        if (princessDist != -1 && princessDist < distSoldier[er][ec]) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }
}