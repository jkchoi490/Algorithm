package Sorting_and_Searching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution3_InsertionSort { // 삽입정렬

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		int[] arr = new int[N];
		for(int i = 0; i<N; i++) arr[i] = Integer.parseInt(st.nextToken());
		for(int x : InsertionSort(N, arr)) System.out.print(x+" ");
		

	}

	public static int[] InsertionSort(int n, int[] arr) {
		for(int i = 0; i<n; i++) {
			int tmp = arr[i], j; // tmp에 arr[i] 값 저장
			for(j = i-1; j>=0; j--) { //이전에 있는 원소들과 비교하며 삽입해감
				if(arr[j] > tmp) arr[j+1] = arr[j];  // arr[j]가 tmp보다 크면 arr[j]값을 뒤에 둔다
				else break;
			}
			arr[j+1] = tmp; //for문 다 돌고 난 후 j+1위치에 tmp 삽입  
			//System.out.println(Arrays.toString(arr));
/*
				[11, 7, 5, 6, 10, 9] i = 0
				[7, 11, 5, 6, 10, 9] i = 1, j = 0 
				[5, 7, 11, 6, 10, 9] i = 2, j = 1, 0
				[5, 6, 7, 11, 10, 9] i = 3, j = 2, 1, 0
				[5, 6, 7, 10, 11, 9] i = 4, j = 3, 2, 1, 0
				[5, 6, 7, 9, 10, 11] i = 5, j = 4, 3, 2, 1, 0
*/			
		}
		
		return arr;
	}

}
