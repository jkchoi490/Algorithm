package BFS;

import java.util.*;

// Escape!
public class Solution_Escape {
    static class State {
        int x, y, keys;
        State prev;

        State(int x, int y, int keys, State prev) {
            this.x = x;
            this.y = y;
            this.keys = keys;
            this.prev = prev;
        }
    }

    public static List<int[]> escape(String[] grid) {
        int n = grid.length, m = grid[0].length();
        int startX = 0, startY = 0;
        int totalKeys = 0;

        // 키 개수와 시작 위치 탐색
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < m; x++) {
                char c = grid[y].charAt(x);
                if (c == '@') {
                    startX = x; startY = y;
                } else if (Character.isLowerCase(c)) {
                    totalKeys |= (1 << (c - 'a'));
                }
            }
        }

        int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
        boolean[][][] visited = new boolean[n][m][1 << 8];
        Queue<State> q = new LinkedList<>();
        State start = new State(startX, startY, 0, null);
        q.offer(start);
        visited[startY][startX][0] = true;

        while (!q.isEmpty()) {
            State cur = q.poll();
            char cell = grid[cur.y].charAt(cur.x);

            // 키 수집
            int keys = cur.keys;
            if (Character.isLowerCase(cell)) {
                keys |= (1 << (cell - 'a'));
            }

            // 출구 도착 & 모든 키 보유
            if (cell == '$' && keys == totalKeys) {
                return reconstructPath(cur);
            }

            for (int[] d : dirs) {
                int nx = cur.x + d[0];
                int ny = cur.y + d[1];
                if (nx < 0 || ny < 0 || nx >= m || ny >= n) continue;

                char next = grid[ny].charAt(nx);
                if (next == '#') continue; // 벽

                // 문 통과 가능 여부
                if (Character.isUpperCase(next)) {
                    if ((keys & (1 << (next - 'A'))) == 0) continue;
                }

                if (!visited[ny][nx][keys]) {
                    visited[ny][nx][keys] = true;
                    q.offer(new State(nx, ny, keys, cur));
                }
            }
        }

        return null; // 탈출 불가
    }

    private static List<int[]> reconstructPath(State end) {
        List<int[]> path = new ArrayList<>();
        for (State cur = end; cur != null; cur = cur.prev) {
            path.add(new int[]{cur.x, cur.y});
        }
        Collections.reverse(path);
        return path;
    }

    // === 테스트용 메인 ===
    public static void main(String[] args) {
        String[] grid = {
                ".a..",
                "##@#",
                "$A.#"
        };
        List<int[]> res = escape(grid);
        if (res == null) {
            System.out.println("No path");
        } else {
            for (int[] p : res) {
                System.out.print("(" + p[0] + "," + p[1] + ") ");
            }
        }
    }
}
