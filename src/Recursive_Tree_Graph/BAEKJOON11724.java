import java.io.*;
import java.util.*;

public class BAEKJOON11724 {

	static int N,M;
	static boolean[] visited;
	static ArrayList<ArrayList<Integer>> graph;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		graph = new ArrayList<>();
		visited = new boolean[N+1];
		for(int i = 0; i<N+1; i++) {
			graph.add(new ArrayList<Integer>());
		}
		for(int i = 0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			graph.get(u).add(v);
			graph.get(v).add(u);
		}
		
	
		int answer = 0;
		for(int i = 1; i<=N; i++) {
			if(!visited[i]) {
				BFS(i, visited, graph);
				answer++;
			}
		}
		System.out.println(answer);
	}
	public static void BFS(int v, boolean[] visited, ArrayList<ArrayList<Integer>> graph) {
		Queue<Integer> q = new LinkedList<>();
		
		visited[v] = true;
		q.offer(v);
		
		while(!q.isEmpty()) {
			int nv = q.poll();
			for(int x : graph.get(nv)) {
				if(!visited[x]) {
					visited[x] = true;
					q.offer(x);
					}
				}
		}
	}

}
