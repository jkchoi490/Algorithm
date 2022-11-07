package String;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution2 {
	static StringBuilder sb = new StringBuilder();
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = br.readLine();
		System.out.println(Solution(str));
	}

	private static String Solution(String str) {
		for(char x : str.toCharArray()) {
			if(Character.isLowerCase(x)) sb.append(Character.toUpperCase(x));
			else sb.append(Character.toLowerCase(x));
		}
		
		return sb.toString();
	}

}
