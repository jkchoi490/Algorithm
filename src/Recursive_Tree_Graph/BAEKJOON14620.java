import java.io.*;
import java.util.*;

class Point{
	int x, y;
	public Point(int x, int y) {
		this.x=x;
		this.y=y;
	}
}
public class BAEKJOON14620 {

	static int n, answer = Integer.MAX_VALUE;
	static int[][] map;
	static ArrayList<Point> list;
	static Point[] combi;
	static boolean[] visited;
	static int[] dx = {-1, 0, 1, 0};
	static int[] dy = {0, 1, 0, -1};
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		map = new int[n][n];
		list = new ArrayList<>();
		for(int i = 0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int j = 0; j<n; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(i>=1 && i<n-1 && j>=1 && j<n-1) {
					list.add(new Point(i,j));
				}
			}
		}
		visited = new boolean[list.size()];
		combi = new Point[3];
		DFS(0);
		System.out.println(answer);

	}
	
	public static void DFS(int L) {
		if(L==3) {
			solve(combi);
			return;
		}else {
			for(int i = 0; i<list.size(); i++) {
				if(!visited[i]) {
					visited[i] = true;
					combi[L] = list.get(i);
					DFS(L+1);
					visited[i] = false;
				}
			}
		}
		
	}

	public static void solve(Point[] combi) {
		boolean[][] visit = new boolean[n][n];
		boolean flag = false;
		int sum = 0;
		for(Point p : combi) {
			visit[p.x][p.y] = true;
			sum += map[p.x][p.y];
			for(int d = 0; d<4; d++) {
				int nx = p.x+dx[d];
				int ny = p.y+dy[d];
				if(nx>=0 && nx<n && ny>=0 && ny<n && visit[nx][ny]) {
					flag = true;
					//break;
				}
				if(nx>=0 && nx<n && ny>=0 && ny<n && !visit[nx][ny]) {
					visit[nx][ny] = true;
					sum += map[nx][ny];
				}
			}
		}
		
		int min = Integer.MAX_VALUE;
		if(!flag) {
			min = Math.min(min, sum);
			answer = Math.min(answer, min);
		}
		
		
	}

}
