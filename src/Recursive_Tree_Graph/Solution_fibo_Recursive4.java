package Recursive_Tree_Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Solution_fibo_Recursive4 {
	
	static int[] fibo;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		fibo = new int[N+1];
		DFS(N);
		for(int i = 1; i<fibo.length; i++) System.out.print(fibo[i]+" ");
	}

	private static int DFS(int n) {
		if(fibo[n]>0) return fibo[n];
		if(n==1) return fibo[n] = 1;
		else if (n==2) return fibo[n] = 1;
		else return fibo[n] = DFS(n-2)+DFS(n-1);
		
	}

}
