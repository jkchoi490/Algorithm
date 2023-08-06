package HashMap_and_TreeSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Solution_HashMap5 { // k번째 큰 수

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		int[] card = new int[N];
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i<N; i++) {
			card[i] = Integer.parseInt(st.nextToken());
		}
		System.out.println(solution(N, K, card));
	}

	private static int solution(int n, int k, int[] card) {
		int ans = -1;
		 // 중복제거 위해서 Set사용 
		TreeSet<Integer> Tset = new TreeSet<>(Collections.reverseOrder());//reverseOrder() : 내림차순
		for(int i = 0; i<n; i++) { // N장 중 3장 뽑아야 하므로 3중 for문
			for(int j = i+1; j<n; j++) {
				for(int l = j+1; l<n; l++) {
					Tset.add(card[i]+card[j]+card[l]);
				}
			}
		}
		int cnt = 0;
		for(int x : Tset) {
			cnt++;
			if( cnt == k) return x;
		}
		return ans;
		
	}

}

/*
TreeSet 메서드

Tset.remove(143); -> 원소를 지우는 메서드
System.out.println(Tset.size()); ->
System.out.println("first : "+ Tset.first()); -> 첫번째 값 리턴(오름차순 정렬->최소값, 내림차순 -> 최대값)
System.out.println("last : "+ Tset.last());

*/

