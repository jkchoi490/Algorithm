package Array;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class Solution_Array8 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		Integer[] arr = new Integer[N]; // 배열에서 내림차순 이용하려면 Integer로 선언해야함
		for(int i = 0; i<N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}

		//System.out.println(solution_p(arr, N));
		for(int x : solution_p(arr,N)) System.out.print(x+" ");
		
	}

	private static Integer[] solution_p(Integer[] arr, int n) {
		Integer[] answer = new Integer[n];
		for(int i = 0; i<n; i++) {
			int cnt = 1;
			for(int j = 0; j<n; j++) {
				if(arr[j]>arr[i]) cnt++;
			}
			answer[i] = cnt;
		}
		return answer;
	}

	private static String solution(Integer[] input_arr, int n) {
		StringBuilder sb = new StringBuilder();
		Integer[] sorted_arr = input_arr.clone();
		Arrays.sort(sorted_arr,Collections.reverseOrder());

		int now = 0;
		boolean same = false;
		for(int i =0; i<n; i++) {
			now = input_arr[i];
			for(int j = 0; j<n; j++) {
				while(same==true) {
					if(now == sorted_arr[j]) {
						System.out.println(j+1);
						same = true;
					}
				}
			}
		}
		
		System.out.println(Arrays.toString(input_arr));
		System.out.println(sb.toString());
		return sb.toString();
	}

}
