package TwoPointers_and_SlidingWindow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_TwoPointers4 { // 얀속부분수열(복합적 문제)

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int[] arr = new int[N];
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i<N; i++) arr[i] = Integer.parseInt(st.nextToken()); 
		System.out.println(solution(arr, N, M));
	}

	
	private static int solution(int[] arr, int n, int m) {
		int cnt = 0;
		int p1 = 0;
		int sum = 0;
	
		for(int p2 = 0; p2<n; p2++) {
				sum += arr[p2];
				if(sum == m) cnt++;
				while(sum>=m) {
					sum -=arr[p1++];
					if(sum == m) cnt++;
				}
				
			}
		
		return cnt;
	}

}
