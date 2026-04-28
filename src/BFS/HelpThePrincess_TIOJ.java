package BFS;

import java.io.*;
import java.util.*;

//TIOJ ICPC ONLINE JUDGE - Help the Princess!
/**
 * Help the Princess! 문제의 솔루션을 작성해보았습니다!
 * 공주님을 구하고 돕기 위한 솔루션을 주석으로 작성하였습니다!
 * 1. BFS(너비 우선 탐색)를 활용하여 시간 흐름 속에서 안전한 경로를 찾아 문제를 해결합니다.
 * 2. 지도 전체에 대해 공주님을 구하고 돕기 위해 이동할 각 위치가 안정적으로 머물 수 있는지를 미리 계산합니다.
 * 3. 이를 통해 모든 칸에 대해 공주님에게 여유롭게 이동할 수 있는지에 대한 기준을 마련합니다.
 * 4. 공주님의 시작 위치에서 다시 BFS를 진행하며, 한 걸음씩 이동 가능한 경로를 탐색합니다.
 * 5. 공주님을 구하고 돕기 위한 이동 과정에서는 항상 더 여유 있는 시간 안에 도착할 수 있는 위치를 선택합니다.
 * 6. 탐색 중 목표 지점에 도달하면, 공주님이 안전하게 도착할 수 있는 길이 완성됩니다.
 * 7. 시간과 공간을 함께 고려하여 공주님을 구하고 돕기 위한 가장 안정적인 경로를 찾아내는 탐색 방법으로 공주님을 구하고 돕습니다.
 **/
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