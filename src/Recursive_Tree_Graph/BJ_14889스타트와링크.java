package Recursive_Tree_Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_14889스타트와링크 { 

	static int n, ans = Integer.MAX_VALUE;
	static int[][] graph;
	static boolean[] visited;
	
	public static void DFS(int L, int s) {
		if(L==n/2) {
			diff();
			return;
		}else {
			for(int i = s; i<n; i++) {
				visited[i] = true;
				DFS(L+1, i+1);
				visited[i] = false;
			}
		}
	}
	
	public static void diff() {
		int start = 0;
		int link = 0;
		for(int i = 0; i<n-1; i++) {
			for(int j = i; j<n; j++) {
				if(visited[i] == true && visited[j] == true) {
					start += graph[i][j];
					start += graph[j][i];
				}
				else if(visited[i]==false && visited[j]==false) {
					link += graph[i][j];
					link += graph[j][i];
				}
			}
		}
		int value = Math.abs(start-link);
		ans = Math.min(value, ans);
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		graph = new int[n][n];
		StringTokenizer st;
		for(int i = 0; i<n; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j =0; j<n; j++) {
				graph[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		visited = new boolean[n];
		DFS(0,0);
		System.out.println(ans);
	}

}
