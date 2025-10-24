package BFS;

import java.io.*;
import java.util.*;

//Escape
class Escape  {
    static class Cell {
        int x, y;
        Cell(int x, int y) { this.x = x; this.y = y; }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());

            Set<Long> blocked = new HashSet<>();
            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                blocked.add(encode(x, y));
            }

            if (blocked.contains(encode(1, 2)) && blocked.contains(encode(2, 1))) {
                sb.append("NO\n");
                continue;
            }
            if (blocked.contains(encode(N, N - 1)) && blocked.contains(encode(N - 1, N))) {
                sb.append("NO\n");
                continue;
            }

            boolean escaped = canEscape(N, blocked);
            sb.append(escaped ? "YES\n" : "NO\n");
        }

        System.out.print(sb);
    }

    static boolean canEscape(int N, Set<Long> blocked) {
        Queue<Cell> q = new ArrayDeque<>();
        Set<Long> visited = new HashSet<>();
        q.add(new Cell(1, 1));
        visited.add(encode(1, 1));

        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, 1, -1};

        while (!q.isEmpty()) {
            Cell c = q.poll();
            if (c.x == N && c.y == N) return true;

            for (int dir = 0; dir < 4; dir++) {
                int nx = c.x + dx[dir];
                int ny = c.y + dy[dir];
                if (nx < 1 || ny < 1 || nx > N || ny > N) continue;

                long code = encode(nx, ny);
                if (blocked.contains(code) || visited.contains(code)) continue;

                visited.add(code);
                q.add(new Cell(nx, ny));

                if (visited.size() > 1_000_000) break;
            }
        }
        return false;
    }

    static long encode(int x, int y) {
        return ((long) x << 20) | y;
    }
}
