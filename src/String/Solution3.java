package String;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution3 { //3. 문장 속 단어
/*
 * 첫 줄에 가장 긴 단어를 출력한다. 
 * 가장 길이가 긴 단어가 여러개일 경우 문장속에서 가장 앞쪽에 위치한


 * 입력 : it is time to study
 * 출력 : study
 * */
	
	
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = br.readLine();
		System.out.println(Solution(str));
	}

	private static String Solution(String str) {
		String answer = "";
		int m = Integer.MIN_VALUE; // 비교할 최소값
		String[] s = str.split(" "); //공백으로 리스트 생성
		for(String x : s) { //리스트 s에 있는 원소 x 하나씩 받아오기
			int len = x.length(); //가장 긴 단어 출력해야하므로
			if(len>m) { // len > m 
				m = len; //m에 x길이 저장
				answer = x; 
			}
		}
		
		
		return answer;
	}
}