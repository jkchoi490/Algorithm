package String;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Solution12 {
	
	static int n;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		String str = br.readLine();
		solution(str);
		
	}
	private static void solution(String str) {
		String answer = "";
		for(int i = 0; i<n; i++) {
			String tmp = str.substring(0, 7); //0~6까지 잘라내기 #****##, #**####, #**####, #**##**
			tmp = tmp.replace('#', '1').replace('*', '0'); // #-> 1, * -> 0 으로 변경
			int num = Integer.parseInt(tmp, 2); //tmp를 이진수로 변경
			answer += (char) num; // 숫자 -> 문자로 변경
			str = str.substring(7); // 7개씩 끊고 넘어가기
			
		}
		System.out.println(answer);
		
	}

	
}
