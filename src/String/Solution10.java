package String;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution10 { //10. 가장짧은 문자거리

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		String s = st.nextToken();
		char t = st.nextToken().charAt(0); //e 라는 string 에서 첫번째 인덱스
		System.out.print(Solution(s,t));
	}

	private static String Solution(String s, char t) {
		
		int[] answer = new int[s.length()];
		int p = 1000;
		for(int i = 0; i<s.length(); i++) { //왼쪽으로부터의 t로부터 떨어진 거리
			if(s.charAt(i) == t) {
				p = 0;
				answer[i] = p;
			}
			else {
				p++;
				answer[i] = p;
			}
		}
		p = 1000;
		 //오른쪽에 있는 t로부터의 거리 구하기
		for(int i = s.length()-1; i>=0; i--) { //for문 거꾸로 돌면서 오른쪽으로부터의 t로부터 떨어진 거리 구하기
			if(s.charAt(i) == t) {
				p = 0;
			}
			else {
				p++;
				answer[i] = Math.min(answer[i], p);//기존값과 p값중 작은 거로 넣기
			}
		}
		
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i<answer.length; i++) {
			sb.append(answer[i]).append(" ");
		}
			
		return sb.toString();
	}
	
}