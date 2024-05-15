import java.io.*;
import java.util.*;

public class BAEKJOON1697 {

	static int n,k;
	static int[] dx = {-1, 1};
	static boolean[] visited;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		
		visited = new boolean[100001];
		int answer = BFS();
		System.out.println(answer);

	}
	private static int BFS() {
		Queue<Integer> q = new LinkedList<>();
		visited[n] = true;
		q.offer(n);
		int L = 0;
		while(!q.isEmpty()) {
			int len = q.size();
			for(int i = 0; i<len; i++) {
				int now = q.poll();
				for(int d = 0; d<2; d++) {
					int nx = now+dx[d];
					if(nx>=0 && nx <= 100000 && !visited[nx]) {
						if(nx == k) return L+1;
						visited[nx] = true;
						q.offer(nx);
					}
					
				}
				
				int nx = now*2;
				if(nx>=0 && nx<=100000 && !visited[nx]) {
					if(nx==k) return L+1;
					visited[nx] = true;
					q.offer(nx);
				}
				
			}
			L++;
		}
		return 0;
	}

}
