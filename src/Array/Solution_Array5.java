package Array;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Solution_Array5 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		System.out.println(solution(N));
	}

	private static int solution(int N) {
		int ans = 0;
		int[] arr = new int[N+1]; // 0으로 초기화	(0~N)
		for(int i = 2; i<=N; i++) { 
			if(arr[i] == 0) ans++;
			for(int j = i; j<=N; j=j+i) {
				arr[j] = 1;
			}
		}
		//System.out.println(Arrays.toString(arr));
		return ans;
	}
	
}
