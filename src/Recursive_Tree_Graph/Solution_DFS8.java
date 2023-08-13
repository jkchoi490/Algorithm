package Recursive_Tree_Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_DFS8 { // 순열
	
	static int N, M;
	static int[] arr, perm, ch; //중복해서 뽑으면 안되므로 체크배열 필요

	public static void main(String[] args) throws IOException { 
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		arr = new int[N];
		perm = new int[M];
		ch = new int[N];
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i<N; i++) arr[i] = Integer.parseInt(st.nextToken());
		DFS(0);
		
		
		
	}

	private static void DFS(int L) { //nPm
		if(L == M) { 
			for(int x : perm) System.out.print(x+" ");
			System.out.println();
		}else {
			for(int i = 0; i < N; i++) {
				if(ch[i] == 0) { 
					ch[i] = 1; 
					perm[L] = arr[i]; 
					DFS(L+1); 
					ch[i] = 0; 
				}
				
			}
		}
		
	}

}
