import java.io.*;
import java.util.*;

class Virus{
	int x, y, time;
	public Virus(int x, int y, int time) {
		this.x=x;
		this.y=y;
		this.time=time;
	}
}
public class BAEKJOON17142 {
	static int N, M, answer = Integer.MAX_VALUE;
	static int emptySpace = 0;
	static int[][] map;
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, 1, 0, -1};
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
				if(map[i][j] == 0) emptySpace++;
			}
		}
		
		if(emptySpace == 0) System.out.println(0);
		else {
			DFS(0);
			System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);	
		}
		

	}
	public static void DFS(int cnt) {
		if(cnt == M) {
			spreadVirus(emptySpace);
			return;
		}else {
			for(int i = 0; i<N; i++) {
				for(int j = 0; j<N; j++) {
					if(map[i][j] == 2) {
						map[i][j] = -1;
						DFS(cnt+1);
						map[i][j] = 2;
					}
				}
			}
		}
		
	}
	
	public static void spreadVirus(int emptySpace) {
		Queue<Virus> q = new LinkedList<>();
		boolean[][] infected = new boolean[N][N];
		ArrayList<Virus> virus = new ArrayList<>();
		for(int i = 0; i<N; i++) {
			for(int j = 0; j<N; j++) {
				if(map[i][j] == -1) {
					q.offer(new Virus(i,j,0));
					infected[i][j] = true;
				}
				if(map[i][j] == 2) virus.add(new Virus(i,j,0));
			}
		}

		while(!q.isEmpty()) {
				Virus now = q.poll();
				for(int d = 0; d<4; d++) {
					int nx = now.x+dx[d];
					int ny = now.y+dy[d];
					
					if(nx<0 || nx>=N || ny<0 || ny>=N) continue;
					if(infected[nx][ny]==true || map[nx][ny] ==1) continue;		

					if(map[nx][ny] == 0) emptySpace--;
					
					if(emptySpace == 0) {
						answer = Math.min(answer, now.time+1);
						return;
					}
					infected[nx][ny] = true;
					q.offer(new Virus(nx, ny, now.time+1));	
			
				}
		}
		
	
	}

}
