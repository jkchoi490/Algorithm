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
		
		System.out.println(find_sum(map, N));
		
		
		
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
		int answer = Integer.MIN_VALUE;
		
		int max_sum = 0;
		int col_sum = 0; //각 행 값들의 합 (가로)
		int row_sum = 0; //각 열 값들의 합 (세로)
		int cross_sum1 = 0; //대각선 값들의 합 
		int cross_sum2 = 0; //대각선 값들의 합 
		
		for(int i = 0; i<N; i++){
			col_sum = row_sum = 0;
			for(int j = 0; j<N; j++) {
				col_sum += map[i][j]; //i는 고정이고 j가 변하면서 행의 합 구함
				row_sum += map[j][i];	
			}
			answer = Math.max(answer, col_sum);
			answer = Math.max(answer, row_sum);
		}		
		
		
		cross_sum1 = cross_sum2 = 0;
		
		for(int i = 0; i<N; i++) {
			cross_sum1 += map[i][i];
			cross_sum2 += map[i][N-1-i];
			
		}
		
		answer = Math.max(answer, cross_sum1);
		answer = Math.max(answer, cross_sum2);
		
		return answer;
		
		
	}
	
	public int solution(int n, int[][] arr){
		int answer=-2147000000;
		int sum1=0, sum2=0;
		
		for(int i=0; i<n; i++){
			sum1=sum2=0;
			for(int j=0; j<n; j++){
				sum1+=arr[i][j]; //i는 고정이고 j가 변하면서 행의 합 구함
				sum2+=arr[j][i]; //j는 고정이고 i가 변하면서 열의 합 구함
			}
			answer=Math.max(answer, sum1);
			answer=Math.max(answer, sum2);
		}
		
		//대각선의 합
		sum1=sum2=0;
		for(int i=0; i<n; i++){
			sum1+=arr[i][i];
			sum2+=arr[i][n-i-1];
		}
		answer=Math.max(answer, sum1);
		answer=Math.max(answer, sum2);
		return answer;
	}
	


}
