package Stack_and_Queue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class BAEKJOON28278 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Stack<Integer> stack = new Stack<>();
		int n = Integer.parseInt(br.readLine());
		for(int i = 0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			String command = st.nextToken();
			if(command.equals("1")) stack.push(Integer.parseInt(st.nextToken()));
			else if(command.equals("2")) {
					if(!stack.isEmpty()) {
						stack.pop();
						System.out.println(stack.peek());
						
					}
					else if(stack.size()==0) System.out.println(-1);
			} 
			else if(command.equals("3")) System.out.println(stack.size());
			else if(command.equals("4")) {
					if(stack.isEmpty()) System.out.println(1);
					else System.out.println(0);
			} 
			else if(command.equals("5")) {
					if(!stack.isEmpty()) System.out.println(stack.pop());
					else System.out.println(-1);
				}
				
			}
			
		}
	}


