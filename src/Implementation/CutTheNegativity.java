package Implementation;

import java.io.*;
import java.util.*;

//Kattis - Cut the Negativity
public class CutTheNegativity {

    static class Edge implements Comparable<Edge> {
        int from, to, cost;
        Edge(int f, int t, int c) {
            from = f;
            to = t;
            cost = c;
        }
        @Override
        public int compareTo(Edge o) {
            if (this.from != o.from) return this.from - o.from;
            return this.to - o.to;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int n = Integer.parseInt(br.readLine());

        List<Edge> list = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= n; j++) {
                int cost = Integer.parseInt(st.nextToken());
                if (cost != -1) {
                    list.add(new Edge(i, j, cost));
                }
            }
        }

        Collections.sort(list);

        StringBuilder sb = new StringBuilder();
        sb.append(list.size()).append("\n");
        for (Edge e : list) {
            sb.append(e.from).append(" ")
                    .append(e.to).append(" ")
                    .append(e.cost).append("\n");
        }
        System.out.print(sb.toString());
    }
}