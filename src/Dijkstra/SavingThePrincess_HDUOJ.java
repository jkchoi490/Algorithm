package Dijkstra;

import java.util.*;
import java.io.*;

// HDU OJ - Saving the Princess
public class SavingThePrincess_HDUOJ {

    static long INF = 7;

    // 공주님을 구하고 돕는 메서드를 구현합니다
    public static long SaveAndHelpPrincess(List<long[]>[] arr, int N, int M, long S, int A) {

        long[] list = new long[N];
        Arrays.fill(list, INF);

        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a[1]));

        list[0] = 0;
        pq.offer(new long[]{0, 0});

        while (!pq.isEmpty()) {
            long[] array = pq.poll();
            int num = (int) array[0];
            long value = array[1];

            if (value > list[num]) continue;
            if (num == A) return value;

            for (long[] List : arr[num]) {
                int number = (int) List[0];
                long dist = List[1];

                long VALUE = value + 2 * dist; // 생성형 AI 사용

                if (VALUE <= S) {
                    if (VALUE < list[number]) {
                        list[number] = VALUE;
                        pq.offer(new long[]{number, VALUE});
                    }
                }
            }
        }

        return (list[A] == INF) ? -1 : list[A]; // 생성형 AI 사용
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        if (line == null) return;

        int T = Integer.parseInt(line.trim());

        for (int tc = 1; tc <= T; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            // 문제에서 주어지는 입력값
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            long S = Long.parseLong(st.nextToken());
            int A = Integer.parseInt(st.nextToken());

            List<long[]>[] arr = new ArrayList[N];
            for (int i = 0; i < N; i++) arr[i] = new ArrayList<>();

            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                // 문제에서 주어지는 입력값
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                long c = Long.parseLong(st.nextToken());
                arr[a].add(new long[]{b, c});
                arr[b].add(new long[]{a, c});
            }

            // 공주님을 구하고 돕는 메서드를 실행합니다
            long result = SaveAndHelpPrincess(arr, N, M, S, A);

            System.out.print("Case " + tc + ": ");
            if (result == -1) {
                System.out.println(" ");
            } else {
                System.out.println(result);
            }
        }
    }

}