import java.io.*;
import java.util.*;

public class BAEKJOON2578 { 
	static int[][] board;
	static int cnt;
	static boolean up = false, down = false;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		board = new int[5][5];
		StringTokenizer st;
		for(int i = 0; i<5; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j<5; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		int a = 0, b = 0;
		for(int i = 0; i<5; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j<5; j++) {
				int num = Integer.parseInt(st.nextToken());
				for(int k = 0; k<5; k++) {
					for(int l = 0; l<5; l++) {
						if(board[k][l] == num) {
							board[k][l] = 0;
							 a = k;
							 b = l;
						}
					}
				}
				rCheck(a,b);
				cCheck(a,b);
				upCheck();
				downCheck();
				
				if(cnt >= 3) {
					System.out.println(i*5+j+1);
					return;
				}
				
			}
		}
		
	}

	private static void rCheck(int i, int j) {
		int zeroCnt = 0;
		for(int k = 0; k<5; k++) {
			if(board[i][k] == 0) zeroCnt++;
		}
		if(zeroCnt == 5) cnt++;
		
	}

	private static void cCheck(int i, int j) {
		int zeroCnt = 0;
		for(int k = 0; k<5; k++) {
			if(board[k][j] == 0) {
				zeroCnt++;
			}
		}
		if(zeroCnt == 5) cnt++;
	}

	private static void upCheck() {
		int zeroCnt = 0;
		if(!up) {
			for(int i = 0; i<5; i++) {
				if(board[4-i][i] == 0) {
					zeroCnt++;
				}
			}
			if(zeroCnt == 5) {
				cnt++;
				up = true;
			}
		}
		
	}

	private static void downCheck() {
		int zeroCnt = 0;
		if(!down) {
			for(int i = 0; i<5; i++) {
				if(board[i][i] == 0) zeroCnt++;
			}
			if(zeroCnt == 5) {
				cnt++;
				down = true;
			}
		}
	}

}


