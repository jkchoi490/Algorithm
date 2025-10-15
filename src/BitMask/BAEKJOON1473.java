package BitMask;

import java.io.*;
import java.util.*;

//미로탈출
public class BAEKJOON1473 {
    static int n, m;
    static char[][] board;
    static int[][] dxdy = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}}; // 좌, 상, 우, 하

    static boolean canGo(int x, int y, int nx, int ny, int dir, int rowMask, int colMask) {
        char here = board[x][y];
        char there = board[nx][ny];

        // 행/열 회전 적용 여부
        if (((rowMask >> x) & 1) == 1) here = toggle(here);
        if (((colMask >> y) & 1) == 1) here = toggle(here);
        if (((rowMask >> nx) & 1) == 1) there = toggle(there);
        if (((colMask >> ny) & 1) == 1) there = toggle(there);

        if (here == 'A') {
            if (dir == 0 || dir == 2) return there == 'A' || there == 'D';
            else return there == 'A' || there == 'C';
        } else if (here == 'C') {
            if (dir == 1 || dir == 3) return there == 'A' || there == 'C';
        } else if (here == 'D') {
            if (dir == 0 || dir == 2) return there == 'A' || there == 'D';
        }
        return false;
    }

    static char toggle(char c) {
        if (c == 'C') return 'D';
        if (c == 'D') return 'C';
        return c;
    }

    static int solve() {
        Queue<int[]> q = new ArrayDeque<>();
        Set<Long> visited = new HashSet<>();

        q.offer(new int[]{0, 0, 0, 0}); // rowMask, colMask, x, y
        visited.add(encode(0, 0, 0, 0));

        int depth = 0;

        while (!q.isEmpty()) {
            int size = q.size();
            while (size-- > 0) {
                int[] cur = q.poll();
                int rowMask = cur[0], colMask = cur[1], x = cur[2], y = cur[3];

                if (x == n - 1 && y == m - 1) return depth;

                // 4방향 이동
                for (int d = 0; d < 4; d++) {
                    int nx = x + dxdy[d][0];
                    int ny = y + dxdy[d][1];
                    if (nx < 0 || nx >= n || ny < 0 || ny >= m) continue;

                    if (canGo(x, y, nx, ny, d, rowMask, colMask)) {
                        long code = encode(rowMask, colMask, nx, ny);
                        if (!visited.contains(code)) {
                            visited.add(code);
                            q.offer(new int[]{rowMask, colMask, nx, ny});
                        }
                    }
                }

                // 버튼 누르기
                int nextRowMask = rowMask ^ (1 << x);
                int nextColMask = colMask ^ (1 << y);
                long code = encode(nextRowMask, nextColMask, x, y);
                if (!visited.contains(code)) {
                    visited.add(code);
                    q.offer(new int[]{nextRowMask, nextColMask, x, y});
                }
            }
            depth++;
        }

        return -1;
    }

    static long encode(int rowMask, int colMask, int x, int y) {
        // 비트 압축: [rowMask(7비트)][colMask(7비트)][x(3비트)][y(3비트)]
        return ((long) rowMask << 16) | ((long) colMask << 9) | (x << 3) | y;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        board = new char[n][m];
        for (int i = 0; i < n; i++) board[i] = br.readLine().trim().toCharArray();

        System.out.println(solve());
    }
}