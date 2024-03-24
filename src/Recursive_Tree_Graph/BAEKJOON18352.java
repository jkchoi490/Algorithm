import java.io.*;
import java.util.*;

public class BAEKJOON18352 {

	static int N,M,K,X;
	static boolean[] visited;
	static int[] distance;
	static ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		X = Integer.parseInt(st.nextToken());
		distance = new int[N+1];
		visited = new boolean[N+1];
		for(int j = 0; j<N+1; j++) {
			graph.add(new ArrayList<>());
		}
	
		for(int i = 0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			int A = Integer.parseInt(st.nextToken());
			int B = Integer.parseInt(st.nextToken());
			graph.get(A).add(B);
		}
		
		BFS(X);

	}
	public static void BFS(int x) {
		ArrayList<Integer> answer = new ArrayList<>();
		Queue<Integer> Q = new LinkedList<>();
		Q.offer(x);
		visited[x] = true;
		distance[x] = 0;
		while(!Q.isEmpty()) {
			int now = Q.poll();
			for(int next : graph.get(now)) {
				if(visited[next] == false) {
					visited[next] = true;
					Q.offer(next);
					distance[next] = distance[now]+1;
					if(distance[next]== K) {
						answer.add(next);
					}
				}
			}
		}
		
		if(answer.size() == 0) System.out.println(-1);
		else {
			Collections.sort(answer);
			for(int ans : answer) {
				System.out.println(ans);
			}
		}
	}

}
