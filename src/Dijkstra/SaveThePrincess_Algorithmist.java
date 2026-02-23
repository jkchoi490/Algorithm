package Dijkstra;

import java.io.*;
import java.util.*;

// Algorithmist - Save the Princess
public class SaveThePrincess_Algorithmist {

    // 공주님을 구하고 돕는 메서드를 구현합니다
    static double SaveAndHelpPrincess(double[] R, double[] C, double[] r) {

        int N = R.length;

        double[] dist = new double[N];
        boolean[] visited = new boolean[N];

        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        dist[0] = 0.0;

        PriorityQueue<State> pq = new PriorityQueue<>();
        pq.offer(new State(0, 0.0));

        while (!pq.isEmpty()) {
            State current = pq.poll();
            int node = current.node;

            if (visited[node]) continue;
            visited[node] = true;

            if (node == N - 1) break;

            for (int num = 0; num < N; num++) {
                if (visited[num]) continue;

                double d = getDistance(R[node], C[node], R[num], C[num]) - r[node] - r[num];
                if (d < 0) d = 0;

                if (dist[num] > dist[node] + d) {
                    dist[num] = dist[node] + d;
                    pq.offer(new State(num, dist[num]));
                }
            }
        }

        return dist[N - 1];
    }

    static class State implements Comparable<State> {
        int node;
        double dist;

        State(int node, double dist) {
            this.node = node;
            this.dist = dist;
        }

        @Override
        public int compareTo(State o) {
            return Double.compare(this.dist, o.dist);
        }
    }

    static double getDistance(double r, double c, double R, double C) {
        return Math.hypot(r - R, c - C);
    }


    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int T = Integer.parseInt(br.readLine().trim());

        StringBuilder sb = new StringBuilder();

        for (int tc = 1; tc <= T; tc++) {

            st = new StringTokenizer(br.readLine());
            double pr = Double.parseDouble(st.nextToken());
            double pc = Double.parseDouble(st.nextToken());
            double R = Double.parseDouble(st.nextToken());
            double C = Double.parseDouble(st.nextToken());

            int n = Integer.parseInt(br.readLine().trim());

            int N = n + 2;

            double[] arr_R = new double[N];
            double[] arr_C = new double[N];
            double[] r = new double[N];

            arr_R[0] = pr;
            arr_C[0] = pc;
            r[0] = 0;

            for (int i = 1; i <= n; i++) {
                st = new StringTokenizer(br.readLine());
                arr_R[i] = Double.parseDouble(st.nextToken());
                arr_C[i] = Double.parseDouble(st.nextToken());
                r[i] = Double.parseDouble(st.nextToken());
            }

            arr_R[N - 1] = R;
            arr_C[N - 1] = C;
            r[N - 1] = 0;

            // 공주님을 구하고 돕는 메서드를 실행합니다
            double answer = SaveAndHelpPrincess(arr_R, arr_C, r);

            sb.append("Case ")
                    .append(tc)
                    .append(": ")
                    .append(String.format(Locale.US, "%.8f", answer))
                    .append("\n");
        }

        System.out.print(sb.toString());
    }
}