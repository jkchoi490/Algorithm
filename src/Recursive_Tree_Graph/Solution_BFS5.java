package Recursive_Tree_Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Point{
	public int x, y;
	Point(int x, int y){
		this.x=x;
		this.y=y;
	}
}

public class Solution_BFS5 { // 미로의 최단거리 통로(BFS)

	static int[][] map,dis;//dis:움직인 칸의 수를 저장할 배열
	static int[] dx = {0,0,-1,1};
	static int[] dy = {-1,1,0,0};

	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		map = new int[8][8];
		dis = new int[8][8];
		for(int i = 1; i<=7; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 1; j<=7; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		BFS(1,1);
		if(dis[7][7] == 0) System.out.println(-1); //도착할 수 없으면 (도착지점에 움직인 칸의 수가 0이면)-1 리턴
		else System.out.println(dis[7][7]);//그게 아니면 dis[7][7]에 저장된 값 출력
		

	}

	private static void BFS(int x, int y) { 
		Queue<Point> q = new LinkedList<>();
		q.offer(new Point(x, y)); //큐에 x,y 좌표넣고
		map[x][y] = 1; //방문처리
		
		while(!q.isEmpty()) {
			Point now = q.poll();
			for(int i = 0; i<4; i++) {
				int nx = now.x + dx[i];
				int ny = now.y + dy[i];
				if(nx>=1 && nx<=7 && ny >=1 && ny<=7 && map[nx][ny]==0) {
					map[nx][ny] = 1;
					q.offer(new Point(nx, ny));
					dis[nx][ny] = dis[now.x][now.y]+1; // 한칸씩 움직일 때마다 dis배열에 칸수 1씩 더해줌
					
				}
			}
			
		}
		
		
	}


}
