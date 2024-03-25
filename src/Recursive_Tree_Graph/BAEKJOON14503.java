import java.io.*;
import java.util.*;

public class BAEKJOON14503 {

	static int N,M,r,c,d,cnt=0;
	static boolean flag;
	static int[][] map;
	static boolean[][] visited;
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, 1, 0, -1};
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st=  new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		st =  new StringTokenizer(br.readLine());
		r = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		d = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		visited= new boolean[N][M];
		for(int i = 0; i<N; i++) {
			st =  new StringTokenizer(br.readLine());
			for(int j = 0; j<M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		while(true) {
			if(visited[r][c] == false) { 
				visited[r][c] = true;
				cnt++;
			}
			
			boolean flag = false;
			for(int i = 0; i<4; i++) {
				d = (d+3) %4;
				int nx = r + dx[d];
				int ny = c + dy[d];
				if(map[nx][ny] == 0 && visited[nx][ny] == false) {
					r += dx[d];
					c += dy[d]; 
					flag = true; 
					break;
				}
			}
			
			if(!flag) {
				int back = (d+2)%4;
				if(map[r+dx[back]][c+dy[back]] == 1) break;
				r += dx[back];
				c += dy[back];
			}
		}
		System.out.println(cnt);
	}
	
}
