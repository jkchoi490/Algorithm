import java.io.*;
import java.util.*;

class Dust{
	int x, y, size;
	public Dust(int x, int y, int size){
		this.x=x;
		this.y=y;
		this.size=size;
	}
}
public class BAEKJOON17144 {
	static int R,C,T;
	static int[][] map;
	static ArrayList<Dust> cleaner;
	static Queue<Dust>dusts;
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, 1, 0, -1};
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		T = Integer.parseInt(st.nextToken());
		cleaner = new ArrayList<>();
		dusts = new LinkedList<>();
		map = new int[R][C];
		for(int i = 0; i<R; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j<C; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j] == -1) cleaner.add(new Dust(i,j,map[i][j]));
				if(map[i][j] > 0) dusts.add(new Dust(i,j,map[i][j]));
			}
		}
		
		while(T-- > 0) {
			spreadDust();
			airCirculation();
		}
		int sum = 0;
		for(int i = 0; i<R; i++) {
			for(int j = 0; j<C; j++) {
				if(map[i][j] > 0) sum += map[i][j];
				//System.out.print(map[i][j]+" ");
			}
			//System.out.println();
		}
		System.out.println(sum);
	}
	public static void spreadDust() {
		int cnt = 0;
		int spread_size = 0;
		while(!dusts.isEmpty()) {
			cnt = 0;
			spread_size = 0;
			Dust dust = dusts.poll();
			int now_x = dust.x;
			int now_y = dust.y;
			int now_size = dust.size;
			for(int d = 0; d<4; d++) {
				int nx = now_x + dx[d];
				int ny = now_y + dy[d];
				spread_size = now_size/5;
				if(nx>=0 && nx<R && ny>=0 && ny<C && map[nx][ny] == -1) continue;
				if(nx<0 || nx>=R || ny<0 || ny>=C) continue;
				if(nx>=0 && nx<R && ny>=0 && ny<C) {
					map[nx][ny] += spread_size;
					cnt++;
				}
			}
			map[now_x][now_y] -= (spread_size)*cnt;
		}
		
	}

	public static void airCirculation() {

	}
		
	

}
