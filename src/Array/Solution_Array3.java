package Array;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution_Array3 {
	static int N;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		int[][] game = new int[2][N];
		StringTokenizer st;
		for(int i = 0; i<2; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j<N; j++) {
			game[i][j]  = Integer.parseInt(st.nextToken());
		}
	}
		System.out.println(solution(game));	
	}

	private static String solution(int[][] game) {
		StringBuilder sb = new StringBuilder();
		for(int i = 1; i<2; i++) {
			for(int j = 0; j<N; j++) {
				if(game[i][j]< game[i-1][j]) {
					if((game[i-1][j] == 3) && (game[i][j] ==1)) {
						sb.append("B\n");
					}
					else sb.append("A\n");
			
				}
				
				else if (game[i][j] > game[i-1][j]){
					if((game[i-1][j] == 1) && (game[i][j] ==3)) {//A가 3(보), B가 1(가위)
						sb.append("A\n");
						}
					else sb.append("B\n");

				}
				else {
					sb.append("D\n");
				}
				
			}
		}
		
		return sb.toString();
	}

}
