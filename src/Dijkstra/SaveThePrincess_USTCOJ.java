package Dijkstra;

import java.io.*;
import java.util.*;

// USTC OJ - Save the Princess
public class SaveThePrincess_USTCOJ {

    static final int INF = 1_000_000_000;

    static class Node {
        int d, r, c, mask;
        Node(int d, int r, int c, int mask) {
            this.d = d; this.r = r; this.c = c; this.mask = mask;
        }
    }

    static int N, M;
    static char[][] grid;
    static int sr, sc;
    static int pr, pc;


    static final int[] dr = {1, -1, 0, 0};
    static final int[] dc = {0, 0, 1, -1};

    // 공주님을 구하고 돕기 위한 메서드 구현
    static int SaveAndHelpPrincess() {

        int[][][] dist = new int[4][M][N];
        for (int num = 0; num < 4; num++) {
            for (int i = 0; i < M; i++) Arrays.fill(dist[num][i], INF);
        }

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.d));

        dist[0][sr][sc] = 0;
        pq.add(new Node(0, sr, sc, 0));

        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            int d = cur.d, r = cur.r, c = cur.c, mask = cur.mask;

            if (d != dist[mask][r][c]) continue;
            if (r == pr && c == pc) return d;

            for (int k = 0; k < 4; k++) {
                int nr = r + dr[k];
                int nc = c + dc[k];
                if (nr < 0 || nr >= M || nc < 0 || nc >= N) continue;

                char ch = grid[nr][nc];
                int nd = d;
                int nmask = mask;

                if (ch == 'E' || ch == 'P' || ch == 'Y') {
                    nd += 1;
                } else if (ch == 'B') {
                    nd += 1;
                    nmask |= 2;
                } else if (ch == 'R') {
                    if ((mask & 2) == 0) continue;
                    nd += 1;
                } else if (ch == 'W') {
                    nd += 1;
                    nmask |= 1;
                } else if (ch == 'L') {
                    nd += 2;
                } else if (ch == 'S') {
                    if ((mask & 1) == 0) continue;
                    nd += 2;
                } else {
                    continue;
                }

                if (nd < dist[nmask][nr][nc]) {
                    dist[nmask][nr][nc] = nd;
                    pq.add(new Node(nd, nr, nc, nmask));
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        while (true) {
            String line = br.readLine();
            if (line == null) break;
            line = line.trim();
            if (line.isEmpty()) continue;

            StringTokenizer st = new StringTokenizer(line);
            M = Integer.parseInt(st.nextToken());
            N = Integer.parseInt(st.nextToken());
            if (M == 0 && N == 0) break;

            grid = new char[M][N];
            pr = -1; pc = -1;
            sr = -1; sc = -1;

            for (int i = 0; i < M; i++) {
                String row = br.readLine();
                while (row != null && row.length() < N) {
                    String str = br.readLine();
                    if (str == null) break;
                    row = row + str;
                }

                for (int j = 0; j < N; j++) {
                    char ch = row.charAt(j);
                    grid[i][j] = ch;
                    if(ch == 'P') {
                        pr = i; pc = j;
                    }
                    else  if (ch == 'Y') {
                        sr = i; sc = j;
                        grid[i][j] = 'E';
                    }
                }
            }

            //공주님을 구하는 메서드 실행
            sb.append(SaveAndHelpPrincess()).append('\n');
        }

        System.out.print(sb.toString());
    }
}
