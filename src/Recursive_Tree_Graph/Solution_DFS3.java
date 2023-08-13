package Recursive_Tree_Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_DFS3 { // 합이 같은 부분집합(DFS : 아마존 인터뷰)
	static String answer = "NO";
	static int N, total = 0;
	static boolean flag = false; // 서로소인지 판별
	
	static int[] ch, arr;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		ch = new int[N+1];
		arr = new int[N+1];
		for(int i = 1; i<=N; i++) arr[i] = Integer.parseInt(st.nextToken());
		DFS(0,0,arr);
		System.out.println(answer);
	
	}

	public static void DFS(int L, int sum, int[] arr) {
		if(flag) return; //서로소이면 종료
		if(sum > total/2) return; // 두 부분집합의 총합이 같아야 하므로 부분집합의 총합이 total의 절반보다 크면 종료
		if(L == N) {
			if((total-sum)== sum) { // 두 부분집합의 총합이 같으면
				answer = "YES"; //answer값을 YES로 하고
				flag = true; //flag를 true로 
			}
		}else {
			DFS(L+1, sum+arr[L], arr);
			DFS(L+1, sum, arr);
			
		}
	
	}

}
