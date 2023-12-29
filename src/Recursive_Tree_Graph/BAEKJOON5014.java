import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BAEKJOON5014 {
	static int answer = 0;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int F = Integer.parseInt(st.nextToken());
		int S = Integer.parseInt(st.nextToken());
		int G = Integer.parseInt(st.nextToken());
		int U = Integer.parseInt(st.nextToken());
		int D = Integer.parseInt(st.nextToken());
		
		answer = BFS(F,S,G,U,D);
		if(answer>=0)System.out.println(answer);
		else System.out.println("use the stairs");
	}

	private static int BFS(int F, int S, int G, int U, int D) {
		int cnt = -1;
		boolean[] visited = new boolean[F+1];
		Queue<Integer> q = new LinkedList<>();
		q.add(S);
		visited[S] = true;
		while(!q.isEmpty()) {
			int size = q.size();
			++cnt;
			while(size-- > 0) {
				int now = q.poll();
				if(now == G) return cnt;
				if(now+U <= F && !visited[now+U]) {
					q.add(now+U);
					visited[now+U] = true;
				}
				if(now-D > 0 && !visited[now+D]) {
					q.add(now-D);
					visited[now-D] = true;
				}
			}
		}
		return -1;
	}

}
