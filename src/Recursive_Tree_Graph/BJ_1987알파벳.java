package Recursive_Tree_Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_1987알파벳 {  //알파벳
	//백트래킹,DFS 문제
	static int R,C, ans = 0; 
	static int[][] graph;
	static int[] dx = {0,0,-1,1};
	static int[] dy = {-1,1,0,0};
	static boolean[] checked; //백트래킹을 위해서
	
	public static void DFS(int x, int y, int cnt) {
		
		if(checked[graph[x][y]]) {
			ans = Math.max(ans, cnt);
			return;
		}
		else {
			for(int i = 0; i<4; i++) {
				int nx = x + dx[i];
				int ny = y + dy[i];
				if(nx>=0 && ny>=0 && nx<R && ny<C) {
					checked[graph[x][y]] = true;
					DFS(nx, ny, cnt+1);
					checked[graph[x][y]] = false;
				}
			}
			
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		graph = new int[R][C];
		checked = new boolean[R*C];
		for(int i = 0; i<R; i++) {
			String str = br.readLine();
			for(int j = 0; j<C; j++) {
				graph[i][j] = str.charAt(j) - 'A'; //char -> int 형변환
			}
		}
		DFS(0,0,0);
		System.out.println(ans);
	}

}
