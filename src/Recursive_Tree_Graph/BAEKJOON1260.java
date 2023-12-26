package DFS_and_BFS;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BAEKJOON1260 {

	static ArrayList<ArrayList<Integer>> graph = new ArrayList<ArrayList<Integer>>();
	static boolean[] visited, visited2;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int V = Integer.parseInt(st.nextToken());

		visited = new boolean[N + 1];
		visited2 = new boolean[N + 1];
		for (int i = 0; i <= N; i++) {
			graph.add(new ArrayList<Integer>());
		}
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			graph.get(a).add(b);
			graph.get(b).add(a);
		}

		
		 for(int i = 0; i<=N; i++) {
			 Collections.sort(graph.get(i));
			 //System.out.println(graph.get(i));
		 }

		DFS(V);
		System.out.println();
		BFS(V, graph, visited2);
	}

	public static void DFS(int v) {
		visited[v] = true;
		System.out.print(v + " ");

		for (int nv : graph.get(v)) {
			if (!visited[nv]) {
				visited[nv] = true;
				DFS(nv);
			}
		}

	}

	private static void BFS(int start, ArrayList<ArrayList<Integer>> graph, boolean[] visited2) {

		Queue<Integer> q = new LinkedList<Integer>();

		q.offer(start);
		visited2[start] = true;

		while (!q.isEmpty()) {
			int v = q.poll();
			System.out.print(v + " ");
			for (int i = 0; i < graph.get(v).size(); i++) {
				int temp = graph.get(v).get(i);
				if (!visited2[temp]) {
					visited2[temp] = true;
					q.offer(temp);
					
				}
			}
		}

	}

}
