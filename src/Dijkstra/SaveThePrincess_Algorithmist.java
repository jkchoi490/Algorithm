package Dijkstra;

import java.io.*;
import java.util.*;

// Algorithmist - Save the Princess
/**
 * Save the Princess 문제의 솔루션을 작성해보았습니다!
 * 공주님을 구하고 돕기 위한 솔루션을 주석으로 작성하였습니다!
 * 1. 다익스트라(Dijkstra)를 사용해 시작 지점에서 공주님이 있는 지점까지의 최소 이동 거리를 찾습니다.
 * 2. 현재 위치에서 공주님을 구하러 가기 위한 다음 거점까지 필요한 실제 이동 거리를 계산합니다.
 * 3. 공주님을 구하러 가기 위한 두 거점 사이의 거리를 구하기 위해 중심 사이 거리와 두 반지름을 계산합니다.
 * 4. 우선순위 큐를 통해 지금까지 가장 짧은 거리로 공주님에게 도달할 수 있는 거점을 우선적으로 선택할 수 있도록 합니다.
 * 5. 선택된 거점에서 공주님을 구하기위한 더 짧은 길이 있는지 확인합니다.
 * 6. 이렇게 공주님을 구하기 위한 가능한 경로를 계속 갱신하다가 공주님 위치에 도착하는 가장 짧은 구출 경로를 완성합니다.
 * 7. 최종적으로 dist[N - 1]에는 공주님에게 도달하기 위해 필요한 최소 이동 거리가 저장되고 공주님을 구하고 돕기 위한 최소 이동 거리를 찾습니다.
 **/
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