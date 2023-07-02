package Stack_and_Queue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Solution_Stack1 {
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = br.readLine();
		System.out.println(solution_p(input));
	}
	
	
	private static String solution(String input) {
		String ans = "NO";
		char[] inputs = input.toCharArray();
		Stack<Character> stack = new Stack<>();
		
		for(char x : inputs) {
			if(x == '(') stack.push(x);
			else if(x == ')') stack.pop();
		}
		if(stack.isEmpty()==true) {
			ans = "YES";
		}
		return ans;
	}
	
	private static String solution_p(String input) {
		String ans = "YES";
		char[] inputs = input.toCharArray();
		Stack<Character> stack = new Stack<>();
		
		for(char x : inputs) {
			if(x == '(') stack.push(x);
			else { // x == ')'일때 , 
				//스택이 비어있으면 짝이 안맞는 괄호이므로 NO 출력
				if(stack.isEmpty()) return ans="NO"; //닫는 괄호 ) 가 많은 상황 EX. (()))
				else stack.pop(); //스택이 비어있지 않으면 하나 꺼냄
			}
		}
		if(!stack.isEmpty()) return ans="NO"; //여는 괄호 (가 많은 상황 EX. (())()(
		return ans;
	}

}
