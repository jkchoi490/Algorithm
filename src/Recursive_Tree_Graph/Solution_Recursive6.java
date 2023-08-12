package Recursive_Tree_Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution_Recursive6 { // 부분집합 구하기(DFS)
	
	static int N; 
	static int[] ch;

	public static void main(String[] args) throws IOException { 
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		ch = new int[N+1];
		DFS(1);
	}
	
	private static void DFS(int L) {
		if(L == N+1) { //기저 조건
			String tmp = "";
			for(int i = 1; i<=N; i++) {
				if(ch[i] == 1) tmp += (i+" "); //부분집합의 원소들 출력
			}
			
			if(tmp.length()>0) System.out.println(tmp);
		
		}else {
			ch[L] = 1; //원소 포함 o
			DFS(L+1);
			ch[L] = 0; //원소 포함 x
			DFS(L+1);
		}
	}

}
