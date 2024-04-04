import java.io.*;
import java.util.*;

class Cloud{
	int x, y;
	public Cloud(int x, int y) {
		this.x=x;
		this.y=y;
	}
}
public class BAEKJOON21610 {

	static int N,M;
	static int[][] map;
	static int[] dx = {0, -1, -1, -1, 0, 1, 1, 1};
	static int[] dy = {-1, -1, 0, 1, 1, 1, 0, -1};
	static Queue<Cloud> clouds = new LinkedList<>();
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		for(int i = 0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j<N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		clouds.add(new Cloud(N-1, 0));
		clouds.add(new Cloud(N-1, 1));
		clouds.add(new Cloud(N-2, 0));
		clouds.add(new Cloud(N-2, 1));
		
		for(int num = 1; num<=M; num++) {
			st = new StringTokenizer(br.readLine());
			int d = Integer.parseInt(st.nextToken())-1;
			int s = Integer.parseInt(st.nextToken());
			move_rain(d, s);
			water();
			cloud();
		}
		int answer = 0;
		for(int i = 0; i<N; i++) {
			for(int j = 0; j<N; j++) {
				answer += map[i][j];
			}
		}
		System.out.println(answer);
	}
	private static void move_rain(int d, int s) {
		for(Cloud cloud : clouds) {
			cloud.x  = (cloud.x + dx[d]*(s%N) + N) % N;
			cloud.y = (cloud.y + dy[d]*(s%N) + N) % N;
			map[cloud.x][cloud.y]++;
		}
		
	}
	
	private static void water() {
		for(Cloud cloud : clouds) {
			int cnt = 0;
			for(int d = 1; d<8; d+=2 ) {
				int nx = cloud.x+dx[d];
				int ny = cloud.y+dy[d];
				if(nx>=0 && nx<N && ny>=0 && ny<N && map[nx][ny]>0) {
					cnt++;
				}
			}
			map[cloud.x][cloud.y] += cnt;
		}
		
	
		
	}
	private static void cloud() {
		boolean[][] visit = new boolean[N][N];
		while(!clouds.isEmpty()) {
			Cloud cloud = clouds.poll();
			visit[cloud.x][cloud.y] = true;
		}
		
		for(int i = 0; i<N; i++) {
			for(int j = 0; j<N; j++) {
				if(visit[i][j]== false && map[i][j]>=2) {
					map[i][j] -= 2;
					clouds.add(new Cloud(i,j));
				}
			}
		}
	}

}
