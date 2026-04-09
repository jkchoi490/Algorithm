package Dijkstra;

import java.io.*;
import java.util.*;

// Kattis - All is Well
public class AllisWell {

    static final int[] DR = {-1, 1, 0, 0};
    static final int[] DC = {0, 0, -1, 1};

    static class FastReader {
        private final BufferedReader br;
        private StringTokenizer st;

        FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (st == null || !st.hasMoreTokens()) {
                String line = br.readLine();
                if (line == null) return null;
                st = new StringTokenizer(line);
            }
            return st.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }

    static class Node implements Comparable<Node> {
        int state;
        long dist;

        Node(int state, long dist) {
            this.state = state;
            this.dist = dist;
        }

        @Override
        public int compareTo(Node other) {
            return Long.compare(this.dist, other.dist);
        }
    }

    public static void main(String[] args) throws Exception {
        FastReader fr = new FastReader();

        // 문제에서 주어진 입력값
        int R = fr.nextInt();
        int C = fr.nextInt();
        int P = fr.nextInt();

        int[] range = new int[P];
        for (int i = 0; i < P; i++) {
            range[i] = fr.nextInt();
        }

        int[][] arr = new int[R][C];
        boolean[][] check = new boolean[R][C];
        int[] pr = new int[P];
        int[] pc = new int[P];

        Arrays.fill(pr, -1);
        Arrays.fill(pc, -1);

        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                String token = fr.next();
                char ch = token.charAt(0);

                if ('A' <= ch && ch <= 'Z') {
                    int idx = ch - 'A';
                    check[r][c] = true;
                    arr[r][c] = -1;

                    if (idx < P) {
                        pr[idx] = r;
                        pc[idx] = c;
                    }
                } else {
                    arr[r][c] = Integer.parseInt(token);
                }
            }
        }

        if (check[0][0]) {
            System.out.println("");
            return;
        }

        int N = R * C;

        boolean[][] array = new boolean[P][N];
        for (int i = 0; i < P; i++) {
            long value = 1L * range[i] * range[i];

            for (int r = 0; r < R; r++) {
                for (int c = 0; c < C; c++) {
                    if (check[r][c]) continue;

                    long dr = r - pr[i];
                    long dc = c - pc[i];
                    long d = dr * dr + dc * dc;

                    if (d <= value) {
                        array[i][r * C + c] = true;
                    }
                }
            }
        }

        long answer = dijkstra(R, C, P, arr, check, array);

        if (answer >= Long.MAX_VALUE) {
            System.out.println("");
        } else {
            System.out.println(answer);
        }
    }

    static long dijkstra(int R, int C, int P, int[][] arr, boolean[][] check, boolean[][] array) {
        int N = R * C;
        long[][] dist = new long[P + 1][N];

        for (int i = 0; i <= P; i++) {
            Arrays.fill(dist[i], Long.MAX_VALUE);
        }

        PriorityQueue<Node> pq = new PriorityQueue<>();

        int value = 0;
        int startValue = advanceValue(0, value, P, array);
        dist[startValue][value] = arr[0][0];

        int startState = encode(value, startValue, P);
        pq.offer(new Node(startState, arr[0][0]));

        while (!pq.isEmpty()) {
            Node cur = pq.poll();

            int Current = decode(cur.state, P);
            int curValue = decodeValue(cur.state, P);

            if (cur.dist != dist[curValue][Current]) continue;

            if (curValue == P) {
                return cur.dist;
            }

            int r = Current / C;
            int c = Current % C;

            for (int dir = 0; dir < 4; dir++) {
                int nr = r + DR[dir];
                int nc = c + DC[dir];

                if (nr < 0 || nr >= R || nc < 0 || nc >= C) continue;
                if (check[nr][nc]) continue;

                int NC = nr * C + nc;
                long Dist = cur.dist + arr[nr][nc];
                int Value = advanceValue(curValue, NC, P, array);

                if (Dist < dist[Value][NC]) {
                    dist[Value][NC] = Dist;
                    int State = encode(NC, Value, P);
                    pq.offer(new Node(State, Dist));
                }
            }
        }

        return Long.MAX_VALUE;
    }

    static int advanceValue(int value, int VALUE, int P, boolean[][] arr) {
        while (value < P && arr[value][VALUE]) {
            value++;
        }
        return value;
    }

    static int encode(int VALUE, int value, int P) {
        return VALUE * (P + 1) + value;
    }

    static int decode(int state, int P) {
        return state / (P + 1);
    }

    static int decodeValue(int state, int P) {
        return state % (P + 1);
    }
}