package TwoPointers_and_SlidingWindow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution_TwoPointers2 { //공통원소 구하기

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[] A = new int[N];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i = 0; i<N; i++) {
			A[i] = Integer.parseInt(st.nextToken());
		}
		int M = Integer.parseInt(br.readLine());
		int[] B = new int[M];
		st = new StringTokenizer(br.readLine());
		for(int j = 0; j<M; j++) {
			B[j] = Integer.parseInt(st.nextToken());
		}
		solution(N,A,M,B);
		
	}
	
	private static void solution(int N, int[] A, int M, int[] B) {
		ArrayList<Integer> arr = new ArrayList<>();
		int p1 = 0; 
		int p2 = 0;
		
		Arrays.sort(A);
		Arrays.sort(B);
		
		while(p1<N && p2<M) {
			if(A[p1] < B[p2]) p1++;
			else if(A[p1] == B[p2]) {
				arr.add(A[p1++]);
				p2++;
			}
			else p2++;
		}
		
		for(int x : arr) {
			System.out.print(x+" ");
		}
	}

}
