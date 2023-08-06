package Stack_and_Queue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Solution_Stack4 { // 후위식 연산(postfix)

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		char[] input = br.readLine().toCharArray();
		System.out.println(solution(input));
	}

	public static int solution(char[] input) {
		Stack<Integer> stack = new Stack<>();
		int ans = 0;
		for(char x : input) {
			if(Character.isDigit(x)) { //Character.isDigit(x) :x가 숫자이면 true 반환 
				stack.push(x-48); //x는 char형이므로 48을 빼서 숫자로 stack에 push
		}
		else {
			//연산에 사용될 숫자 먼저 꺼냄 
			int rt = stack.pop(); //오른쪽 먼저 꺼냄
			int lt = stack.pop();
			//연산 수행한 후 다시 스택에 push
			if(x=='+') stack.push(lt+rt);
			else if(x=='-') stack.push(lt-rt);
			else if(x=='*') stack.push(lt*rt);
			else if(x=='/') stack.push(lt/rt);
		}
	}
	
		ans = stack.get(0); //연산 결과 스택의 0번째 값
		return ans;

	}
	
}
