package Dijkstra;

import java.util.*;
import java.io.*;

// Virtual Judge - Saving Princess claire_
public class SavingPrincessClaire_VirtualJudge {

    static int r, c, num;
    static char[] grid;
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    // 공주님을 구하고 돕는 메서드를 구현합니다
    static String SaveAndHelpPrincess() {

        int R = -1, C = -1; //임시 초기값
        List<Integer> list = new ArrayList<>();
        List<Integer> arrayList = new ArrayList<>();

        for (int i = 0; i < r * c; i++) {
            if (grid[i] == 'Y') R = i;
            else if (grid[i] == 'C') C = i;
            else if (grid[i] == 'P') list.add(i);
            else if (grid[i] == '*') arrayList.add(i);
        }

        int[] dist = new int[r * c];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[R] = 0;

        boolean check = false;
        boolean CHECK = false;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.offer(new int[]{R, 0});

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int num = cur[0], d = cur[1];
            if (d > dist[num]) continue;

            if (grid[num] == 'P' && !check) {
                check = true;
                for (int element : list) {
                    if (dist[d] < dist[element]) {
                        dist[element] = d;
                        pq.offer(new int[]{element, d});
                    }
                }
            }

            if (grid[num] == '*' && !CHECK) {
                CHECK = true;
                for (int element : arrayList) {
                    if (d < dist[element]) {
                        dist[element] = d;
                        pq.offer(new int[]{element, d});
                    }
                }
            }

            int row = num / c, col = num % c;
            for (int i = 0; i < 4; i++) {
                int nr = row + dr[i], nc = col + dc[i];
                if (nr < 0 || nr >= r || nc < 0 || nc >= c) continue;
                int value = nr * c + nc;
                if (grid[value] == '#') continue;
                int nd = d + (grid[value] == '*' ? num : 0);
                if (nd < dist[value]) {
                    dist[value] = nd;
                    pq.offer(new int[]{value, nd});
                }
            }
        }

        return dist[C] == Integer.MAX_VALUE ? "" : String.valueOf(dist[c]);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;
            StringTokenizer st = new StringTokenizer(line);
            r = Integer.parseInt(st.nextToken());
            c = Integer.parseInt(st.nextToken());
            num = Integer.parseInt(st.nextToken());

            grid = new char[r * c];
            int idx = 0;
            while (idx < r * c) {
                String row = br.readLine();
                if (row == null) break;
                for (char ch : row.toCharArray()) {
                    if (idx < r * c) grid[idx++] = ch;
                }
            }
            // 공주님을 구하고 돕는 메서드를 실행합니다
            sb.append(SaveAndHelpPrincess()).append('\n');
        }
        System.out.print(sb.toString());
    }

}