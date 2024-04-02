import java.awt.Point;
import java.io.*;
import java.util.*;

public class BAEKJOON14502 {

	static int N, M, ans = 0;
	static int[][] graph;
	static int[][] infectedmap;
	static int[] dx = {0,0,-1,1};
	static int[] dy = {-1,1,0,0};
	static ArrayList<Point> virus;
	
	public static void setupWall(int cnt) {
		if(cnt == 3) { 
			BFS();
			return;
		}else {
			for(int i = 0; i<N; i++) {
				for(int j = 0; j<M; j++) {
					if(graph[i][j] == 0) {
						graph[i][j] = 1;
						setupWall(cnt+1);
						graph[i][j] = 0;
					}
				}
			}
		
		
		}
	}
	
	private static void BFS() {
		infectedmap = new int[N][M];
		for(int i = 0; i<N; i++) {
			System.arraycopy(graph[i], 0, infectedmap[i], 0, M);
		}
		Queue<Point> q = new LinkedList<>();
		for(int i = 0; i<virus.size(); i++) {
			Point v = virus.get(i);
			q.add(new Point(v.x, v.y));
			while(!q.isEmpty()) {
				Point now = q.poll();
				for(int d = 0; d<4; d++) {
					int next_x = now.x+dx[d];
					int next_y = now.y+dy[d];
					if(next_x < 0 || next_y<0 || next_x>=N || next_y>=M) continue;
					if(infectedmap[next_x][next_y] == 1 || infectedmap[next_x][next_y] == 2) continue;
					infectedmap[next_x][next_y] = 2;
					q.add(new Point(next_x, next_y));
				}
			}
		}
		int sum = 0;
		for(int x = 0; x<N; x++) {
			for(int y = 0; y<M; y++) {
				if(infectedmap[x][y] == 0) sum +=1;
			}
		}
		ans = Math.max(ans, sum);
		
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		graph = new int[N][M];
	
		virus = new ArrayList<>();
		for(int i = 0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j<M; j++) {
				graph[i][j] = Integer.parseInt(st.nextToken());
				if(graph[i][j] == 2) virus.add(new Point(i,j));
			}
		}
	
		setupWall(0);
		
		System.out.println(ans);
	}


}
