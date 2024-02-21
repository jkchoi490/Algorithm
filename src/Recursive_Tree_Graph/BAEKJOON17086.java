import java.io.*;
import java.util.*;

class Shark{
	int x, y;
	public Shark(int x, int y) {
		this.x=x;
		this.y=y;
	}
}
public class BAEKJOON17086 {
	
	static int N, M, answer = 0;
	static int[][] map;
	static Queue<Shark> q;
	static int[] dx = {-1, 0, 1, 0, -1, -1, 1, 1};
	static int[] dy = {0, 1, 0, -1, 1, -1, 1, -1};
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		q = new LinkedList<>();
		for(int i = 0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j<M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j] == 1) q.offer(new Shark(i,j));
			}
		}
		BFS();
		System.out.println(answer-1);
	}
	
	public static void BFS() {
		while(!q.isEmpty()) {
			Shark now = q.poll();
			for(int d = 0; d<8; d++) {
				int nx = now.x+dx[d];
				int ny = now.y+dy[d];
				if(nx>=0 && nx<N && ny>=0 && ny<M && map[nx][ny]==0) {
					map[nx][ny] = map[now.x][now.y]+1;
					answer = Math.max(answer, map[nx][ny]);
					q.offer(new Shark(nx, ny));
				}
			}
		}
		
	}

}
