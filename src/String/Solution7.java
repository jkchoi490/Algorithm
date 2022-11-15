package String;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution7 { // 7. 회문 문자열

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = br.readLine();
		System.out.println(Solution(str));

	}

	private static String Solution(String str) {
		String answer = "";
		str = str.toUpperCase();
		char[] c_arr = str.toCharArray();
		int lt = 0;
		int rt = str.length()-1;
		for(int i = 0; i<str.length()/2; i++) {
			if(c_arr[i] == c_arr[rt-i]) continue;
			else {
				answer = "NO";
				return answer;
			}
			
		}
		answer = "YES";
		return answer;
	}

}