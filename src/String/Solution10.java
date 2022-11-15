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
		String ans = "";
		//공백 기준으로 string 나누기 teachermode e
		
		//s 배열 생성
		char[] s = input.toCharArray();
		
		System.out.println(Arrays.toString(s));
		
		
		return ans;
	}

}
