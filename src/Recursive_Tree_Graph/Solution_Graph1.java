package Recursive_Tree_Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Solution_Graph1 { // 경로탐색(인접리스트, ArrayList)
	static int N,M, answer = 0;
	static ArrayList<ArrayList<Integer>> graph; //2차원 배열로 구현 시 n이 크면 메모리 문제가 발생할 수 있음 -> 인접리스트 사용(갈 수 있는 정점만 탐색하면 된다)
	static int[] ch; //방문처리할 체크배열

	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		graph = new ArrayList<ArrayList<Integer>>();
		for(int i = 0; i<=N; i++) {
			graph.add(new ArrayList<Integer>()); 
		}
		ch = new int[N+1]; 
		for(int i = 0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()); 
			int b = Integer.parseInt(st.nextToken());
			graph.get(a).add(b); //a번 ArrayList에 b를 넣는다
		}
		
/*
 			1 -> 2-3-4
 			2 -> 1-3
 			3 -> 4
 			4 -> 2-5
 			5 ->
 */				
		ch[1] = 1; //시작 지점 방문처리
		DFS(1);
		System.out.println(answer);

	}

	public static void DFS(int v) {
		if(v == N) answer++; // N에 도착하면 카운트
		else {
			for(int nv : graph.get(v)) { //v번 ArrayList에서 방문할 정점 하나씩 꺼내옴
				if(ch[nv] == 0) { // 방문하지 않았으면
					ch[nv] = 1; //방문처리
					DFS(nv);
					ch[nv] = 0; //방문처리 다시 풀어주기
				}
			}
		}
		
	}

}
