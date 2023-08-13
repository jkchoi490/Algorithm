package Recursive_Tree_Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution_BFS4 { // 그래프 최단거리(BFS)
	static int N, M;
	static ArrayList<ArrayList<Integer>> graph;
	static int[] ch; //방문여부 체크 배열
	static int[] dis; //1번 정점에서 각 정점으로 가는 최소 간선수 저장

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
		dis = new int[N+1];
		for(int i = 0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			graph.get(a).add(b);
		}
		
		BFS(1);
		for(int i = 2; i<=N; i++) {
			System.out.println(i+" "+dis[i]);
		}

	}

	public static void BFS(int v) {
		ch[v] = 1; // v 정점 방문
		dis[v] = 0; // 간선수 0으로 초기화
		Queue<Integer> q = new LinkedList<>(); // 큐 생성
		q.offer(v); // v 정점 큐에 넣기
		while(!q.isEmpty()) {
			int cv = q.poll(); //정점 꺼내기
			for(int nv : graph.get(cv)) { //현재 정점의 인접리스트에서 다음 정점 꺼내옴
				if(ch[nv] == 0) { //방문 안했으면
					ch[nv] = 1; //방문처리해주고
					q.offer(nv); // 큐에 다음 정점 넣어준다
					dis[nv] = dis[cv] + 1; // nv까지의 거리는 cv까지 거리+1
				}
			}
		}
		
	}

}
