import java.io.*;
import java.util.*;

public class BAEKJOON13549 {
	
	static int[] dx = {1, -1};

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		
		int answer = BFS(n,k);
		System.out.println(answer);
	}

	private static int BFS(int n, int k) {
		int[] visited = new int[100001];
		Deque<Integer> dq = new LinkedList<>();
		Arrays.fill(visited, -1);
		visited[n] = 0;
		dq.offer(n);
	
		while(!dq.isEmpty()) {
				int x = dq.poll();
				
				if(x == k) return visited[x];
				
				int nx = x*2;
				
				if(nx>=0 && nx<=100000 && (visited[nx] == -1 || visited[nx] > visited[x])) {
					visited[nx] = visited[x];
					dq.offerFirst(nx);
				}
				
				for(int d = 0; d<2; d++) {
					nx = x + dx[d];
					if(nx>=0 && nx<=100000 &&(visited[nx] == -1 || visited[nx] > visited[x] + 1)) {
						visited[nx] = visited[x]+1;
						dq.offerLast(nx);
					}
				}
	
		}
		return -1;
		
	}

}
