import java.io.*;
import java.util.*;

public class BAEKJOON14499 {

    static int[] dx = {0, 0, -1, 1}; 
    static int[] dy = {1, -1, 0, 0};
    static int n, m, x, y, k, nx, ny;
    static int[][] map;
    static int[] moves;
    static int[] dice = new int[6]; 

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        x = Integer.parseInt(st.nextToken());
        y = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        map = new int[n][m];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        st = new StringTokenizer(br.readLine());
        moves = new int[k];
        for (int i = 0; i < k; i++) {
            moves[i] = Integer.parseInt(st.nextToken()) - 1;
        }

        for (int i = 0; i < k; i++) {
            int result = move(moves[i]);
            if (result != -1) {
                System.out.println(result);
            }
        }
    }

    public static int move(int d) {
        nx = x + dx[d];
        ny = y + dy[d];

        if (nx >= 0 && nx < n && ny >= 0 && ny < m) {
            int temp;
            if (d == 0) { 
                temp = dice[0];
                dice[0] = dice[3];
                dice[3] = dice[5];
                dice[5] = dice[2];
                dice[2] = temp;
            } else if (d == 1) { 
                temp = dice[0];
                dice[0] = dice[2];
                dice[2] = dice[5];
                dice[5] = dice[3];
                dice[3] = temp;
            } else if (d == 2) { 
                temp = dice[0];
                dice[0] = dice[4];
                dice[4] = dice[5];
                dice[5] = dice[1];
                dice[1] = temp;
            } else { 
                temp = dice[0];
                dice[0] = dice[1];
                dice[1] = dice[5];
                dice[5] = dice[4];
                dice[4] = temp;
            }

            if (map[nx][ny] == 0) {
                map[nx][ny] = dice[5];
            } else {
                dice[5] = map[nx][ny];
                map[nx][ny] = 0;
            }

            x = nx;
            y = ny;
            return dice[0];
        } else {
            return -1;
        }
    }
}
