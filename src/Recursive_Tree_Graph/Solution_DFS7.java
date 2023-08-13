package Recursive_Tree_Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class Solution_DFS7 { // 동전교환

	// 동전의 개수는 무한히 많이 있음
	static int N,M, answer = Integer.MAX_VALUE;
	static Integer[] coins; // ** Arrays.sort사용을 위해 객체형 int 배열로 선언
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		
		coins = new Integer[N]; 
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i = 0; i<N; i++) coins[i] = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(br.readLine());
		
		Arrays.sort(coins, Collections.reverseOrder()); //** 큰 금액부터 적용하면 효율적임 -> 내림차순 정렬한 후 DFS진행
		
		DFS(0, 0, coins);
		System.out.println(answer);

	}

	public static void DFS(int L, int sum, Integer[] coins) {
		if(sum == M) {
			answer = Math.min(answer, L);
			return;
		}
		
		if(L>=answer) return; //** 동전개수가 answer값 보다 크면 종료시켜서 시간복잡도 줄이기

		if(sum > M) return;
		else {
			for(int i = 0; i<N; i++) {
				DFS(L+1, sum+coins[i], coins);
			}
		}
	}

}
