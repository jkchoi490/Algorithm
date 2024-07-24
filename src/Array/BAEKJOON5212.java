import java.io.*;
import java.util.*;

public class BAEKJOON5212 {

	static int R, C;
	static char[][] map, newMap;
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, 1, 0, -1};
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		map = new char[R][C];
		newMap = new char[R][C];
	
		for(int i = 0; i<R; i++) {
			String str = br.readLine();
			for(int j = 0; j<C; j++) {
				map[i][j] = str.charAt(j);
				newMap[i][j] = map[i][j];
			}
		}
		
		for(int i = 0; i<R; i++) {
			for(int j = 0; j<C; j++) {
				if(map[i][j]=='X') {
					melt(i, j);
				}
			}
		}
		
		print();
	}
	
	private static void print() {
		int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE, 
				maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
		for(int i = 0; i<R; i++) {
			for(int j = 0; j<C; j++) {
				if(newMap[i][j] == 'X') {
					minX = Math.min(minX, i);
					minY = Math.min(minY, j);
					maxX = Math.max(maxX, i);
					maxY = Math.max(maxY, j);
				}
			}
		}
		StringBuilder sb = new StringBuilder();
		for(int i = minX; i<=maxX; i++) {
			for(int j = minY; j<=maxY; j++) {
				sb.append(newMap[i][j]);
			}
			sb.append("\n");
		}
		
		System.out.println(sb.toString());
	}

	private static void melt(int x, int y) {
		
		int cnt = 0;
		
		for(int d = 0; d<4; d++) {
			int nx = x + dx[d];
			int ny = y + dy[d];

			
			if(nx>=0 && nx<R && ny>=0 && ny<C && map[nx][ny] == '.') {
				cnt++;
				
			}
			if(nx<0 || nx>=R || ny<0 || ny>=C) {
				cnt++;
			}
		}
		
		if(cnt >= 3) {
			newMap[x][y] = '.';
		}
		
	}

}
