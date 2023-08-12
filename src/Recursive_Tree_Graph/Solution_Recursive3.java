package Recursive_Tree_Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution_Recursive3 { // 팩토리얼

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		System.out.println(DFS(N));;
	}

	private static int DFS(int n) {
		if(n==1) return 1;
		else return n*DFS(n-1);
	}

}
