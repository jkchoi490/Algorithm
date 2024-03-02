import java.io.*;
import java.util.*;

public class BAEKJOON1012 {
	
	static int[][] map;
	static ArrayList<Integer> list;
	static int M,N, cnt = 0;
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, 1, 0, -1};
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		for(int tc = 1; tc<=T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			M = Integer.parseInt(st.nextToken());
			N = Integer.parseInt(st.nextToken());
			int K = Integer.parseInt(st.nextToken());
			map = new int[M][N];
			list = new ArrayList<>();
			for(int i = 0; i<K; i++) {
				st = new StringTokenizer(br.readLine());
				int X = Integer.parseInt(st.nextToken());
				int Y = Integer.parseInt(st.nextToken());
				map[X][Y] = 1;
			}
			solve(map);
		}
	}
	
	public static void solve(int[][] map) {
		for(int i = 0; i<M; i++) {
			for(int j = 0; j<N; j++) {
				if(map[i][j] == 1) {
					cnt = 0;
					DFS(i, j);
					list.add(cnt);
				}
			}
		}
		System.out.println(list.size());
	}

	public static void DFS(int x, int y) {
		map[x][y] = 0;
		cnt++;
		
		if(x==M && y==N) {
			return;
		}else {
			for(int d = 0; d<4; d++) {
				int nx = x + dx[d];
				int ny = y + dy[d];
				if(nx>=0 && nx<M && ny>=0 && ny<N && map[nx][ny]==1) {
					DFS(nx, ny);
				}
			}
		}
		
	}

}
