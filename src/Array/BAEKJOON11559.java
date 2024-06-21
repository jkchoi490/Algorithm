import java.io.*;
import java.util.*;

public class BAEKJOON11559 {

    static char[][] map;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        map = new char[12][6];
        for (int i = 0; i < 12; i++) {
            map[i] = br.readLine().toCharArray();
        }

        down();
        /*
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 6; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        */
    }

    private static void down() {
        for (int j = 0; j < 6; j++) { 
            for (int i = 11; i > 0; i--) {
                if (map[i][j] == '.') {
                    int k = i - 1;
                    while (k >= 0 && map[k][j] == '.') {
                        k--;
                    }
                    if (k >= 0) {
                        map[i][j] = map[k][j];
                        map[k][j] = '.';
                    }
                }
            }
        }
    }
}
