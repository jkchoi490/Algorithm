package String;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution12 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		String str = br.readLine();
		int str_length = 7;
		
		for(int i = 0; i<str.length(); i++) {
			String[] input = new String[str_length+1];
			StringTokenizer st = new StringTokenizer(str);
			for(int j = 0; j<input.length; j++) {
				input[j] =  st.nextToken();
			}
		}
		/*
		StringTokenizer st = new StringTokenizer(br.readLine());
		int str_length = 7;
		String[] input = new String[str_length+1];
		for(int i = 0; i<input.length; i++) {
			input[i] =  st.nextToken();
			
		}
		*/
		
		System.out.println(n);
		System.out.println(str);
		//System.out.println(Arrays.toString(input));
	}

}
