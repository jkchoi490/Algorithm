package Recursive_Tree_Graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Solution_Recursive4 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[] arr = new int[N];
		DFS(N,arr);
		

	}

	private static void DFS(int n, int[] arr) {
		arr[0] = 1;
		arr[1] = 1;
		int k = 2;
		if(k == n) {
			System.out.println(Arrays.toString(arr));
			return;
		}
		else {
			for(int i = 0; i<n-2; i++) {
				arr[i+2] = arr[i]+arr[i+1];
				k++;
			}
			DFS(n-1, arr);
		}
		
	}

	
}
