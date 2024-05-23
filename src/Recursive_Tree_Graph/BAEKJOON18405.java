import java.io.*;
import java.util.*;

public class BAEKJOON18405 {

	static int n,k,s;
	static int[][] map;
	static Queue<int[]> q;
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0 , 1, 0, -1};
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		map = new int[n][n];
	
		PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->a[0]-b[0]);
		for(int i = 0; i<n; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j<n; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j] > 0) pq.offer(new int[] {map[i][j], i, j, 0});
			}
		}
		q = new LinkedList<>();
		while (!pq.isEmpty()) {
            q.offer(pq.poll());
        }
		
		st = new StringTokenizer(br.readLine());
		s = Integer.parseInt(st.nextToken());
		int x = Integer.parseInt(st.nextToken())-1;
		int y = Integer.parseInt(st.nextToken())-1;
		
	
		BFS();
	
		System.out.println(map[x][y]);
	}
	private static void BFS() {
		while(!q.isEmpty()) {
				int[] cur = q.poll();
				int time = cur[3];
				
				if(time == s) return;
				for(int d = 0; d<4; d++) {
					int nx = cur[1]+dx[d];
					int ny = cur[2]+dy[d];
					if(nx>=0 && nx<n && ny>=0 && ny<n && map[nx][ny] == 0) {
						map[nx][ny] = cur[0];
						q.offer(new int[] {cur[0], nx, ny, time+1});
					}
				}
			
		}
	}

}
