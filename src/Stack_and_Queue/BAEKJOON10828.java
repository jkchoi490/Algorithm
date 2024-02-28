import java.io.*;
import java.util.*;

public class BAEKJOON10828 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		Stack<Integer> stack = new Stack<>();
		for(int i = 0; i<N; i++) {
			String[] str = br.readLine().split(" ");
			String command = str[0];
			if(command.equals("push")) {
				int num = Integer.parseInt(str[1]);
				stack.push(num);
			}
			else if(command.equals("pop")) {
				if(stack.isEmpty()) System.out.println(-1);
				else System.out.println(stack.pop());
			}
			
			else if(command.equals("size")) {
				System.out.println(stack.size());
			}
			else if(command.equals("empty")) {
				if(stack.isEmpty()) System.out.println(1);
				else System.out.println(0);
			}
			else if(command.equals("top")) {
				if(stack.isEmpty()) System.out.println(-1);
				else System.out.println(stack.peek());
			}
			
		}

	}

}
