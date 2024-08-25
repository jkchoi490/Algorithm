import java.util.*;

class Solution_미로탈출명령어 {
    static String answer;
    static int[] dx = {1, 0, 0, -1}; 
    static int[] dy = {0, -1, 1, 0};
    static char[] dir = {'d', 'l', 'r', 'u'}; 
    static StringBuilder path;

    public String solution(int n, int m, int x, int y, int r, int c, int k) {
        answer = "impossible";
        path = new StringBuilder();
        
        x -= 1;
        y -= 1;
        r -= 1;
        c -= 1;
        
        int minDist = Math.abs(r - x) + Math.abs(c - y);
        if (minDist > k || (k - minDist) % 2 != 0) {
            return "impossible";
        }
        
        DFS(x, y, r, c, k, 0, n, m);
        return answer;
    }
    
    public void DFS(int x, int y, int r, int c, int k, int cnt, int n, int m) {
        if (!answer.equals("impossible")) {
            return; 
        }
        if (cnt + Math.abs(r - x) + Math.abs(c - y) > k) {
            return;
        }
        if (x == r && y == c && cnt == k) {
            answer = path.toString();
            return;
        }
        for (int d = 0; d < 4; d++) {
            int nx = x + dx[d];
            int ny = y + dy[d];
            if (nx >= 0 && nx < n && ny >= 0 && ny < m) {
                path.append(dir[d]);
                DFS(nx, ny, r, c, k, cnt + 1, n, m);
                if (!answer.equals("impossible")) return; 
                path.deleteCharAt(path.length() - 1);
            }
        }
    }
}
