package Stack_and_Queue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Solution_Stack5 { // 쇠막대기

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String bar = br.readLine();
		System.out.println(solution(bar));

	}

	private static int solution(String bar) {
		int ans = 0;
		Stack<Character> stack = new Stack<>();
		for(int i = 0; i<bar.length(); i++) {
			
			if(bar.charAt(i) == '(') stack.push('(');
			else {
				stack.pop();
				if(bar.charAt(i-1) == '(') ans += stack.size();//바로 앞지점이 닫는괄호(레이저)면 cnt에 스택 사이즈만큼 더해줌
				else ans++;
			}
			
		}
	
	
		return ans;
	}

}
