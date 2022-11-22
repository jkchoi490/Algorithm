package String;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution11 {

	public static void main(String[] args) throws IOException {
		// 11. 문자열 압축
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println(solution(br.readLine()));
	}

	private static String solution(String input) {
		StringBuilder sb = new StringBuilder();
		input = input+" "; // java.lang.StringIndexOutOfBoundsException 에러 방지
		char target = input.charAt(0);
		int counting = 0;
		for(int i = 0; i<input.length(); i++) { 
			if(input.charAt(i) == target) {
				counting++;
			}  
			else {// 타겟이랑 문자랑 다르면
				sb.append(target).append(counting);
				target = input.charAt(i);
				counting = 1;
			}		
		
			
		}
	
		for(int i = 0; i<sb.length(); i++) {
			if(sb.charAt(i) == '1') {
				sb.deleteCharAt(i);
			}
		}
		
		return sb.toString();
	}
	
	public String solution_p(String s){
		String answer="";
		s=s+" ";
		int cnt=1;
		for(int i=0; i<s.length()-1; i++){
			if(s.charAt(i)==s.charAt(i+1)) cnt++;
			else{
				answer+=s.charAt(i);
				if(cnt>1) answer+=String.valueOf(cnt);
				cnt=1;
			}
		}
		return answer;
	}

}
