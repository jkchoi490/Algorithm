import java.io.*;
import java.util.*;

public class BAEKJOON2138 {

	static int n;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		char[] arr = br.readLine().toCharArray();
		char[] result = br.readLine().toCharArray();
		
		int answer1 = countSwitches(Arrays.copyOf(arr, n), result, false);
		int answer2 = countSwitches(Arrays.copyOf(arr, n), result, true);
		int answer = Math.min(answer1, answer2);
		System.out.println(answer == Integer.MAX_VALUE ? -1 : answer);
	}
	public static int countSwitches(char[] arr, char[] result, boolean firstSwitch) {
		int n = arr.length;
		int switches = 0;
		
		if(firstSwitch) {
			change(arr, 0);
			switches++;
		}
		for(int i = 1; i<n; i++) {
			if(arr[i-1] != result[i-1]) {
				change(arr, i);
				switches++;
			}
		}
	
		for(int i = 0; i<n; i++) {
			if(arr[i] != result[i]) {
				return Integer.MAX_VALUE;
			}
		}
		
		return switches;
		
	}
	public static void change(char[] arr, int idx) {
		for(int i = idx - 1; i<=idx+1; i++) {
			if(i >= 0 && i<arr.length) {
				if(arr[i] == '0') arr[i] = '1';
				else arr[i] = '0';
			}
		}
		
	}
	

}
