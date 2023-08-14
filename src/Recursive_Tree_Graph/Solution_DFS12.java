package Recursive_Tree_Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_DFS12 { //미로탐색(DFS)
	static int[][] map;
	static int cnt = 0;
	static int[] dx = {0, 0, -1, 1};
	static int[] dy = {-1, 1, 0, 0};

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		map = new int[8][8];
		
		for(int i = 1; i<=7; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 1; j<=7; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		map[1][1] = 1; //**출발지점 방문처리 해주기
		DFS(1,1);
		System.out.println(cnt);

	}

	public static void DFS(int x, int y) {
		if(x == 7 && y == 7) {
			cnt++;
		}else {
			for(int i = 0; i<4; i++) {
				int nx = x + dx[i];
				int ny = y + dy[i];
				if(nx>=1 && nx<=7 && ny>=1 && ny<=7 && map[nx][ny] == 0) {
					map[nx][ny] =1; //방문처리
					DFS(nx, ny);
					map[nx][ny] =0; //다시 방문처리 풀어주기
				}
			}
			
		}
		
	}

}
