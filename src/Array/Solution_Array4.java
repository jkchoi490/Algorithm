package Array;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution_Array4 {

	public static void main(String[] args) throws IOException {
		// 4. 피보나치 수열
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		System.out.println(solution(N));
	}

	private static String solution(int N) {
		StringBuilder sb = new StringBuilder();
		int[] arr = new int[N];
		
		arr[0] = 1;
		arr[1] = 1;
		sb.append(arr[0]).append(" ").append(arr[1]).append(" ");
		for(int i = 2; i<arr.length; i++) {
			arr[i] = arr[i-1]+arr[i-2];
			sb.append(arr[i]).append(" ");
		}
		
		return sb.toString();
	}

}
