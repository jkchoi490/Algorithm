

import java.io.*;
import java.util.*;

public class BAEKJOON2559 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		int[] arr = new int[n];
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i<n; i++) arr[i] = Integer.parseInt(st.nextToken());
		
		int sum = 0;
		for(int i = 0; i<k; i++) {
			sum += arr[i];
		}
		
		int max = sum;
		for(int i = k; i<n; i++) {
			sum = sum - arr[i-k]+arr[i];
			max = Math.max(max, sum);
		}
		System.out.println(max);
	}

}
