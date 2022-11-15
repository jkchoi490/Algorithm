package String;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Solution9 { // 9.숫자만 추출

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = br.readLine();
		System.out.println(Solution(str));

	}
	private static int Solution(String str) {
		String ans = "";
		for(char x : str.toCharArray()) {
			if(Character.isDigit(x)) ans += x;
		}
		return Integer.parseInt(ans);
	}

}