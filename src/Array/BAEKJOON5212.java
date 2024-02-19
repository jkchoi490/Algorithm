import java.awt.Point;
import java.io.*;
import java.util.*;

public class BAEKJOON5212 {
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, 1, 0, -1};

	public static void main(String[] args) throws IOException{

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int R = Integer.parseInt(st.nextToken());
		int C = Integer.parseInt(st.nextToken());
		char[][] map = new char[R][C];
		int[][] ch = new int[R][C];
		Queue<Point> q  = new LinkedList<>();
		for(int i = 0; i<R; i++) {
			String str = br.readLine();
			for(int j = 0; j<C; j++) {
				map[i][j] = str.charAt(j);
				if(map[i][j] == 'X') {
					q.offer(new Point(i,j));
				}
			}
		}
		
		while(!q.isEmpty()) {
			Point now = q.poll();
			for(int d = 0; d<4; d++) {
				int nx = now.x + dx[d];
				int ny = now.y + dy[d];
				if(nx<0 || nx>=R || ny<0 || ny>=C ||map[nx][ny]=='.') {
					ch[now.x][now.y]++;
			}
			}
		}
		int min_r = Integer.MAX_VALUE, min_c = Integer.MAX_VALUE, max_r= 0, max_c = 0;
		for(int i = 0; i<R; i++) {
			for(int j = 0; j<C; j++) {
				if(ch[i][j]>0 && ch[i][j]<3) {
					min_r = Math.min(min_r, i);
					max_r = Math.max(max_r, i);
					min_c =  Math.min(min_c, j);
					max_c =  Math.max(max_c, j);
				}
			}
		}
		char[][] result = new char[max_r][max_c];
		for(int i = min_r; i<=max_r; i++) {
			for(int j = min_c; j<=max_c; j++) {
				if(ch[i][j]>0 && ch[i][j]<3) result[i-1][j-1] = 'X';
				else if(ch[i][j]==0 || ch[i][j]>=3) result[i-1][j-1] = '.';
			}
		}
		StringBuilder sb = new StringBuilder();
		for(int i = min_r; i<=max_r; i++) {
			for(int j = min_c; j<=max_c; j++) {
				sb.append(result[i-1][j-1]);
			}
			sb.append("\n");
		}
		System.out.println(sb);

		
	}

} //런타임 에러 (ArrayIndexOutOfBounds)
