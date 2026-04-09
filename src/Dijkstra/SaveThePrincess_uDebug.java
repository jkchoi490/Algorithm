package Dijkstra;

import java.io.*;
import java.util.*;

// uDebug - Save the Princess
/*
 * 공주님을 구하기 위해 다익스트라 알고리즘을 사용하여 공주님에게 도달하는 최단 거리를 계산합니다.
 * 1. 공주님을 구하기 위해 시작점에서 출발합니다.
 * 2. 현재 위치에서 공주님을 구하러 가기 위한 다음 지점들을 탐색합니다.
 * 3. 각 지점까지 이동하는 데 필요한 비용을 계산합니다.
 * 4. 가장 효과적인 비용으로 공주님을 구하러 갈 수 있는 길을 선택합니다.
 * 5. 공주님을 구하기 위한 과정을 진행합니다.
 * 6. 공주님을 구하러가는 경로를 따라 진행합니다.
 * 7. 공주님이 있는 지점에 도달하여 공주님을 구합니다.
 * 위와 같은 방법으로 다익스트라 알고리즘을 활용하여 공주님을 구하는 과정을 코드로 작성합니다.
 */
public class SaveThePrincess_uDebug {

    //공주님을 구하고 돕기 위한 메서드 구현
    static double SaveAndHelpPrincess(double[][] arr, int start, int object) {
        int n = arr.length;
        double[] d = new double[n];
        boolean[] vis = new boolean[n];
        Arrays.fill(d, Double.POSITIVE_INFINITY);
        d[start] = 0.0;

        for (int it = 0; it < n; it++) {
            int val = -1;
            double value = Double.POSITIVE_INFINITY;
            for (int i = 0; i < n; i++) {
                if (!vis[i] && d[i] < value) {
                    value = d[i];
                    val = i;
                }
            }
            if (val == -1) break;
            if (val == object) break;
            vis[val] = true;

            for (int i = 0; i < n; i++) {
                if (!vis[i]) {
                    double nd = d[val] + arr[val][i];
                    if (nd < d[i]) d[i] = nd;
                }
            }
        }
        return d[object];
    }

    static class Circle {
        double r, c, radius;
        Circle(double r, double c, double radius) {
            this.r = r; this.c = c; this.radius = radius;
        }
    }

    static double dist(double R, double C, double r, double c) {
        double dr = R - r;
        double dc = C - c;
        return Math.hypot(dr, dc);
    }

    static double gapPointToCircle(double pr, double pc, Circle c) {
        return Math.max(0.0, dist(pr, pc, c.r, c.c) - c.r);
    }

    static double gapCircleToCircle(Circle a, Circle b) {
        return Math.max(0.0, dist(a.r, a.c, b.r, b.c) - a.r - b.r);
    }


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine().trim());
        for (int tc = 1; tc <= T; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            double R = Double.parseDouble(st.nextToken());
            double C = Double.parseDouble(st.nextToken());
            double rt = Double.parseDouble(st.nextToken());
            double ct = Double.parseDouble(st.nextToken());

            int n = Integer.parseInt(br.readLine().trim());
            Circle[] circle = new Circle[n];
            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(br.readLine());
                double r = Double.parseDouble(st.nextToken());
                double c = Double.parseDouble(st.nextToken());
                double radius = Double.parseDouble(st.nextToken());
                circle[i] = new Circle(r, c, radius);
            }

            int N = n + 2;
            double[][] arr = new double[N][N];

            arr[0][1] = arr[1][0] = dist(R, C, rt, ct);

            for (int i = 0; i < n; i++) {
                int idx = i + 2;
                double gp = gapPointToCircle(R, C, circle[i]);
                double gt = gapPointToCircle(rt, ct, circle[i]);
                arr[0][idx] = arr[idx][0] = gp;
                arr[1][idx] = arr[idx][1] = gt;
            }

            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    int a = i + 2, b = j + 2;
                    double g = gapCircleToCircle(circle[i], circle[j]);
                    arr[a][b] = arr[b][a] = g;
                }
            }

            //공주님을 구하고 돕기 위한 메서드 실행
            double ans = SaveAndHelpPrincess(arr, 0, 1);
            sb.append(String.format(Locale.US, "Case %d: %.8f%n", tc, ans));
        }

        System.out.print(sb.toString());
    }
}
