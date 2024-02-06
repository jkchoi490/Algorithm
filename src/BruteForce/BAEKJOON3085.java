import java.io.*;
import java.util.*;

public class BAEKJOON3085 {

	static int N, max=1;
	static char[][] board;
	static int[] dx = {-1,0,1,0};
	static int[] dy = {0,-1,0,1};
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		board = new char[N][N];
		for(int i = 0; i<N; i++) {
			board[i] = br.readLine().toCharArray();
		}

		for(int i = 0; i<N; i++) {
			for(int j = 0; j<N-1; j++) {
				change(i, j, i, j+1);
				findMax();
				change(i,j+1,i,j);
			}
		}

		for(int i = 0; i<N-1; i++) {
			for(int j = 0; j<N; j++) {
				change(i, j, i+1, j);
				findMax();
				change(i+1, j, i, j);
			}
		}
		System.out.println(max);
		
	}
	public static void findMax() {
		
		for(int i = 0; i<N; i++) {
			int cnt = 1;
			for(int j = 0; j<N-1; j++) {
				if(board[i][j]==board[i][j+1]) {
					cnt++;
					max = Math.max(max, cnt);
				}
				else {
					cnt = 1;
				}
			}
		}
		for(int i = 0; i<N; i++) {
			int cnt = 1;
			for(int j = 0; j<N-1; j++) {
				if(board[j][i]==board[j+1][i]) {
					cnt++;
					max = Math.max(max, cnt);
				}
				else {
					cnt = 1;
				}
			}
		}
		
	}
	public static void change(int x1, int y1, int x2, int y2) {
		char tmp = board[x1][y1];
		board[x1][y1] = board[x2][y2];
		board[x2][y2] = tmp;
	}

}
