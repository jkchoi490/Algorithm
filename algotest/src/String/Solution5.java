package String;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Solution5 { //120 mb , 

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = br.readLine();
		System.out.println(Solution(str));

	}
	
	public static String Solution(String str) {
		StringBuilder sb = new StringBuilder();
		char[] input = str.toCharArray();
		System.out.println(Arrays.toString(input));

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
		
	  /* answer = "";
	   * answer=String.valueOf(s);
	   * �� �����ϰ� ��ü ����
	   * */
		for(int i = 0; i<input.length; i++) {
			sb.append(input[i]);
		}
		return sb.toString();
	}

}