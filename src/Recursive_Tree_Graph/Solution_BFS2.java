package Recursive_Tree_Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution_BFS2 { //  송아지 찾기1(BFS : 상태트리탐색) -> 최단거리 구하기
	
	static int S, E;
	static int[] dis = {1, -1, 5}; //스카이 콩콩
	static int[] ch; //방문한 곳 체크 
	static Queue<Integer> q = new LinkedList<>(); // BFS를 위한 큐 생성
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		S = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		System.out.println(BFS(S,E));
	}

	public static int BFS(int s, int e) {
		ch = new int[10001]; //1부터 10000사이므로 10001을 크기로 함
		ch[s] = 1; // s 방문 후  1로 체크
		q.offer(s); // 큐에 s 삽입
		int L = 0;  //점프를 해서 몇번만에 갈수있는지 (깊이)
		
		while(!q.isEmpty()) {
			int len = q.size();
			for(int i = 0; i<len; i++) { //큐 사이즈만큼 for loop
				int x = q.poll(); // 큐에서 꺼냄
				for(int j = 0; j<3; j++) {
					int nx = x + dis[j]; // 
					if(nx == e) {
						return L+1; 
					}
					if(nx>=1 && nx<=10000 && ch[nx]==0){ //1부터 10000사이에 nx가 있고 방문 안했으면
						ch[nx]=1; //방문 체크하고 
						q.offer(nx); //큐에 nx 넣기
					}
				}
			}
			L++; 
		}
 		return 0;
	}

}
