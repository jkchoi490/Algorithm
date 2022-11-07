package String;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution1 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = br.readLine();
		char c = br.readLine().charAt(0);
		System.out.println(solution(str, c));
	}

	private static int solution(String str, char c) {
		int answer = 0;
		str = str.toUpperCase(); 
		c = Character.toUpperCase(c);
		for(int i = 0; i<str.length(); i++) {
			if(str.charAt(i) == c) answer++;
		}
		return answer;
	}

}