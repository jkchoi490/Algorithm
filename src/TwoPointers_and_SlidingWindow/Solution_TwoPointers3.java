package TwoPointers_and_SlidingWindow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution_TwoPointers3 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());
		int[] arr = new int[N];
		for(int i = 0; i<N; i++) {
			arr[i]=Integer.parseInt(st.nextToken());
		}
		
		System.out.println(solution(N,K,arr));
	}

	private static int solution(int n, int k, int[] arr) {
		int max = 0;
		int sum = 0;
		int p1 = 0;
		int p2 = k-p1;
		
		while(p1<n && p2<n) {
			for(int i = p1; i<p2; i++) {
				sum += arr[i];
			}
			max = Math.max(max, sum);
			sum = 0;
			p1++;
			p2++;
		}
		
		return max;
	}

}
