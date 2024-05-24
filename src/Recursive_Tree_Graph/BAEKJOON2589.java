import java.io.*;
import java.util.*;

public class BAEKJOON2589 {

	static int n, m, max = Integer.MIN_VALUE;
	static char[][] map;
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, 1, 0, -1};
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		map = new char[n][m];
		for(int i = 0; i<n; i++) {
			map[i] = br.readLine().toCharArray();
		}
		
		for(int i = 0; i<n; i++) {
			for(int j = 0; j<m; j++) {
				if(map[i][j] == 'L') {
					BFS(i,j);
				}
			}
		}
		System.out.println(max);
	}
	private static void BFS(int x, int y) {
		int[][] dist = new int[n][m];
		boolean[][] visited = new boolean[n][m];
		Queue<int[]> q = new LinkedList<>();
		visited[x][y] = true;
		q.offer(new int[] {x, y});
		
		while(!q.isEmpty()) {
			int[] cur = q.poll();
			for(int d = 0; d<4; d++) {
				int nx = cur[0]+dx[d];
				int ny = cur[1]+dy[d];
				if(nx>=0 && nx<n && ny>=0 && ny<m && !visited[nx][ny] && map[nx][ny]=='L') {
					dist[nx][ny] = dist[cur[0]][cur[1]] + 1;
					visited[nx][ny] = true;
					q.offer(new int[] {nx, ny});
					max = Math.max(max, dist[nx][ny]);
				}
			}
		}
		
	}

}
