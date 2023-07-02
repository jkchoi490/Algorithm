package Recursive_Tree_Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution_Recursive2 {

	public static void main(String[] args) throws IOException {
	
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		DFS(N);
		
	}

	private static void DFS(int n) {
		if(n == 0) return;
		else {
			DFS(n/2);
			System.out.print(n%2);
		}
		
	}

	private static void fail_DFS(int n) { //오답
		StringBuilder sb = new StringBuilder();
		if(n /2 == 1) {
			System.out.print(Integer.parseInt(sb.toString()));
			return;
		}
		else {
			fail_DFS(n/2);
			sb.append(String.valueOf(n%2));
		}	
		
	}

}
