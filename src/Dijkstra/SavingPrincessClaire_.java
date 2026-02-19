package Dijkstra;

import java.io.*;
import java.util.*;

// HDU OJ - Saving Princess claire_
public class SavingPrincessClaire_ {

    // 공주님을 구하고 돕는 메서드를 구현합니다
    static int SaveAndHelpPrincess(int r, int c, int tr, int tc) {

        dist = new int[R][C];

        for (int i = 0; i < R; i++)
            Arrays.fill(dist[i], Integer.MAX_VALUE);

        PriorityQueue<Node> pq = new PriorityQueue<>();

        dist[r][c] = 0;
        pq.add(new Node(r, c, 0));

        boolean check = false;

        while (!pq.isEmpty()) {

            Node cur = pq.poll();

            if (cur.num > dist[cur.r][cur.c])
                continue;

            if (cur.r == tr && cur.c == tc)
                return cur.num;

            if (grid[cur.r][cur.c] == 'P' && !check) {

                check = true;

                for (int[] arr : list) {

                    int nr = arr[0];
                    int nc = arr[1];

                    if (cur.num < dist[nr][nc]) {

                        dist[nr][nc] = cur.num;
                        pq.add(new Node(nr, nc, cur.num));
                    }
                }
            }


            for (int i = 0; i < dr.length; i++) {

                int nr = cur.r + dr[i];
                int nc = cur.c + dc[i];

                if (nr < 0 || nr >= R || nc < 0 || nc >= C)
                    continue;

                if (grid[nr][nc] == '#')
                    continue;

                int NUM = cur.num;

                if (grid[nr][nc] == '*')
                    NUM += num;

                if (NUM < dist[nr][nc]) {

                    dist[nr][nc] = NUM;
                    pq.add(new Node(nr, nc, NUM));
                }
            }
        }

        return Integer.MAX_VALUE;
    }

    static class Node implements Comparable<Node> {
        int r, c, num;

        Node(int r, int c, int num) {
            this.r = r;
            this.c = c;
            this.num = num;
        }

        public int compareTo(Node o) {
            return this.num - o.num;
        }
    }

    static int R, C, num;
    static char[][] grid;
    static int[][] dist;

    static int[] dr = {1, -1, 0, 0};
    static int[] dc = {0, 0, 1, -1};

    static List<int[]> list;

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;

        while ((line = br.readLine()) != null) {

            if (line.trim().isEmpty()) continue;

            String[] input = line.split(" ");

            R = Integer.parseInt(input[0]);
            C = Integer.parseInt(input[1]);
            num = Integer.parseInt(input[2]);

            grid = new char[R][C];
            list = new ArrayList<>();

            int r = 0, c = 0;
            int tr = 0, tc = 0;

            for (int i = 0; i < R; i++) {

                String row = br.readLine();

                for (int j = 0; j < C; j++) {

                    grid[i][j] = row.charAt(j);

                    if (grid[i][j] == 'Y') {
                        r = i;
                        c = j;
                    }

                    if (grid[i][j] == 'C') {
                        tr = i;
                        tc = j;
                    }

                    if (grid[i][j] == 'P') {
                        list.add(new int[]{i, j});
                    }
                }
            }

            // 공주님을 구하고 돕는 메서드를 실행합니다
            int answer = SaveAndHelpPrincess(r, c, tr, tc);

            if (answer == Integer.MAX_VALUE)
                System.out.println("");
            else
                System.out.println(answer);
        }
    }

}
