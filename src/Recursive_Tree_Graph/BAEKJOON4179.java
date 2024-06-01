import java.io.*;
import java.util.*;

public class BAEKJOON4179 {

	static int R,C;
	static char[][] map;
	static Queue<int[]> jQ, fQ;
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, 1, 0, -1};
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		map = new char[R][C];
		jQ = new LinkedList<>();
		fQ = new LinkedList<>();
		for(int i = 0; i<R; i++) {
			String line = br.readLine();
			for(int j = 0; j<C; j++) {
				map[i][j] = line.charAt(j);
				if(map[i][j] == 'J') jQ.offer(new int[] {i,j,0});
				else if(map[i][j] == 'F') fQ.offer(new int[] {i,j});
			}
		}
		
		BFS();
	}
	
	public static void BFS() {
		boolean[][] visited = new boolean[R][C];
		while(!jQ.isEmpty()) {

			int fireSize = fQ.size();
			for(int i = 0; i<fireSize; i++) {
			int[] fire = fQ.poll();
			for(int d = 0; d<4; d++) {
				
				int fx = fire[0]+dx[d];
				int fy = fire[1]+dy[d];

				if(fx>=0 && fx<R && fy>=0 && fy<C && map[fx][fy]=='.') {
					map[fx][fy] = 'F';
					fQ.offer(new int[] {fx, fy});
					}
				}
			}
			
			int jsize = jQ.size();
			for(int i = 0; i<jsize; i++) {
				int[] cur = jQ.poll();
				for(int d = 0; d<4; d++) {
					int nx = cur[0]+dx[d];
					int ny = cur[1]+dy[d];
				
					int time = cur[2];
				
					if(nx<0 || nx>=R || ny<0 || ny>=C) {
						System.out.println(time+1);
						return;
					}
					if(nx>=0 && nx<R && ny>=0 && ny<C && map[nx][ny]=='#') continue;
					if(nx>=0 && nx<R && ny>=0 && ny<C && !visited[nx][ny] && map[nx][ny]=='.') {
						map[cur[0]][cur[1]] = '.';
						visited[nx][ny] = true;
						jQ.offer(new int[] {nx, ny, time+1});
					}
					
				}
			}
			
		}
		
		
		
		System.out.println("IMPOSSIBLE");
		
	}

}
