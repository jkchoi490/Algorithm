import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;
class Point{
	int x, y;
	public Point(int x, int y) {
		this.x=x;
		this.y=y;
	}
}
public class BAEKJOON2178 {

	static int N,M;
	static int[][] map;
	static int[][] visited;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		visited = new int[N][M];
		for (int i = 0; i < N; i++) {
			String str = br.readLine();
			for(int j = 0; j < M; j++) {
				map[i][j] = (int) str.charAt(j) - '0';
			}
		}
		
		BFS();
		System.out.println(visited[N-1][M-1]);
	}
	public static void BFS() {
		visited[0][0] = 1;
		Queue<Point> q = new LinkedList<>();
		q.offer(new Point(0,0));
		
		while(!q.isEmpty()) {
			Point now = q.poll();
			for(int i = 0; i<4; i++) {
				int nx = now.x + dx[i];
				int ny = now.y + dy[i];
				if(nx>=0 && nx<N && ny>=0 && ny<M && map[nx][ny] == 1 && visited[nx][ny]==0) {
					visited[nx][ny] = visited[now.x][now.y]+1;
					q.offer(new Point(nx, ny));
					
				}
			}
		}
	}


}
