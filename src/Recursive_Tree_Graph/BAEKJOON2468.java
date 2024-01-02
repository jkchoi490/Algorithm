import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

public class BAEKJOON2468 {
	static int n,max_height,areasize,ans=Integer.MIN_VALUE;
	static int cnt=0;
	static int[][] graph;
	static boolean[][] visited;
	static ArrayList<Integer> area;
	static int[] dx = {0,0,-1,1};
	static int[] dy = {-1,1,0,0};

	public static void DFS(int height, int x, int y) {
		visited[x][y] = true;
		
		for(int i = 0; i<4; i++) {
			int nx = x + dx[i];
			int ny = y + dy[i];
			if(nx>=0 && ny>=0 && nx<n && ny<n && !visited[nx][ny] && graph[nx][ny]>=height) {
				visited[nx][ny] = true;
				DFS(height,nx,ny);
				}
			}
		}
	
	public static int countingArea() {
		
		for(int h = 1; h<=max_height; h++) {
			int cnt = 0;
			visited = new boolean[n][n];
			for(int i = 0; i<n; i++) {
				for(int j = 0; j<n; j++) {
					if(!visited[i][j] && graph[i][j]>=h) {
						cnt++;
						DFS(h,i,j);
					}
				}
			}
			area.add(cnt);
		}
		Collections.sort(area);
		ans = area.get(area.size()-1);
		return ans;
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		graph = new int[n][n];
		visited = new boolean[n][n];
		StringTokenizer st;
		for(int i = 0; i<n; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j<n; j++) {
				graph[i][j]= Integer.parseInt(st.nextToken());
				max_height = Math.max(max_height, graph[i][j]);
			}
		}
		area = new ArrayList<>();
		
		countingArea();
		System.out.println(ans);

	}


}
