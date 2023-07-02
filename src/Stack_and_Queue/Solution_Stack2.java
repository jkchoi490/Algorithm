package Stack_and_Queue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Solution_Stack2 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		char[] inputs = br.readLine().toCharArray();
		//System.out.println(solution(inputs));
		System.out.println(solution_p(inputs));
	}

	private static String solution_p(char[] inputs) {
		String ans = "";
		Stack<Character> stack2 = new Stack<>();
		for(char x : inputs) {
			if(x == ')') {
				while(stack2.pop()!='('); //'('를 꺼내고 멈춘다.
			}
			else stack2.push(x);
		}
		
		for(int i = 0; i<stack2.size(); i++) {
			ans += stack2.get(i);
		}
		
		return ans;
	}

	private static String solution(char[] inputs) { //fail
		StringBuilder sb = new StringBuilder();
		
		Stack<Character> stack = new Stack<>();
		int open_cnt = 0;
		int close_cnt = 0;
		int open_idx = 0;
		int close_idx = 0;
		int open_target_idx = 0;
		int close_target_idx = 0;
		
		for(char x : stack) {
			stack.push(x);
			
			if(x=='(') {
				open_cnt++;
				System.out.println(open_cnt);
			}
			else if (x == ')') {
				close_cnt++;
				System.out.println(close_cnt);
			}
			
			else if((x=='(') &&(open_cnt == 1)) {
				open_target_idx = stack.indexOf(x); 
				System.out.println(open_target_idx);
			}
			else if ((x == ')')&&(close_cnt == open_cnt)) {
				close_target_idx = stack.indexOf(x);
				System.out.println(close_target_idx);
			}
			
			else continue;
		}
		System.out.println(stack);
		return sb.toString();
	}

}
