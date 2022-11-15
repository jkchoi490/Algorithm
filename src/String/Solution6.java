package String;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution6 { // 6. 중복문자 제거

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = br.readLine();
		System.out.println(Solution(str));

	}

	private static String Solution(String str) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i<str.length(); i++) {
			if(str.indexOf(str.charAt(i)) == i) sb.append(str.charAt(i));
		}
		return sb.toString();
	}

}