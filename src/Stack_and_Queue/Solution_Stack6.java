package Stack_and_Queue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Solution_Stack6 { // 괄호

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		for(int i = 0; i<T; i++) {
			String str = br.readLine();
			System.out.println(solution(str)); 	
		}

	}

	public static String solution(String str) {
		String ans = "NO";
		int lcnt = 0, rcnt = 0; 
		Stack<Character> stack = new Stack<>();
		for(char x : str.toCharArray()) {
			if(x==')') {
				rcnt++; 
				while(!stack.isEmpty()&&stack.pop()!='('); 
			}else {
				lcnt++; 
				stack.push(x);
			}
		}
		
		if(stack.isEmpty() && rcnt==lcnt) return ans = "YES"; 
		
		return ans;
	}

}
