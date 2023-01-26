package TwoPointers_and_SlidingWindow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution_TwoPointers1 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[] arr= new int[N];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int n = 0; n <N; n++) {
			arr[n] = Integer.parseInt(st.nextToken());
		}
		int M = Integer.parseInt(br.readLine());
		int[] marr = new int[M];
		st = new StringTokenizer(br.readLine()); 
		for(int m = 0; m<M; m++) {
			marr[m] = Integer.parseInt(st.nextToken());
		}
		
		//System.out.println(Arrays.toString(arr));
		//System.out.println(Arrays.toString(marr));
		System.out.println(TwoPointers(arr));
		
	}
	public static int TwoPointers(int[] arr) {
		int partial_sum = 0;
		
		int count = 0;
		int start = 0;
		int end = 0;
		
		
		
		return partial_sum;
	}
	

}