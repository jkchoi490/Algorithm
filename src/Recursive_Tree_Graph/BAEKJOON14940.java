import java.io.*;
import java.util.*;

public class BAEKJOON14940 {

	static int n,m;
	static int[][] map;
	static int[][] distance;
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, 1, 0, -1};
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		map = new int[n][m];
		distance = new int[n][m];
		int x = 0, y = 0;
		for(int i = 0; i<n; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j<m; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j] == 2) {
					x = i;
					y = j;
				}
			}
		}
		
		BFS(x, y);
		
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i<n; i++) {
			for(int j = 0; j<m; j++) {
				if(distance[i][j] == 0 && map[i][j] == 1) {
					sb.append(-1).append(" ");
				}
				else sb.append(distance[i][j]).append(" ");
			}
			sb.append("\n");
		}
		System.out.println(sb.toString());
	}
	public static void BFS(int x, int y) {
		Queue<int[]> q = new LinkedList<>();
		boolean[][] visited = new boolean[n][m];
		q.offer(new int[] {x, y});
		distance[x][y] = 0;
		visited[x][y] = true;
		while(!q.isEmpty()) {
			int[] cur = q.poll();
			for(int d = 0; d<4; d++) {
				int nx = cur[0]+dx[d];
				int ny = cur[1]+dy[d];
				if(nx>=0 && nx<n && ny>=0 && ny<m && !visited[nx][ny] && map[nx][ny]==1) {
					distance[nx][ny] = distance[cur[0]][cur[1]]+1;
					visited[nx][ny] = true;
					q.offer(new int[] {nx, ny});
				}
			}
		}
		
	}
	

}
