import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;


public class BAEKJOON2583 {
	static int m,n,k,count = 0;
	static int[][] graph;
	static ArrayList<Integer> arealist;
	static int[] dx = {0,0,-1,1};
	static int[] dy = {-1,1,0,0};

	public static void DFS(int x,int y) {
		graph[x][y] =1;
		count++;
		if(x == m && y == n) return;
		else {
			for(int i = 0; i<4; i++) {
				int nx = x + dx[i];
				int ny = y + dy[i];
				if(nx>=0 && ny>=0 && nx<m && ny<n && (graph[nx][ny] == 0)) {
					DFS(nx, ny);
				
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		m = Integer.parseInt(st.nextToken());
		n = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		graph = new int[m][n];
	
		for(int i = 0; i<k; i++) {
			st = new StringTokenizer(br.readLine());
			int y1 = Integer.parseInt(st.nextToken());
			int x1 = Integer.parseInt(st.nextToken());
			int y2 = Integer.parseInt(st.nextToken());
			int x2 = Integer.parseInt(st.nextToken());
			
			for(int a = y1; a<y2; a++) {
				for(int b = x1; b<x2; b++) {
					graph[b][a] = 1;
				}
			}
		}
		
		arealist = new ArrayList<Integer>();
		
		for(int i = 0; i<m; i++) {
			for(int j = 0; j<n; j++) {
				if(graph[i][j] == 0) {
					count = 0;
					DFS(i,j);
					arealist.add(count);
				}
			}
		}

		System.out.println(arealist.size());
		
		Collections.sort(arealist);
		for(int x : arealist) System.out.print(x +" ");
	}
	

	
}
