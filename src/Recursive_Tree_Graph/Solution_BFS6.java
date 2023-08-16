package Recursive_Tree_Graph;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;


public class Solution_BFS6 { // 토마토

	static int M, N, answer = Integer.MIN_VALUE;
	static int[][] box, day;
	static Queue<Point> q = new LinkedList<>();
	static int[] dx = {0,0,-1,1};
	static int[] dy = {-1,1,0,0};
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		M = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		box = new int[N][M];
		day = new int[N][M];
		
		for(int i = 0; i<N; i++) {
			 st = new StringTokenizer(br.readLine());
			for(int j = 0; j<M; j++) {
				box[i][j] = Integer.parseInt(st.nextToken());
				if(box[i][j] == 1) q.add(new Point(i,j));
				
			}
		}
		
		
		BFS(); //BFS 진행
		
		boolean flag = true;
		for(int i = 0; i<N; i++) {
			for(int j = 0; j<M; j++) {
				if(box[i][j] == 0) { //안 익은 토마토가 있으면
					flag = false; //flag를 false로
				}
					
			}
		}
		
		
		if(flag) { // 다 익었으면 (0이 없으면)
			for(int i = 0; i<N; i++) {
				for(int j = 0; j<M; j++) {
					answer = Math.max(answer, day[i][j]); //day 배열에서 최대값을 answer에 저장
				}
			}
			System.out.println(answer); //for문 다 돌고 최댓값 출력
			
		}
		
		else System.out.println(-1); //그렇지 않으면 (토마토가 다 익지 못하는 상황) -1 출력
		
		
	}

	private static void BFS() {
		
		while(!q.isEmpty()) {
			Point now = q.poll(); //1인 지점 꺼내기
			for(int i = 0; i<4; i++) { //사방탐색
				int nx = now.x + dx[i];
				int ny = now.y + dy[i];
				
				if(nx>=0 && nx<N && ny<M && ny>=0 && box[nx][ny] == 0) {
					box[nx][ny] = 1; //1로 저장 (익힌 토마토)
					q.offer(new Point(nx,ny)); //큐에 1인 지점 넣어주기
					day[nx][ny] = day[now.x][now.y]+1; //day배열에 날짜 저장
					
				}
			}
			
		}
	}

}
