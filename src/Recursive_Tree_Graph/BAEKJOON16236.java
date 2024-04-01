import java.io.*;
import java.util.*;

public class BAEKJOON16236 {

	static int N, bx, by; 
	static int size = 2;
	static boolean stop = false; 
	static boolean eat = false; 
	static int cnt = 0; 
	static int result = 0;
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, 1, 0, -1};
	static int[][] map;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		map = new int[N][N];
		StringTokenizer st;
		for(int i = 0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j<N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j] == 9) {
					bx = i;
					by = j;
					map[i][j] = 0;
				}
			}
		}
		
		while(!stop) {
			boolean[][] visited = new boolean[N][N];
			BFS(bx, by, visited, size);
			if(eat) {
				eat = false;
				cnt++; 
				map[bx][by] = 0; 
				if(cnt == size) { 
					size += 1; 
					cnt = 0; 
				}
				
			}
			else {
				stop = true; 
			}
		}
		System.out.println(result);
		
	}
	public static void BFS(int a, int b, boolean[][] visited, int shark_size) {
		Queue<int[]> q = new LinkedList<>();
		q.offer(new int[] {a, b, 0});
		visited[a][b] = true;
		int temp = 0; 
		while(!q.isEmpty()) {
			int[] now = q.peek();
			int x = now[0];
			int y = now[1];
			int time = now[2]; 
			
			if(map[x][y] >0 && map[x][y] < shark_size && temp == time) {
				if((bx > x)||(bx == x && by > y)) {
					by = y; 
					bx = x;
					continue;
				}
			}
			q.poll();
			for(int i = 0; i<4; i++) {
				int nx = x + dx[i];
				int ny = y + dy[i];
				if(nx>=0 && nx<N && ny>=0 && ny<N && !visited[nx][ny]) {
					if(map[nx][ny] <= shark_size) {
						if(map[nx][ny] >0 && map[nx][ny] < shark_size && !eat) {
							eat = true;
							bx = nx; 
							by = ny;
							temp = time+1;
							result += temp; 
						}else {
							q.offer(new int[] {nx, ny, time+1});
							visited[nx][ny] = true;
						}
					}
				}
			}
		}
		
	}

}
