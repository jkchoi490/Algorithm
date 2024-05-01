import java.io.*;
import java.util.*;

public class BAEKJOON2668 {
    static int N;
    static int[] arr, result;
    static boolean[] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        arr = new int[N+1];
        result = new int[N+1];
        visited = new boolean[N+1];

        for (int i = 1; i <= N; i++) {
            arr[i] = Integer.parseInt(br.readLine());
        }

        for (int i = 1; i <= N; i++) {
            visited[i] = true;
            DFS(i, arr[i]);
            visited[i] = false;
        }

        int count = 0;
        for (int i = 1; i <= N; i++) {
            if (result[i] == 1) {
                count++;
            }
        }
        System.out.println(count);

        for (int i = 1; i <= N; i++) {
            if (result[i] == 1) {
                System.out.println(i);
            }
        }

    }

    public static void DFS(int start, int cur) {
        if (visited[cur]) {
            if (start == cur) {
                result[start] = 1;
            }
            return;
        }

        visited[cur] = true;
        DFS(start, arr[cur]);
        visited[cur] = false;
    }
}
