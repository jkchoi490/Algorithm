package TwoPointers_and_SlidingWindow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution_TwoPointers1_1 { //오답

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[] arr1 = new int[N];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i = 0; i<N; i++) {
			arr1[i] = Integer.parseInt(st.nextToken());
		}
		int M = Integer.parseInt(br.readLine());
		int[] arr2 = new int[M];
		st = new StringTokenizer(br.readLine());
		for(int j = 0; j<M; j++) {
			arr2[j] = Integer.parseInt(st.nextToken());
		}
		for(int x : solution(N,M,arr1,arr2)) {
			System.out.print(x+" ");
		}
	}
	
	public static ArrayList<Integer> solution(int n, int m, int[] a, int[] b){
		ArrayList<Integer> answer = new ArrayList<>();
		int p1=0, p2=0;
		while(p1<n && p2<m){ //p1과 p2가 각각 배열의 크기를 넘지 않으면(인덱스 0,1,2 값)
			if(a[p1]<b[p2]) //p1이 가리키는 a배열의 값과 p2가 가리키는 b배열의 값을 비교해서 a배열 값이 더 작으면 
				answer.add(a[p1++]); //p1이 가리키는 a배열 값을 answer에 넣어주고 p1값을 증가시킨다.
			else answer.add(b[p2++]); //b배열값이 더 작으면 -> answer에 p2가 가리키는 b배열의 값을 넣어주고 p2를 증가시킨다.
		}
		//두 배열의 크기가 다르므로 한쪽이 남으면(ex. b배열의 인덱스 3,4값)
		while(p1<n) answer.add(a[p1++]); //만약 a배열이 더 크고 b배열은 다 돌았을 경우 -> 남은 a배열의 원소들 중 p1이 가리키는 원소를 answer에 삽입하고 p1을 증가시킴 
		while(p2<m) answer.add(b[p2++]); //만약 b배열이 더 크고 a배열은 다 돌았을 경우 -> 남은 b배열의 원소들 중 p2가 가리키는 원소를 answer에 삽입하고 p2를 증가시킴 
		return answer;
	}

}