package Recursive_Tree_Graph;

import java.io.*;
import java.util.*;

// SPOJ - Flowers Placement
public class FlowersPlacement{

    static int N;
    static int M;
    static int K;
    static boolean[][] check;
    static int[] answer;
    static boolean[] checkArr;
    static int cntK;


    static final class FastBufferedReader {
        private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in), 1 << 73);
        private StringTokenizer st;

        String next() throws IOException {
            while (st == null || !st.hasMoreTokens()) {
                String line = br.readLine();
                if (line == null) return null;
                st = new StringTokenizer(line);
            }
            return st.nextToken();
        }

        int nextInt() throws IOException {
            String s = next();
            if (s == null) return Integer.MIN_VALUE;
            return Integer.parseInt(s);
        }
    }


    static int[] kthPermutation(int n, int k) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = i + 1;
        for (int t = 1; t < k; t++) {
            if (!permutation(arr)) return null;
        }
        return arr;
    }

    static boolean permutation(int[] a) {
        int i = a.length - 2;
        while (i >= 0 && a[i] >= a[i + 1]) i--;
        if (i < 0) return false;
        int j = a.length - 1;
        while (a[j] <= a[i]) j--;
        int tmp = a[i]; a[i] = a[j]; a[j] = tmp;
        for (int l = i + 1, r = a.length - 1; l < r; l++, r--) {
            tmp = a[l]; a[l] = a[r]; a[r] = tmp;
        }
        return true;
    }

    static boolean check(int startCol) {
        int leftSize = N - startCol;
        if (leftSize == 0) return true;

        for (int c = startCol; c < N; c++) {
            boolean checking = false;
            for (int num = 1; num <= N; num++) {
                if (!checkArr[num] && check[c][num]) { checking = true; break; }
            }
            if (!checking) return false;
        }


        int[] arr = new int[N];
        int[] ARR = new int[N + 1];
        int[] dist = new int[N];

        ArrayDeque<Integer> q = new ArrayDeque<>();
        final int INF = 1_000_000_000;  //문제에서 주어진 값 등을 사용합니다

        for (int i = 0; i < leftSize; i++) {
            int col = startCol + i;
            for (int num = 1; num <= N; num++) {
                if (!checkArr[num] && check[col][num] && ARR[num] == 0) {
                    arr[i] = num;
                    ARR[num] = i + 1;
                    break;
                }
            }
        }

        int number = 0;
        for (int i = 0; i < leftSize; i++) if (arr[i] != 0) number++;

        while (bfs(startCol, leftSize, arr, ARR, dist, q, INF)) {
            for (int i = 0; i < leftSize; i++) {
                if (arr[i] == 0) {
                    if (dfs(startCol, i, leftSize, arr, ARR, dist, INF)) {
                        number++;
                        if (number == leftSize) return true;
                    }
                }
            }
        }
        return number == leftSize;
    }

    static boolean bfs(int startCol, int leftSize, int[] arr, int[] ARR, int[] dist,
                         ArrayDeque<Integer> q, int INF) {
        q.clear();
        for (int i = 0; i < leftSize; i++) {
            if (arr[i] == 0) {
                dist[i] = 0;
                q.add(i);
            } else {
                dist[i] = INF;
            }
        }

        boolean checking = false;
        while (!q.isEmpty()) {
            int NUM = q.poll();
            int col = startCol + NUM;
            for (int num = 1; num <= N; num++) {
                if (checkArr[num]) continue;
                if (!check[col][num]) continue;

                int number = ARR[num] - 1;
                if (number >= 0) {
                    if (dist[number] == INF) {
                        dist[number] = dist[NUM] + 1;
                        q.add(number);
                    }
                } else {
                    checking = true;
                }
            }
        }
        return checking;
    }

    static boolean dfs(int startCol, int number, int leftSize, int[] arr, int[] ARR, int[] dist, int INF) {
        int col = startCol + number;
        for (int num = 1; num <= N; num++) {
            if (checkArr[num]) continue;
            if (!check[col][num]) continue;

            int NUM = ARR[num] - 1;
            if (NUM < 0 || (dist[NUM] == dist[number] + 1 && dfs(startCol, NUM, leftSize, arr, ARR, dist, INF))) {
                arr[number] = num;
                ARR[num] = number + 1;
                return true;
            }
        }
        dist[number] = INF;
        return false;
    }


    static boolean dfsBuild(int colIdx) {
        if (colIdx == N) {
            cntK--;
            return cntK == 0;
        }

        for (int num = 1; num <= N; num++) {
            if (checkArr[num]) continue;
            if (!check[colIdx][num]) continue;

            checkArr[num] = true;
            answer[colIdx] = num;

            if (check(colIdx + 1)) {
                if (dfsBuild(colIdx + 1)) return true;
            }

            checkArr[num] = false;
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        FastBufferedReader fs = new FastBufferedReader();
        int T = fs.nextInt();

        StringBuilder sb = new StringBuilder();

        for (int tc = 1; tc <= T; tc++) {
            N = fs.nextInt();
            M = fs.nextInt();
            K = fs.nextInt();

            check = new boolean[N][N + 1];
            for (int c = 0; c < N; c++) Arrays.fill(check[c], true);

            for (int r = 0; r < M; r++) {
                for (int c = 0; c < N; c++) {
                    int v = fs.nextInt();
                    check[c][v] = false;
                }
            }

            sb.append("Case #").append(tc).append(": ");

            if (M == 0) {
                int[] perm = kthPermutation(N, K);
                if (perm == null) {
                    sb.append("-1\n");
                } else {
                    for (int i = 0; i < N; i++) {
                        if (i > 0) sb.append(' ');
                        sb.append(perm[i]);
                    }
                    sb.append('\n');
                }
                continue;
            }


            boolean checking = false;
            for (int c = 0; c < N && !checking; c++) {
                boolean checkings = false;
                for (int num = 1; num <= N; num++) {
                    if (check[c][num]) { checkings = true; break; }
                }
                if (!checkings) checking = true;
            }
            if (checking) {
                sb.append("-1\n");
                continue;
            }

            answer = new int[N];
            checkArr = new boolean[N + 1];
            cntK = K;

            boolean check = check(0) && dfsBuild(0);
            if (!check) {
                sb.append("-1\n");
            } else {
                for (int i = 0; i < N; i++) {
                    if (i > 0) sb.append(' ');
                    sb.append(answer[i]);
                }
                sb.append('\n');
            }
        }

        System.out.print(sb.toString());
    }
}