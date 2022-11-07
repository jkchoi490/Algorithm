package String;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Solution11 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = br.readLine();
		System.out.println(solution(str));
	}

	private static String solution(String str) {
		StringBuilder sb = new StringBuilder();
		//KKHSSSSSSSE
		int counting = 0;
		char target = str.charAt(0);
		for(int i = 0; i<str.length(); i++) {
			if(target == str.charAt(i)) counting++;
			else if (target != str.charAt(i)) {
				target = str.charAt(i);
			}
			
		}
		return sb.toString();
	}

}
