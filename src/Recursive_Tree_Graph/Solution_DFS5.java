package Recursive_Tree_Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_DFS5 { // 최대점수 구하기
	static int N, M, answer = Integer.MIN_VALUE;
	static int[] scores, times;
	static int score, time;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		scores = new int[N];
		times = new int[N];
		score=time=0;
		
		for(int i = 0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			scores[i] = Integer.parseInt(st.nextToken());
			times[i] = Integer.parseInt(st.nextToken());
			
		}
		DFS(0,0,0,scores,times);
		System.out.println(answer);

	}

	private static void DFS(int L, int score, int time, int[] scores, int[] times) {
		// 제한시간과 문제 푼 개수로 종료조건과 최대점수를 구한다
		if(time > M) return; //종료조건 : 제한시간 초과
		if(L==N) answer = Math.max(answer, score); // N개를 다 풀면 answer에 최댓값 저장 
		else {
			DFS(L+1, score+scores[L], time+times[L], scores, times);
			DFS(L+1, score, time, scores, times);
		}
		
	}

}
