package Array;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_Array9 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		StringTokenizer st;		
		int[][] map = new int[N][N];
		
		for(int i = 0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j<N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		//System.out.println(solution(map));
		
		
		
		/*
		for(int i = 0; i<N; i++)
		{
			for(int j = 0; j<N; j++) {
				System.out.print(map[i][j]+" ");
			}
			System.out.print("\n");
		}
		*/		
		
	}
	private static int find_sum(int[][] map, int N) {
		int max_sum = 0;
		int col_sum = 0; //행 값들의 합
		int row_sum = 0; //열 값들의 합
		int cross_sum = 0; //대각선 값들의 합
		
		for(int i = 0; i<N; i++){
			col_sum += map[i][i];
			for(int j = 0; j<N; j++) {
			
				
			}
		}		
		
		
		
		return max_sum;
		
		
	}
	


}
