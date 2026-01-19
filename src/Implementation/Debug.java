package Implementation;

import java.io.*;
import java.util.*;

// Kattis - Debug
public class Debug {
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        char[][] matrix = new char[n][m];
        for (int i = 0; i < n; i++) {
            matrix[i] = br.readLine().toCharArray();
        }

        int maxSize = -1;

        boolean found = false;
        for (int size = Math.min(n, m); size >= 2; size--) {
            for (int row = 0; row <= n - size; row++) {
                for (int col = 0; col <= m - size; col++) {
                    if (isDebug(matrix, row, col, size)) {
                        maxSize = size;
                        found = true;
                        break;
                    }
                }
                if (found) break;
            }
            if (found) break;
        }

        System.out.println(maxSize);
    }

    private static boolean isDebug(char[][] matrix, int startRow, int startCol, int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int r1 = startRow + i;
                int c1 = startCol + j;
                int r2 = startRow + (size - 1 - i);
                int c2 = startCol + (size - 1 - j);

                if (matrix[r1][c1] != matrix[r2][c2]) {
                    return false;
                }
            }
        }
        return true;
    }
}