import java.io.*;
import java.util.*;

public class BAEKJOON20057 {
	static int n;
	static int[][] map;
	static int[] dx = {0, 1, 0, -1};
	static int[] dy = {-1, 0, 1, 0};

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
		
		int d = 0, cnt = 0, flag = 0;
		
		while(x != 0 || y != 0) {
			x = x + dx[d];
			y = y + dy[d];
			//
		
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
		

	
		
	}

}
