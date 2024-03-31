import java.io.*;
import java.util.*;

public class BAEKJOON2606 {
	static ArrayList<ArrayList<Integer>> graph;
	static boolean[] visited;
	static int cnt = 0;
	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		int m = Integer.parseInt(br.readLine());
		graph = new ArrayList<ArrayList<Integer>>();
		visited = new boolean[n+1];
		StringTokenizer st;
		for(int i = 0; i<=n; i++) {
			graph.add(new ArrayList<Integer>());
		}
		for(int i = 0; i<m; i++) {
			st  = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			graph.get(a).add(b);
			graph.get(b).add(a);
		}
		
		DFS(1);
		System.out.println(cnt);
	}
	public static void DFS(int v) {
		visited[v] = true;
		for(int next : graph.get(v)) {
			if(!visited[next]) {
				visited[next] = true;
				cnt++;
				DFS(next);
			}
		}
	}

}
