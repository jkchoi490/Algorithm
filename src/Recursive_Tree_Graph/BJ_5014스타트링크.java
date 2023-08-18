package Recursive_Tree_Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BJ_5014스타트링크 {
	
	static int F,S,G,U,D,min = Integer.MAX_VALUE;
	static int ans = -1;
	
	public static int BFS(int F, int S, int G, int U, int D){
	int cnt = -1;
	boolean[] visited = new boolean[F+1]; //방문체크할 배열
	Queue<Integer> q = new LinkedList<>();
	q.add(S); //현재 위치
	visited[S] = true; //현재 위치 방문처리
	
	while(!q.isEmpty()){
		int size = q.size();
		++cnt;
		while(size-- > 0){
			int now = q.poll();
			if(now == G) return cnt; // G층에 도착하면  최소 버튼 수 리턴
			if(now+U <= F && !visited[now+U]){ //위로 U층 이동 : F층보다 아래이고 방문을 안했으면
				q.add(now+U); 
				visited[now+U] = true; //방문처리
			}
			if(now-D > 0 && !visited[now-D]){ // 아래로 D층 이동 : 0보다 크고 방문 안했으면
				q.add(now-D);
				visited[now-D] = true; //방문 처리
			    }
		    }
	    }
        return -1;
    }
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		F = Integer.parseInt(st.nextToken());
		S = Integer.parseInt(st.nextToken());
		G = Integer.parseInt(st.nextToken());
		U = Integer.parseInt(st.nextToken());
		D = Integer.parseInt(st.nextToken());
		
		ans = BFS(F,S,G,U,D);
		if(ans>=0) System.out.println(ans);
		else System.out.println("use the stairs"); 
		
	}


}
