package Array;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution_Array7 {

	public static void main(String[] args) throws IOException {
		// 점수 계산
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		//int[] arr = new int[N+1]; // 입력에서 배열의 크기를 하나 크게 받는다
		//for(int i = 1; i<N; i++) { //0인덱스 비워두고 입력받음
		//	arr[i] = Integer.parseInt(st.nextToken());
		//}
		
		int[] arr = new int[N]; 
		for(int i = 0; i<N; i++) { 
			arr[i] = Integer.parseInt(st.nextToken());
		}
		
		System.out.println(solution_p(N,arr));
	}
	//오답
	private static int solution(int[] arr) {
		int final_score = 0;
		int[] score = new int[arr.length]; //간단한 점수 구하는 문제 -> 
		int now = 0;
		
		for(int i = 0; i<arr.length; i++) {
			if(now == arr[i]) {
				if(now == 1) {
					score[i] = score[i-1]+1;
					now = 1;
				}
				else { //now == 0
					now = 0;
					score[i] =0;
				}
			}
			else {
				if(now == 0 && arr[i]==1) {
					score[i] = 1;
					now = 1;
				}
				
				else {
					now = 0;
					score[i] = 0;
				}
				
			}
		}
		//System.out.println(Arrays.toString(score));
		for(int n = 1; n<score.length; n++) {
			final_score += score[n];
		}
		return final_score;
	}
	
	public static int solution_p(int n, int[] arr){
		int answer=0, cnt=0;
		for(int i=0; i<n; i++){
			if(arr[i]==1){
				cnt++;
				answer+=cnt;
			}
			else cnt=0;
		}	
		return answer;
	}
	
}
