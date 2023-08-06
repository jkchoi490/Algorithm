package Sorting_and_Searching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution2_BubbleSort {

	public static void main(String[] args) throws IOException {// 버블 정렬
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		int[] arr = new int[N];
		for(int i = 0; i<N; i++) arr[i] = Integer.parseInt(st.nextToken());
		for(int x : BubbleSort(arr))System.out.print(x+" ");
	}

	private static int[] BubbleSort(int[] arr) {
		for(int i = 0; i<arr.length-1; i++) { // i는 turn의 횟수
			for(int j = 0; j<arr.length-i-1; j++) {
				if(arr[j] > arr[j+1]) { // 앞에가 크면swap
					int tmp = arr[j];
					arr[j] = arr[j+1];
					arr[j+1] = tmp;
				}
			}
		//	System.out.println(Arrays.toString(arr));
/* 
 () : i번째 turn때 고정되는 수
		1) i = 0	[5, 11, 7, 13, 15, (23)] 
		2) i = 1	[5, 7, 11, 13, (15), 23] 
		3) i = 2	[5, 7, 11, (13), 15, 23] 
		4) i = 3	[5, 7, (11), 13, 15, 23] 
		5) i = 4	[5, (7), 11, 13, 15, 23]
*/
			
		}
		
		
		return arr;
	}

}
