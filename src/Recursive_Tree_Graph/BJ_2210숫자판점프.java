package Recursive_Tree_Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BJ_2210숫자판점프 {
	
	static String[][] graph;
	static int[] dx = {0,0,-1,1};
	static int[] dy = {-1,1,0,0};
	static ArrayList<String> arrlist = new ArrayList<>();
	public static void DFS(int x, int y, int cnt, String num) {

		if(cnt == 5) {
			if(!arrlist.contains(num)) arrlist.add(num);
			return;
		}else {
			for(int i = 0; i<4; i++) {
				int nx = x+dx[i];
				int ny = y+dy[i];
				
				if(nx>= 0 && nx<5 && ny>=0 && ny<5) {
					DFS(nx,ny,cnt+1, num+graph[nx][ny]);
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		graph = new String[5][5];
		StringTokenizer st;
		for(int i = 0; i<5; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j<5; j++) {
				graph[i][j] = st.nextToken();
			}
		}

		for(int i = 0; i<5; i++) {
			for(int j = 0; j<5; j++) {
				DFS(i,j,0,graph[i][j]);
			}
		}
		
		System.out.println(arrlist.size());


	}

}
