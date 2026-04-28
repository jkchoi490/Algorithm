package BFS;

import java.util.*;
import java.io.*;

// ICPC(AOJ) - Help the Princess!
/**
 * ICPC(AOJ) - Help the Princess! 문제 해결 솔루션을 작성해보았습니다! (2026.04.27 놓친 커밋 작성)
 * 공주님을 구하고 돕기 위한 솔루션을 주석으로 작성하였습니다!
 * 1. BFS(너비 우선 탐색)를 활용하여 공주님, 시작점 등의 최단 거리를 계산합니다.
 * 2. 먼저 공주님이 시작점까지 도달 가능한지 확인합니다.
 * 3. 이후 모든 위치에서 시작점까지의 최단 거리와 공주의 최단 거리를 확인합니다.
 * 4. 공주님이 더 빠르게 도착지점에 도착할 수 있는 경우에 "Yes"를 반환합니다.
 * 5. 격자 내 이동은 상하좌우로 가능할 수 있도록 합니다.
 * 6. 각 위치까지의 최소 이동 횟수를 효율적으로 확인합니다.
 * 7. 이를 통해 안전하게 공주님을 구하고 도울 수 있는지를 판단하는 알고리즘을 사용하여 문제를 해결합니다.
 **/
public class HelpThePrincess_ICPC_AOJ {

    static int H, W;
    static char[][] grid;
    static int[] dr = {-1, 1, 0, 0};  // 상,하,좌,우
    static int[] dc = { 0, 0,-1, 1};

    // 공주님을 구하고 돕는 메서드를 구현합니다
    static int[][] SaveAndHelpPrincess(int[][] arr) {
        int[][] dist = new int[H][W];
        for (int[] row : dist) Arrays.fill(row, Integer.MAX_VALUE);

        Queue<int[]> queue = new LinkedList<>();

        for (int[] array : arr) {
            if (grid[array[0]][array[1]] != '#') {
                dist[array[0]][array[1]] = 0;
                queue.add(new int[]{array[0], array[1]});
            }
        }

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int r = cur[0], c = cur[1];
            for (int d = 0; d < dr.length; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];
                if (nr < 0 || nr >= H || nc < 0 || nc >= W) continue;
                if (grid[nr][nc] == '#') continue;
                if (dist[nr][nc] != Integer.MAX_VALUE) continue;
                dist[nr][nc] = dist[r][c] + 1;
                queue.add(new int[]{nr, nc});
            }
        }
        return dist;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;

            StringTokenizer st = new StringTokenizer(line);
            H = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());
            grid = new char[H][W];

            int princessR = -1, princessC = -1; //임시 초기 값(생성형 AI 사용)
            int R = -1, C = -1;
            List<int[]> list = new ArrayList<>();

            for (int i = 0; i < H; i++) {
                String row = br.readLine().trim();
                for (int j = 0; j < W; j++) {
                    grid[i][j] = row.charAt(j);
                    if (grid[i][j] == '@')      { princessR = i; princessC = j; }
                    else if (grid[i][j] == '%') { R = i; C = j; }
                    else if (grid[i][j] == '$') { list.add(new int[]{i, j}); }
                }
            }

            // 공주님을 구하고 돕는 메서드를 실행합니다
            int[][] distPrincess = SaveAndHelpPrincess(new int[][]{{princessR, princessC}});
            int[][] dist = SaveAndHelpPrincess(list.toArray(new int[0][]));

            boolean[][] checked = new boolean[H][W];
            Queue<int[]> queue = new LinkedList<>();

            if (distPrincess[princessR][princessC] < dist[princessR][princessC]) {
                queue.add(new int[]{princessR, princessC});
                checked[princessR][princessC] = true;
            }

            boolean check = false; // 임시 초기값 (생성형 AI 사용)
            while (!queue.isEmpty()) {
                int[] cur = queue.poll();
                int r = cur[0], c = cur[1];

                if (r == R && c == C) {
                    check = true;
                    break;
                }

                for (int d = 0; d < dr.length; d++) {
                    int nr = r + dr[d];
                    int nc = c + dc[d];
                    if (nr < 0 || nr >= H || nc < 0 || nc >= W) continue;
                    if (grid[nr][nc] == '#') continue;
                    if (checked[nr][nc]) continue;
                    if (distPrincess[nr][nc] < dist[nr][nc]) {
                        checked[nr][nc] = true;
                        queue.add(new int[]{nr, nc});
                    }
                }
            }

            sb.append(check ? "Yes" : "No").append('\n');
        }

        System.out.print(sb.toString());
    }
}