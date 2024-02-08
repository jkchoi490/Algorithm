import java.io.*;
import java.util.*;

public class BAEKJOON7562 {

	static int l, start_x, start_y, end_x, end_y;
	static int[] dx = {-1, 1, -2, 2, -2, 2, -1, 1};
	static int[] dy = {-2, -2, -1, -1, 1, 1, 2, 2};
	static int[][] board;
	static boolean[][] visited;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		for(int tc = 1; tc<=T; tc++) {
			l = Integer.parseInt(br.readLine());
			board = new int[l][l];
			visited = new boolean[l][l];
			StringTokenizer st = new StringTokenizer(br.readLine());
			start_x = Integer.parseInt(st.nextToken());
			start_y = Integer.parseInt(st.nextToken());
			st = new StringTokenizer(br.readLine());
			end_x = Integer.parseInt(st.nextToken());
			end_y = Integer.parseInt(st.nextToken());
			System.out.println(BFS(start_x,start_y));
			
		}
		
		
		
	}
	
	public static int BFS(int x, int y) {

		Queue<int[]> q = new LinkedList<>();
		q.offer(new int[] {x,y});
		visited[x][y] = true;
		
		while(!q.isEmpty()) {
	
			int[] now = q.poll();
			x = now[0];
			y = now[1];
			
			if(x==end_x && y==end_y) return board[x][y];
			for(int i = 0; i<8; i++) {
				int nx = x+dx[i];
				int ny = y+dy[i];

				if(nx>=0 && nx<l && ny>=0 && ny<l && visited[nx][ny]==false) {
					q.offer(new int[] {nx, ny});
					board[nx][ny] = board[x][y] + 1;
					visited[nx][ny] = true;
				}
			}
		}
		
		return -1;
		
	}

}
