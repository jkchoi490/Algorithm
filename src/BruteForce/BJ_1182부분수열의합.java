package BruteForce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BJ_1182부분수열의합 { 
	static int n, s; //(1 ≤ N ≤ 20, |S| ≤ 1,000,000) N,S의 범위로 int형 사용
	static int[] arr; 
	static int answer = 0;
/*
N개의 수 중 몇 가지를 골라 합이 S가 되는 경우 세기

N개의 수 중에 몇 가지를 골라야 할지 알 수 없음 ->
N가지의 모든 수에 대해, 각 수를 고르거나/고르지 않는 2가지를 선택 ->
마지막 N번째 수를 선택하고 나서 지금까지 고른 수들을 다 더했을 때, S가 되는지 확인

각 수에 대해 고른다/고르지 않는다 2가지의 경우가 있으므로 모든 경우의 수는 2N (N이 최대 20)
*/
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		s = Integer.parseInt(st.nextToken());
		st = new StringTokenizer(br.readLine());
		arr = new int[n];
		for(int i = 0; i<n; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		
		DFS(0,0);
		if(s==0) answer--; // ** s가 0이면 모든 원소를 전부 고르지 않는 경우도 포함하므로
		System.out.println(answer);
		

	}
	
	public static void DFS(int L, int sum) {
		if(L == n) {
			if(sum == s) answer++;
			return;
		}else {
			DFS(L+1, sum+arr[L]); //arr[L] 선택하는 경우
			DFS(L+1, sum); //선택 안하는 경우
			
		}
	}

}
