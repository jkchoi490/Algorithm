import java.io.*;
import java.util.*;

public class BAEKJOON20058 {

	static int N, Q, len;
	static int[][] map;
	static int[] L;
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, 1, 0, -1};
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());
		len = (int) Math.pow(2, N);
		map = new int[len][len];
		for(int i = 0; i<len; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j<len; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		st = new StringTokenizer(br.readLine());
		L = new int[Q];
		for(int i = 0; i<Q; i++) L[i] = Integer.parseInt(st.nextToken());
	
		for(int i = 0; i<Q; i++){
			rotate(L[i]);
			decrease();
		}
		
		int max = 0;
		int sum = 0;
		for(int i = 0; i<len; i++) {
			for(int j = 0; j<len; j++) {
				if(map[i][j] > 0) {
					sum += map[i][j];
					max = Math.max(max, BFS(i,j));
				}
			}
			
		}
		System.out.println(sum);
		System.out.println(max);
	}
	
	private static void rotate(int L) {
		int[][] newMap = new int[len][len];
		L = (int) Math.pow(2, L);
		for(int x = 0; x<len; x+=L) {
			for(int y = 0; y<len; y+=L) {
				for(int i = 0; i<L; i++) {
					for(int j = 0; j<L; j++) {
						newMap[x+i][y+j] = map[x+L-1-j][y+i];
					}
				}
			}
		}
		map = newMap;
		
	}
	
	private static void decrease() {
		int[][] newMap = new int[len][len];
		
		for(int i = 0; i<len; i++) {
			for(int j = 0; j<len; j++) {
				newMap[i][j] = map[i][j];
			}
		}
		for(int i = 0; i<len; i++) {
			for(int j = 0; j<len; j++) {
				int cnt = 0;
				for(int d = 0; d<4; d++) {
					int nx = i + dx[d];
					int ny = j + dy[d];
					if(nx>=0 && nx<len && ny>=0 && ny<len && map[nx][ny]>0) {
						cnt++;
					}
				}
				if(cnt<3) {
					newMap[i][j]--;
				}
			}
		}
		
		map = newMap;
		
	}
	
	private static int BFS(int x, int y) {
		Queue<int[]> q = new LinkedList<>();
		boolean[][] visited = new boolean[len][len];
		q.offer(new int[] {x, y});
		visited[x][y] = true;
		int cnt = 1;
		while(!q.isEmpty()) {
			int[] cur = q.poll();
			for(int d = 0; d<4; d++) {
				int nx = cur[0]+dx[d];
				int ny = cur[1]+dy[d];
				if(nx>=0 && nx<len && ny>=0 && ny<len && map[nx][ny]>0
						&&!visited[nx][ny]) {
					cnt++;
					visited[nx][ny] = true;
					q.offer(new int[] {nx, ny});
				}
			}
		}
		return cnt;
		
		
	}

}
