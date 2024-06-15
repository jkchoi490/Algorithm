import java.io.*;
import java.util.*;

public class BAEKJOON17144 {

    static int R, C, T;
    static int[][] map;
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};
    static int upCleaner = -1, downCleaner = -1;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());
        map = new int[R][C];

        for (int i = 0; i < R; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < C; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == -1) {
                    if (upCleaner == -1) {
                        upCleaner = i;
                    } else {
                        downCleaner = i;
                    }
                }
            }
        }

        while (T-- > 0) {
            spread();
            Upair();
            Downair();
        }

        int sum = 0;
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (map[i][j] > 0) {
                    sum += map[i][j];
                }
            }
        }
        System.out.println(sum);
    }

    private static void spread() {
        int[][] newMap = new int[R][C];
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                newMap[i][j] = map[i][j];
            }
        }

        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (map[i][j] > 0) {
                    int div = map[i][j] / 5;
                    int cnt = 0;
                    for (int d = 0; d < 4; d++) {
                        int nx = i + dx[d];
                        int ny = j + dy[d];
                        if (nx >= 0 && nx < R && ny >= 0 && ny < C && map[nx][ny] != -1) {
                            cnt++;
                            newMap[nx][ny] += div;
                        }
                    }
                    newMap[i][j] -= div * cnt;
                }
            }
        }
        map = newMap;
    }

    private static void Upair() {
        for (int i = upCleaner - 1; i > 0; i--) {
            map[i][0] = map[i - 1][0];
        }
        for (int i = 0; i < C - 1; i++) {
            map[0][i] = map[0][i + 1];
        }
        for (int i = 0; i < upCleaner; i++) {
            map[i][C - 1] = map[i + 1][C - 1];
        }
        for (int i = C - 1; i > 1; i--) {
            map[upCleaner][i] = map[upCleaner][i - 1];
        }
        map[upCleaner][1] = 0;
    }

    private static void Downair() {
        for (int i = downCleaner + 1; i < R - 1; i++) {
            map[i][0] = map[i + 1][0];
        }
        for (int i = 0; i < C - 1; i++) {
            map[R - 1][i] = map[R - 1][i + 1];
        }
        for (int i = R - 1; i > downCleaner; i--) {
            map[i][C - 1] = map[i - 1][C - 1];
        }
        for (int i = C - 1; i > 1; i--) {
            map[downCleaner][i] = map[downCleaner][i - 1];
        }
        map[downCleaner][1] = 0;
    }
}
