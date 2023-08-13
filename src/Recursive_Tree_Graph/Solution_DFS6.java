package Recursive_Tree_Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_DFS6 { // 중복순열 구하기

	static int N, M;
	static int[] perm;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		perm = new int[M];
		DFS(0);

	}
	public static void DFS(int L) {
		if(L == M) {
			for(int x : perm) System.out.print(x+" ");
			System.out.println();
		}else {
			for(int i = 1; i<=N; i++) {
				perm[L] = i;
				DFS(L+1);
			}
		}
		
	}

}
