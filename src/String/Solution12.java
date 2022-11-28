package String;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Solution12 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		String str = br.readLine();
		System.out.println(solution(n, str));
	}

	private static String solution(int n, String str) {
		StringBuilder sb = new StringBuilder();
		String[] div_arr = new String[n];
	
		for(int j = 0; j<4; j++) {
			for(int i = 1; i<(7*n)+1; i++) {
				sb.append(str.charAt(i-1)); 
				//System.out.println(sb.toString());
				if(i % 7 == 0) {
					div_arr[j] = sb.toString();
					//System.out.println(sb.toString());
					sb.setLength(0); //StringBuilder 저장한 문자열 모두 제거
					j++;
					
				}
				
			}
		//	System.out.println(Arrays.toString(div_arr));
			
			String alphabet = "";
			
			for(int k = 0; k <div_arr.length; k++) {
				alphabet = div_arr[k];
				for(int a = 0 ; a<alphabet.length(); a++) {
					
					if(alphabet.charAt(a) == '#') {
						alphabet = alphabet.replace('#', '1'); 
						div_arr[k] = alphabet;
					}
					else if(alphabet.charAt(a) == '*') {
						alphabet = alphabet.replace('*', '0');
						div_arr[k] = alphabet;
					}
				}
			}
			//System.out.println(Arrays.toString(div_arr));
					
	}
		StringBuilder s_r = new StringBuilder();
		int ans = 0;
		double result = 0;
		
		for(String s : div_arr) {
			ans = 0;	
			for(int i = 0; i<s.length(); i++) {
				if(s.charAt(i) == '1') {
					result = Math.pow(2, 6-i);
					ans += result;
				}
				
			}
			
			String a = Character.toString((char)ans);
			s_r.append(a);
		
		}
		
		return s_r.toString();
	}

}
