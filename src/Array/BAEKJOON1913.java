import java.io.*;
import java.util.*;

public class BAEKJOON1913 {
	static int N, goal;
	static int[] dx = {-1, 0,1,0};
	static int[] dy = {0,  1,0,-1};
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		goal = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		int[][] arr = new int[N][N];
		int cnt_max = 1;
		int x  = N/2, y = N/2;
		int dir = 0, cnt = 0, flag = 0;
		int answer_x = 0, answer_y = 0;
		int t = 2;
		arr[x][y] = 1;
		while(x != 0 || y !=0) {
			
			x = x+dx[dir]; 
			y = y+dy[dir];
			arr[x][y] = t;
			t+=1;
			cnt+=1;
			if(cnt == cnt_max) {
				cnt = 0;
				dir = (dir+1)%4; 
				if(flag == 0) { 
					flag = 1;
				}
				else {
					flag = 0;
					cnt_max += 1;
				}
			}
			
		}
		
		for(int i = 0; i<N; i++) {
			for(int j = 0; j<N; j++) {
				if(arr[i][j] == goal) {
					answer_x = i+1;
					answer_y = j+1;
				}
				sb.append(arr[i][j]).append(" ");
			}
			sb.append("\n");
		}
		
		sb.append(answer_x+" "+answer_y);
		System.out.println(sb.toString());
	}
	
}
