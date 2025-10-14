package BFS;

import java.io.*;
import java.util.*;

//Save the Princess!!!
public class SaveThePrincess {
    static int n, m;
    static char[][] grid;
    static boolean[][][] visited;
    static int[] dx = {-1, 1, 0, 0, 0};
    static int[] dy = {0, 0, -1, 1, 0};

    static class Node {
        int x, y, t;
        Node(int x, int y, int t) {
            this.x = x;
            this.y = y;
            this.t = t;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();
        sc.nextLine();
        grid = new char[n + 2][m];

        int sx = 0, sy = 0, tx = 0, ty = 0;
        for (int i = 0; i < n + 2; i++) {
            String line = sc.nextLine().trim();
            for (int j = 0; j < m; j++) {
                grid[i][j] = line.charAt(j);
                if (grid[i][j] == 'B') {
                    sx = i;
                    sy = j;
                } else if (grid[i][j] == 'T') {
                    tx = i;
                    ty = j;
                }
            }
        }

        visited = new boolean[n + 2][m][2 * m]; // 주기를 2m으로 확장
        int ans = bfs(sx, sy, tx, ty);

        if (ans == -1) System.out.println("Impossible");
        else System.out.println(ans);
    }

    static int bfs(int sx, int sy, int tx, int ty) {
        Queue<Node> q = new LinkedList<>();
        q.add(new Node(sx, sy, 0));
        visited[sx][sy][0] = true;

        while (!q.isEmpty()) {
            Node cur = q.poll();
            if (cur.x == tx && cur.y == ty) return cur.t;

            for (int dir = 0; dir < 5; dir++) {
                int nx = cur.x + dx[dir];
                int ny = cur.y + dy[dir];
                int nt = cur.t + 1;

                if (nx < 0 || nx >= n + 2 || ny < 0 || ny >= m) continue;
                if (visited[nx][ny][nt % (2 * m)]) continue;
                if (!isSafe(nx, ny, nt)) continue;

                visited[nx][ny][nt % (2 * m)] = true;
                q.add(new Node(nx, ny, nt));
            }
        }
        return -1;
    }

    static boolean isSafe(int x, int y, int t) {
        if (x == 0 || x == n + 1) return true; // 첫, 마지막 행엔 몬스터 없음

        for (int j = 0; j < m; j++) {
            if (grid[x][j] == 'X') {
                int ny;
                if (x % 2 == 1) {
                    // 홀수 행: 왼쪽 이동
                    ny = (j - (t % m) + m) % m;
                } else {
                    // 짝수 행: 오른쪽 이동
                    ny = (j + (t % m)) % m;
                }
                if (ny == y) return false;
            }
        }
        return true;
    }
}

