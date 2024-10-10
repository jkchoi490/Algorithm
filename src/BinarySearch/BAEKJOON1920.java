import java.io.*;
import java.util.*;

public class BAEKJOON1920 {

	static int N, M;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		int[] arr = new int[N];
		for(int i = 0; i<N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		Arrays.sort(arr);
		M = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i<M; i++) {
			int value = Integer.parseInt(st.nextToken());
			int result = BinarySearch(arr, value);
			if(result == -1) sb.append(0).append("\n");
			else sb.append(1).append("\n");
		}
		System.out.println(sb.toString());
		
	}
	

	public static int BinarySearch(int[] arr, int value) {
		
		int left = 0;
		int right = arr.length-1;
		
		while(left <= right) {
			int mid = left + (right - left)/2;
			
			if(arr[mid] == value) {
				return mid; 
			}
			
			if(arr[mid] < value) {
				left = mid + 1; 
			}
			else {
				right = mid - 1;
			}
		}
		
		return -1; 
	}

}
