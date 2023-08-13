package Recursive_Tree_Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_DFS4 { // 바둑이 승차
	static int C, N, answer=Integer.MIN_VALUE; // 최댓값 구해야 하므로 최솟값으로 초기화함
	static int[] arr; //바둑이들 배열

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		C = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		arr = new int[N];
		for(int i = 0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			arr[i] = Integer.parseInt(st.nextToken());
		}
		DFS(0,0,arr);
		System.out.println(answer);
	}

	private static void DFS(int L, int sum, int[] arr) {
		//sum>c이면 종료, 
		//max()함수를 사용하여 최댓값을 answer로 지정한다
		if(sum > C) return;
		if(L==N) {
			answer = Math.max(answer, sum);
		}
		else {
			DFS(L+1, sum+arr[L], arr); // 바둑이 태워서 sum에 무게 더하기
			DFS(L+1, sum,arr); //바둑이 안 태우기
		}
		
	}

}
