package BruteForce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BAEKJOON2798 {
	static int result = 0;
	static int[] combi, arr;
	static int n,m;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());
		arr = new int[n];
		combi = new int[3];
		for(int i = 0; i<n; i++) arr[i] = Integer.parseInt(st.nextToken());
		DFS(0,0);
		System.out.println(result);
	}

	private static int DFS(int L, int s) {
		int sum = 0;
		if(L == 3) {
			for(int x : combi) sum += x;	
			if(sum <=m) {
				result = Math.max(result, sum);
				return result;
			}
	
		}else {
			for(int i = s; i<n; i++) {
				combi[L] = arr[i];
				DFS(L+1, i+1);
			}
		}
		return 0;
		
	}

}
