import java.io.*;
import java.util.*;

class Point{
	int x, y;
	public Point(int x, int y) {
		this.x=x;
		this.y=y;
	}
}
public class BAEKJOON16234 {
	static int N,L,R, sum =0;
	static int[][] map;
	static boolean[][] visited;
	static boolean isMove;
	static ArrayList<Point> list = new ArrayList<>();
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, 1, 0, -1};
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		
		
		for(int i = 0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j<N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		int answer = 0;
		while(answer <= 2000) {
			isMove = false;
			visited = new boolean[N][N];

			for(int i = 0; i<N; i++) {
				for(int j = 0; j<N; j++) {
					if(visited[i][j] == false) {
						BFS(i,j);
					}
				}
			}
	
			
			if(!isMove) break;
			else answer++;
		}
		System.out.println(answer);
	}

	private static void BFS(int x, int y) {
		Queue<Point> Q = new LinkedList<>();
		Q.offer(new Point(x, y));
		visited[x][y] = true;
		list.add(new Point(x,y));
		
		while(!Q.isEmpty()) {
			Point now = Q.poll();
			for(int d = 0; d<4; d++) {
				int nx = now.x+dx[d];
				int ny = now.y+dy[d];
				if(nx>=0 && nx<N && ny>=0 && ny<N && visited[nx][ny] == false) { 
				int diff = Math.abs(map[now.x][now.y] - map[nx][ny]); 		
				if( diff>=L && diff<=R) {		
					isMove = true;
					visited[nx][ny] = true;
					Q.offer(new Point(nx, ny));
					list.add(new Point(nx, ny));
				
					}
				}
			}
		}
		
		
		
		int sum = 0;
		for(Point p : list) {
				sum += map[p.x][p.y];
		}
		for(Point p : list) {
				map[p.x][p.y] = sum/list.size();
		}
		
		list = new ArrayList<>();
		
	}

}
