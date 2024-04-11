import java.io.*;
import java.util.*;

public class BAEKJOON20057 {
	static int n;
	static int[][] map;
	static int[] dx = {0, 1, 0, -1};
	static int[] dy = {-1, 0, 1, 0};
	static int[] mul = {2, 10, 7, 1, 5, 10, 7, 1, 2, 0};
	static int[][] spreadx = {
		{-2,-1,-1,-1, 0, 1, 1, 1, 2, 0},
		{ 0, 1, 0,-1, 2, 1, 0,-1, 0, 1},
		{ 2, 1, 1, 1, 0,-1,-1,-1,-2, 0},
		{ 0,-1, 0, 1,-2,-1, 0, 1, 0,-1}	
	};
	
	static int[][] spready = {
		{ 0,-1, 0, 1,-2,-1, 0, 1, 0,-1},
		{-2,-1,-1,-1, 0, 1, 1, 1, 2, 0},
		{ 0, 1, 0,-1, 2, 1, 0,-1, 0, 1},
		{ 2, 1, 1, 1, 0,-1,-1,-1,-2, 0}	
	};
	public static void main(String[] args) throws IOException {
	
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		map = new int[n][n];
		StringTokenizer st;
		for(int i = 0; i<n; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j<n; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		int x = n/2, y = n/2;
		int max = 1;
		int answer = 0;
		int d = 0, cnt = 0, flag = 0;
		
		while(x != 0 || y != 0) {
			x = x + dx[d];
			y = y + dy[d];
			
			if(map[x][y] > 0) {
				int value = map[x][y];
				map[x][y] = 0;	
				int sum = 0;
				
				for(int i = 0; i<=9; i++) {
					int nx = x+spreadx[d][i];
					int ny = y+spready[d][i];
					int t = (value*mul[i])/100;
					if(i==9) {
						t = value - sum;
					}
					if(nx>=0 && nx<n && ny>=0 && ny<n) {
						map[nx][ny] += t;
					}
					else answer += t;
					
					sum += t;
				}
			}
			
			cnt+=1;
			
			if(cnt == max) {
				cnt = 0;
				d = (d+1)%4;
				if(flag == 0) flag = 1;
				else {
					flag = 0;
					max += 1;
				}
			}
		}
		
		System.out.println(answer);
	
		
	}

}
