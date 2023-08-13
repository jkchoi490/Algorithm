package Recursive_Tree_Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_DFS9 { // 조합의 경우수(메모이제이션)

	static int n, r;
	static int[][] memo = new int[35][35]; //메모이제이션을 위한 배열 생성
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		r = Integer.parseInt(st.nextToken());
		System.out.println(DFS(n,r));
		
	}

	private static int DFS(int n, int r) {
		if(memo[n][r] > 0) return memo[n][r]; //이미 구했으면 구한값 리턴
		if(n==r || r==0) return 1; //1을 리턴하는 경우
		else return memo[n][r] = DFS(n-1, r-1) + DFS(n-1, r); //memo 배열에 구한 값 저장
	
	}

}
