package Recursive_Tree_Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_DFS2 { // 경로탐색(DFS) 
	static int N, M, answer = 0;
	static int[][] graph; //방향 그래프
	static int[] ch;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		graph = new int[N+1][N+1];
		ch = new int[N+1];
		
		for(int i = 0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			graph[a][b] = 1; // a, b 연결
		}
		ch[1] = 1; //첫번째 지점 방문 처리
		DFS(1);
		System.out.println(answer);
	}

	private static void DFS(int v) {
		if(v==N) answer++;
		else {
			for(int i = 1; i<=N; i++) {
				if(graph[v][i] == 1 && ch[i] == 0) { //갈수 있는 노드고 방문을 안했으면
					ch[i] = 1; //방문처리
					DFS(i); //v-> i 이므로  DFS(i)
					ch[i] = 0; //back해서 i로 돌아가므로 방문처리 풀어주기
				}
			}
		}
	}

}
