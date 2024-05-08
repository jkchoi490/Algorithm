import java.io.*;
import java.util.*;

public class BAEKJOON2512 {

	static int n,m;
	static int[] arr;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		int sum = 0;
		arr = new int[n];
		for(int i = 0; i<n; i++) { 
			arr[i] = Integer.parseInt(st.nextToken());
			sum += arr[i];
		}
		m = Integer.parseInt(br.readLine());
		
		Arrays.sort(arr);
		if(sum <= m) System.out.println(arr[n-1]);
		else solution(arr, sum);
	}
	
	private static void solution(int[] arr, int total) {
		int answer = 0;
		int left = 0, right = total;
		
		while(left <= right) {
			int mid = (left+right)/2;
			
			if(checkSum(arr, mid, m)) {
				answer = Math.max(answer, mid);
				left = mid + 1;
			}else {
				right = mid - 1;
			}
		}
		
		System.out.println(answer);
	}

	private static boolean checkSum(int[] arr, int mid, int m) {
		int result = 0;
		for(int x : arr) {
			if(x > mid) {
				result += mid;
			}
			else result += x;
		}
		return result <= m;
	}


}
