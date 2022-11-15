package String;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution5 { //5. 특정 문자 뒤집기

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = br.readLine();
		System.out.println(Solution(str));

	}
	
	public static String Solution(String str) {
		String answer = "";
		char[] input = str.toCharArray();
		int lt = 0;
		int rt = input.length-1;
		
		while(lt<rt) {
			if(!Character.isAlphabetic(input[lt])) 
				lt++;
			else if(!Character.isAlphabetic(input[rt])) 
				rt--;
			else {
				char tmp = input[lt];
				input[lt] = input[rt];
				input[rt] = tmp;
				lt++;
				rt--;
			}
		}
		
	 	
	   	answer=String.valueOf(input);

		return answer;
	}

}