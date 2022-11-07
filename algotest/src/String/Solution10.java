package String;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Solution10 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = br.readLine();
		System.out.println(Solution(input));
	}

	private static String Solution(String input) {
		StringBuilder sb = new StringBuilder();
		String[] s = input.split(" ");
		
		char[] s_arr = s[0].toCharArray();
		char t = s_arr[1];
		int[] t_idx;
		int counting = 0;
		//System.out.println(Arrays.toString(s_arr));
		//System.out.println(t);
		
		for(int i = 0; i<s_arr.length; i++) {
			if(s_arr[i] == t) {
				counting++;
				t_idx = new int[counting];
				t_idx[i] = i;
			}
		}
		//System.out.println(Arrays.toString(t_idx));
		
		return sb.toString();
	}
	
/*
	private static String Solution(String str) {
		int[] answer;
		int d = Integer.MAX_VALUE;
		String[] s = str.split(" ");
		char[] carr = s[0].toCharArray();
		char target = s[1].charAt(0); // string -> character
		answer = new int[carr.length];
		
		for(int i = 0; i<carr.length; i++) {
			
		}
		return Arrays.toString(answer);
	}
	*/

}
