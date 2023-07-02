package Recursive_Tree_Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
class Solution_Recursive10 {
	static int N,M,ans = 0;
	static int[][] graph;
	static int[] ch;
	
	private static void DFS(int v) {
		if(v == N) ans++;
		else {
			for(int i = 1; i<=N; i++) {//정점의 개수만큼 돈다
				if(graph[v][i] == 1 && ch[i]==0) {//정점으로 갈 수 있고(간선으로 연결되어있고) 방문을 안했으면
				ch[i] = 1; // i 정점을 방문처리
				DFS(i);//v에서 i로 이동하는 것이므로 DFS(i);
				ch[i] = 0;//방문처리된거 풀어주기 (ch 풀어주기)
				}
			}
		}
		
	}
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // 정점의 수
		M = Integer.parseInt(st.nextToken());// 간선의 수
		graph = new int[N+1][N+1];
		ch = new int[N+1];
		for(int i = 0; i<M; i++) {//for문이 간선개수만큼 돈다
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			graph[a][b] = 1;
		}
		ch[1] = 1;
		DFS(1);
		System.out.println(ans);
	}

}