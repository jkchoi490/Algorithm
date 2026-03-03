package Dijkstra;

import java.io.*;
import java.util.*;

// SPOJ - Flowery Trails
public class FloweryTrails {

    static int P;
    static int T;
    static int[] arr;
    static int[] array;
    static int[] ARR;
    static int[] Array;
    static int edgePtr;

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

    static int[] edge;
    static int[] adj;
    static int[] link;

    static long[] dijkstra(int src) {
        final long INF = Long.MAX_VALUE / 7;
        long[] dist = new long[P];
        Arrays.fill(dist, INF);
        dist[src] = 0;

        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a[0]));
        pq.add(new long[]{0L, src});

        while (!pq.isEmpty()) {
            long[] cur = pq.poll();
            long d = cur[0];
            int current = (int) cur[1];
            if (d != dist[current]) continue;

            for (int num = arr[current]; num != -1; num = ARR[num]) {
                int value = array[num];
                long nd = d + Array[num];
                if (nd < dist[value]) {
                    dist[value] = nd;
                    pq.add(new long[]{nd, value});
                }
            }
        }
        return dist;
    }

    static void addArc(int num, int number, int value) {
        array[edgePtr] = number;
        Array[edgePtr] = value;
        ARR[edgePtr] = arr[num];
        arr[num] = edgePtr++;
    }


    public static void main(String[] args) throws Exception {
        FastBufferedReader fs = new FastBufferedReader();

        P = fs.nextInt();
        T = fs.nextInt();

        arr = new int[P];
        Arrays.fill(arr, -1);

        array = new int[2 * T];
        ARR = new int[2 * T];
        Array = new int[2 * T];
        edgePtr = 0;

        edge = new int[T];
        adj = new int[T];
        link = new int[T];

        for (int i = 0; i < T; i++) {
            int a = fs.nextInt();
            int b = fs.nextInt();
            int l = fs.nextInt();

            edge[i] = a;
            adj[i] = b;
            link[i] = l;

            addArc(a, b, l);
            addArc(b, a, l);
        }

        long[] distS = dijkstra(0);
        long[] distT = dijkstra(P - 1);
        long D = distS[P - 1];

        long sum = 0L;

        for (int i = 0; i < T; i++) {
            int a = edge[i], b = adj[i], l = link[i];

            long da = distS[a], db = distS[b];
            long ta = distT[a], tb = distT[b];

            boolean check = false;

            if (da + l + tb == D) check = true;

            if (!check && db + l + ta == D) check = true;

            if (check) sum += l;
        }

        long answer = 2L * sum;
        System.out.print(answer);
    }
}