package TwoPointers_and_SlidingWindow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution_TwoPointers1 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[] arr= new int[N];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int n = 0; n <N; n++) {
			arr[n] = Integer.parseInt(st.nextToken());
		}
		int M = Integer.parseInt(br.readLine());
		int[] marr = new int[M];
		st = new StringTokenizer(br.readLine()); 
		for(int m = 0; m<M; m++) {
			marr[m] = Integer.parseInt(st.nextToken());
		}
		
		//System.out.println(Arrays.toString(arr));
		//System.out.println(Arrays.toString(marr));
		//System.out.println(TwoPointers(arr));
		
		ArrayList<Integer> ans = solution(N,M,arr,marr);
		for(int x : ans) {
			System.out.print(x+" ");
		}
	}
	
	public static ArrayList<Integer> solution(int n, int m, int[] a, int[] b){
		ArrayList<Integer> answer = new ArrayList<>();
		int p1=0, p2=0;
		while(p1<n && p2<m){
			if(a[p1]<b[p2]) answer.add(a[p1++]);
			else answer.add(b[p2++]);
		}
		while(p1<n) answer.add(a[p1++]);
		while(p2<m) answer.add(b[p2++]);
		return answer;
	}

	

}