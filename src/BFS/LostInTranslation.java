package BFS;

import java.io.*;
import java.util.*;

//Kattis - Lost in Translation
public class LostInTranslation {

    static class Edge {
        String to;
        int cost;
        Edge(String t, int c) { to = t; cost = c; }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        int L = Integer.parseInt(st.nextToken()); // target languages
        int T = Integer.parseInt(st.nextToken()); // translators

        String[] targets = br.readLine().split(" ");
        Set<String> targetSet = new HashSet<>(Arrays.asList(targets));
        targetSet.add("English");

        Map<String, List<Edge>> graph = new HashMap<>();
        for (String s : targetSet) graph.put(s, new ArrayList<>());

        for (int i = 0; i < T; i++) {
            st = new StringTokenizer(br.readLine());
            String a = st.nextToken();
            String b = st.nextToken();
            int c = Integer.parseInt(st.nextToken());
            if (!graph.containsKey(a)) graph.put(a, new ArrayList<>());
            if (!graph.containsKey(b)) graph.put(b, new ArrayList<>());
            graph.get(a).add(new Edge(b, c));
            graph.get(b).add(new Edge(a, c));
        }

        // BFS
        Map<String, Integer> dist = new HashMap<>();
        for (String s : graph.keySet()) dist.put(s, Integer.MAX_VALUE);

        Queue<String> q = new LinkedList<>();
        q.add("English");
        dist.put("English", 0);

        while (!q.isEmpty()) {
            String cur = q.poll();
            int cd = dist.get(cur);
            for (Edge e : graph.get(cur)) {
                if (dist.get(e.to) > cd + 1) {
                    dist.put(e.to, cd + 1);
                    q.add(e.to);
                }
            }
        }

        for (String tg : targets) {
            if (dist.get(tg) == Integer.MAX_VALUE) {
                System.out.println("Impossible");
                return;
            }
        }

        long totalCost = 0;

        for (String lang : targets) {
            int d = dist.get(lang);
            int bestCost = Integer.MAX_VALUE;

            for (Edge e : graph.get(lang)) {
                if (dist.get(e.to) == d - 1) {
                    bestCost = Math.min(bestCost, e.cost);
                }
            }

            if (bestCost == Integer.MAX_VALUE) {
                System.out.println("Impossible");
                return;
            }

            totalCost += bestCost;
        }

        System.out.println(totalCost);
    }
}
