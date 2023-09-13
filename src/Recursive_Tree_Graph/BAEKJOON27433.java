package Recursive_Tree_Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BAEKJOON27433 {
	static int n, ans=1;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		System.out.println(DFS(n));
	}

	private static int DFS(int n) {
		if(n==0) return 1; 
		if(n==1) return 1; 
		else  return ans = n*DFS(n-1);

	}

}
