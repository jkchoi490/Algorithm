package Recursive_Tree_Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution_Recursive3 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		DFS(N);
	}

	private static void DFS(int n) {
		int ans = n;
		if( n == 0) {
			System.out.println(ans);
			return;
		}
		else {
			DFS(n-1);
			ans = ans *(n-1);
		}
		
	}

}
